import hashlib
import logging
import multiprocessing
import threading
import time
from datetime import datetime
from functools import partial
from logging.config import dictConfig
from configs.configs import parallel_ds_batch_size, offset, limit, aws_ocr_prefix, user_mode_pseudo, \
    sample_size, ocr_immutable_keys, ocr_non_tag_keys, dataset_type_ocr, no_of_parallel_processes, shared_storage_path
from repository.ocr import OCRRepo
from utils.datasetutils import DatasetUtils
from kafkawrapper.producer import Producer
from events.error import ErrorEvent
from processtracker.processtracker import ProcessTracker
from events.metrics import MetricEvent


log = logging.getLogger('file')

mongo_instance = None
repo = OCRRepo()
utils = DatasetUtils()
prod = Producer()
error_event = ErrorEvent()
pt = ProcessTracker()
metrics = MetricEvent()


class OCRService:
    def __init__(self):
        pass

    # Method to load ASR Dataset
    def load_ocr_dataset(self, request):
        log.info("Loading OCR Dataset.....")
        try:
            metadata = request
            record = request["record"]
            ip_data = [record]
            batch_data, error_list, pt_list = [], [], []
            total, count, updates, batch = len(ip_data), 0, 0, parallel_ds_batch_size
            if ip_data:
                func = partial(self.get_enriched_ocr_data, metadata=metadata)
                pool_enrichers = multiprocessing.Pool(no_of_parallel_processes)
                enrichment_processors = pool_enrichers.map_async(func, ip_data).get()
                for result in enrichment_processors:
                    if result:
                        if result[0] == "INSERT":
                            if len(batch_data) == batch:
                                if metadata["userMode"] != user_mode_pseudo:
                                    persist_thread = threading.Thread(target=repo.insert, args=(batch_data,))
                                    persist_thread.start()
                                    persist_thread.join()
                                count += len(batch_data)
                                batch_data = []
                            batch_data.append(result[1])
                            pt_list.append({"status": "SUCCESS", "serviceRequestNumber": metadata["serviceRequestNumber"],
                                            "currentRecordIndex": metadata["currentRecordIndex"]})
                            metrics.build_metric_event(result[1], metadata, None, None)
                        elif result[0] == "FAILED":
                            error_list.append({"record": result[1], "code": "UPLOAD_FAILED",
                                               "datasetType": dataset_type_ocr,
                                               "serviceRequestNumber": metadata["serviceRequestNumber"],
                                               "message": "Upload to s3 bucket failed"})
                            pt_list.append({"status": "FAILED", "code": "UPLOAD_FAILED", "serviceRequestNumber": metadata["serviceRequestNumber"],
                                            "currentRecordIndex": metadata["currentRecordIndex"]})
                        elif result[0] == "UPDATE":
                            pt_list.append({"status": "SUCCESS", "serviceRequestNumber": metadata["serviceRequestNumber"],
                                            "currentRecordIndex": metadata["currentRecordIndex"]})
                            metrics.build_metric_event(result[2], metadata, None, True)
                            updates += 1
                        else:
                            error_list.append(
                                {"record": result[1], "code": "DUPLICATE_RECORD", "originalRecord": result[2],
                                 "datasetType": dataset_type_ocr,
                                 "serviceRequestNumber": metadata["serviceRequestNumber"],
                                 "message": "This record is already available in the system"})
                            pt_list.append({"status": "FAILED", "code": "DUPLICATE_RECORD", "serviceRequestNumber": metadata["serviceRequestNumber"],
                                            "currentRecordIndex": metadata["currentRecordIndex"]})
                pool_enrichers.close()
                if batch_data:
                    if metadata["userMode"] != user_mode_pseudo:
                        persist_thread = threading.Thread(target=repo.insert, args=(batch_data,))
                        persist_thread.start()
                        persist_thread.join()
                    count += len(batch_data)
            if error_list:
                error_event.create_error_event(error_list)
            for pt_rec in pt_list:
                pt.create_task_event(pt_rec)
            log.info(f'Done! -- INPUT: {total}, INSERTS: {count}, UPDATES: {updates}, "ERROR_LIST": {error_list}')
        except Exception as e:
            log.exception(e)
            return {"message": "EXCEPTION while loading dataset!!", "status": "FAILED"}
        return {"status": "SUCCESS", "total": total, "inserts": count, "updates": updates, "invalid": error_list}


    # Method to enrich asr dataset
    def get_enriched_ocr_data(self, data, metadata):
        try:
            record = self.get_ocr_dataset_internal({"imageHash": data["imageHash"], "groundTruthHash": data["groundTruthHash"]})
            if record:
                dup_data = self.enrich_duplicate_data(data, record, metadata)
                if dup_data:
                    repo.update(dup_data)
                    return "UPDATE", data, record
                else:
                    return "DUPLICATE", data, record
            insert_data = data
            for key in insert_data.keys():
                if key not in ocr_immutable_keys:
                    if not isinstance(insert_data[key], list):
                        insert_data[key] = [insert_data[key]]
            insert_data["datasetType"] = metadata["datasetType"]
            insert_data["datasetId"] = [metadata["datasetId"]]
            insert_data["tags"] = self.get_tags(insert_data)
            if metadata["datasetMode"] != 'pseudo':
                epoch = eval(str(time.time()).replace('.', '')[0:13])
                file_path = f'{shared_storage_path}{data["imageFilePath"]}'
                s3_file_name = f'{data["imageFilename"]}|{metadata["datasetId"]}|{epoch}'
                object_store_path = utils.upload_file(file_path, f'{aws_ocr_prefix}{s3_file_name}')
                if not object_store_path:
                    return "FAILED", insert_data, insert_data
                insert_data["objStorePath"] = object_store_path
            return "INSERT", insert_data, insert_data
        except Exception as e:
            log.exception(e)
            return None

    def enrich_duplicate_data(self, data, record, metadata):
        db_record = record
        found = False
        for key in data.keys():
            if key not in ocr_immutable_keys:
                if key not in db_record.keys():
                    found = True
                    db_record[key] = [data[key]]
                elif isinstance(data[key], list):
                    pairs = zip(data[key], db_record[key])
                    if any(x != y for x, y in pairs):
                        found = True
                        db_record[key].extend(data[key])
                else:
                    if isinstance(db_record[key], list):
                        if data[key] not in db_record[key]:
                            found = True
                            db_record[key].append(data[key])
        if found:
            db_record["datasetId"].append(metadata["datasetId"])
            db_record["tags"] = self.get_tags(record)
            return db_record

    def get_tags(self, insert_data):
        tag_details = {}
        for key in insert_data:
            if key not in ocr_non_tag_keys:
                tag_details[key] = insert_data[key]
        return list(utils.get_tags(tag_details))

    # Method for deduplication
    def get_ocr_dataset_internal(self, query):
        try:
            exclude = {"_id": False}
            data = repo.search(query, exclude, None, None)
            if data:
                return data[0]
            else:
                return None
        except Exception as e:
            log.exception(e)
            return None

    # Method for searching asr datasets
    def get_ocr_dataset(self, query):
        log.info(f'Fetching datasets..... | {datetime.now()}')
        pt.task_event_search(query, None)
        try:
            off = query["offset"] if 'offset' in query.keys() else offset
            lim = query["limit"] if 'limit' in query.keys() else limit
            db_query, tags = {}, []
            if 'sourceLanguage' in query.keys():
                db_query["sourceLanguage"] = query["sourceLanguage"]
            if 'collectionMode' in query.keys():
                tags.extend(query["collectionMode"])
            if 'collectionSource' in query.keys():
                tags.extend(query["collectionMode"])
            if 'license' in query.keys():
                tags.append(query["licence"])
            if 'domain' in query.keys():
                tags.extend(query["domain"])
            if 'datasetId' in query.keys():
                tags.append(query["datasetId"])
            if 'multipleContributors' in query.keys():
                db_query[f'collectionMethod.{query["multipleContributors"]}'] = {"$exists": True}
            if tags:
                db_query["tags"] = {"$all": tags}
            exclude = {"_id": False}
            data = repo.search(db_query, exclude, off, lim)
            result, query, count = data[0], data[1], data[2]
            log.info(f'Result --- Count: {count}, Query: {query}')
            if result:
                size = sample_size if count > sample_size else count
                path, path_sample = utils.push_result_to_s3(result, query["serviceRequestNumber"], size)
                if path:
                    op = {"serviceRequestNumber": query["serviceRequestNumber"], "count": count, "dataset": path, "datasetSample": path_sample}
                    pt.task_event_search(op, None)
                else:
                    log.error(f'There was an error while pushing result to S3')
                    error = {"code": "S3_UPLOAD_FAILED", "datasetType": dataset_type_ocr, "serviceRequestNumber": query["serviceRequestNumber"],
                                                   "message": "There was an error while pushing result to S3"}
                    op = {"serviceRequestNumber": query["serviceRequestNumber"], "count": 0, "sample": [], "dataset": None, "datasetSample": None}
                    pt.task_event_search(op, error)
            else:
                log.info(f'No records retrieved for SRN -- {query["serviceRequestNumber"]}')
                op = {"serviceRequestNumber": query["serviceRequestNumber"], "count": 0, "sample": [], "dataset": None,
                      "datasetSample": None}
                pt.task_event_search(op, None)
            log.info(f'Done!')
            return op
        except Exception as e:
            log.exception(e)
            return {"message": str(e), "status": "FAILED", "dataset": "NA"}

    def delete_ocr_dataset(self, delete_req):
        log.info(f'Deleting OCR datasets....')
        d, u = 0, 0
        try:
            records = self.get_ocr_dataset({"datasetId": delete_req["datasetId"]})
            for record in records:
                if len(record["datasetId"]) == 1:
                    repo.delete(record["id"])
                    utils.delete_from_s3(record["objStorePath"])
                    metrics.build_metric_event(record, delete_req, True, None)
                    d += 1
                else:
                    record["datasetId"].remove(delete_req["datasetId"])
                    record["tags"].remove(delete_req["datasetId"])
                    repo.update(record)
                    metrics.build_metric_event(record, delete_req, None, True)
                    u += 1
            op = {"serviceRequestNumber": delete_req["serviceRequestNumber"], "deleted": d, "updated": u}
            pt.task_event_search(op, None)
            log.info(f'Done!')
            return op
        except Exception as e:
            log.exception(e)
            log.error(f'There was an error while deleting records')
            error = {"code": "DELETE_FAILED", "datasetType": dataset_type_ocr,
                     "serviceRequestNumber": delete_req["serviceRequestNumber"],
                     "message": "There was an error while deleting records"}
            op = {"serviceRequestNumber": delete_req["serviceRequestNumber"], "deleted": d, "updated": u}
            pt.task_event_search(op, error)
            return None


# Log config
dictConfig({
    'version': 1,
    'formatters': {'default': {
        'format': '[%(asctime)s] {%(filename)s:%(lineno)d} %(threadName)s %(levelname)s in %(module)s: %(message)s',
    }},
    'handlers': {
        'info': {
            'class': 'logging.FileHandler',
            'level': 'DEBUG',
            'formatter': 'default',
            'filename': 'info.log'
        },
        'console': {
            'class': 'logging.StreamHandler',
            'level': 'DEBUG',
            'formatter': 'default',
            'stream': 'ext://sys.stdout',
        }
    },
    'loggers': {
        'file': {
            'level': 'DEBUG',
            'handlers': ['info', 'console'],
            'propagate': ''
        }
    },
    'root': {
        'level': 'DEBUG',
        'handlers': ['info', 'console']
    }
})