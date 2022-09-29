import logging
import re
import time
from logging.config import dictConfig
from configs.configs import ds_batch_size, offset, limit, ocr_prefix, user_mode_pseudo, \
    sample_size, ocr_immutable_keys, ocr_non_tag_keys, dataset_type_ocr, no_of_parallel_processes, \
    ocr_search_ignore_keys, ocr_updatable_keys
from repository.ocr import OCRRepo
from utils.datasetutils import DatasetUtils
from kafkawrapper.producer import Producer
from events.error import ErrorEvent
from processtracker.processtracker import ProcessTracker
from events.metrics import MetricEvent
from.datasetservice import DatasetService


log = logging.getLogger('file')

mongo_instance = None
repo = OCRRepo()
utils = DatasetUtils()
prod = Producer()
error_event = ErrorEvent()
pt = ProcessTracker()
metrics = MetricEvent()
service = DatasetService()


class OCRService:
    def __init__(self):
        pass

    '''
    Method to load OCR dataset into the mongo db
    params: request (record to be inserted)
    '''
    def load_ocr_dataset(self, request):
        try:
            metadata, record = request, request["record"]
            error_list, pt_list, metric_list = [], [], []
            count, updates, batch = 0, 0, ds_batch_size
            if record:
                result = self.get_enriched_ocr_data(record, metadata)
                if result:
                    if result[0] == "INSERT":
                        if metadata["userMode"] != user_mode_pseudo:
                            repo.insert([result[1]])
                            count += 1
                            metrics.build_metric_event(result[1], metadata, None, None)
                        pt.update_task_details({"status": "SUCCESS", "serviceRequestNumber": metadata["serviceRequestNumber"]})
                    elif result[0] == "UPDATE":
                        pt.update_task_details({"status": "SUCCESS", "serviceRequestNumber": metadata["serviceRequestNumber"]})
                        metric_record = (result[1], result[2])
                        metrics.build_metric_event(metric_record, metadata, None, True)
                        updates += 1
                    elif result[0] == "FAILED":
                        error_list.append(
                            {"record": result[1], "code": "UPLOAD_FAILED", "datasetName": metadata["datasetName"],
                             "datasetType": dataset_type_ocr, "serviceRequestNumber": metadata["serviceRequestNumber"],
                             "message": "Upload of image file to object store failed"})
                        pt.update_task_details({"status": "FAILED", "serviceRequestNumber": metadata["serviceRequestNumber"]})
                    else:
                        error_list.append({"record": result[1], "code": "DUPLICATE_RECORD", "originalRecord": result[2],
                                           "datasetType": dataset_type_ocr,
                                           "serviceRequestNumber": metadata["serviceRequestNumber"],
                                           "message": "This record is already available in the system",
                                           "datasetName": metadata["datasetName"]})
                        pt.update_task_details({"status": "FAILED", "serviceRequestNumber": metadata["serviceRequestNumber"]})
                else:
                    log.error(f'INTERNAL ERROR: Failing record due to internal error: ID: {record["id"]}, SRN: {metadata["serviceRequestNumber"]}')
                    error_list.append(
                        {"record": record, "code": "INTERNAL_ERROR", "originalRecord": record,
                         "datasetType": dataset_type_ocr, "datasetName": metadata["datasetName"],
                         "serviceRequestNumber": metadata["serviceRequestNumber"],
                         "message": "There was an exception while processing this record!"})
                    pt.update_task_details(
                        {"status": "FAILED", "serviceRequestNumber": metadata["serviceRequestNumber"]})
            if error_list:
                error_event.create_error_event(error_list)
            log.info(f'OCR - {metadata["userMode"]} - {metadata["serviceRequestNumber"]} - {record["id"]} -- I: {count}, U: {updates}, "E": {len(error_list)}')
        except Exception as e:
            log.exception(e)
            return {"message": "EXCEPTION while loading OCR dataset!!", "status": "FAILED"}
        return {"status": "SUCCESS", "total": 1, "inserts": count, "updates": updates, "invalid": error_list}

    '''
    Method to run dedup checks on the input record and enrich if needed.
    params: data (record to be inserted)
    params: metadata (metadata of record to be inserted)
    '''
    def get_enriched_ocr_data(self, data, metadata):
        try:
            hashes = [data["imageHash"], data["groundTruthHash"]]
            record = self.get_ocr_dataset_internal({"tags": {"$all": hashes}})
            if record:
                dup_data = service.enrich_duplicate_data(data, record, metadata, ocr_immutable_keys, ocr_updatable_keys, ocr_non_tag_keys)
                if dup_data:
                    if metadata["userMode"] != user_mode_pseudo:
                        dup_data["lastModifiedOn"] = eval(str(time.time()).replace('.', '')[0:13])
                        repo.update(dup_data)
                    return "UPDATE", dup_data, record
                else:
                    return "DUPLICATE", data, record
            insert_data = data
            for key in insert_data.keys():
                if key not in ocr_immutable_keys and key not in ocr_updatable_keys:
                    if not isinstance(insert_data[key], list):
                        insert_data[key] = [insert_data[key]]
            insert_data["datasetType"] = metadata["datasetType"]
            insert_data["datasetId"] = [metadata["datasetId"]]
            insert_data["tags"] = service.get_tags(insert_data, ocr_non_tag_keys)
            if metadata["userMode"] != user_mode_pseudo:
                epoch = eval(str(time.time()).replace('.', '')[0:13])
                s3_file_name = f'{metadata["datasetId"]}|{epoch}|{data["imageFilename"]}'
                object_store_path = utils.upload_file(data["fileLocation"], ocr_prefix, s3_file_name)
                if not object_store_path:
                    return "FAILED", insert_data, insert_data
                insert_data["objStorePath"] = object_store_path
                insert_data["lastModifiedOn"] = insert_data["createdOn"] = eval(str(time.time()).replace('.', '')[0:13])
            return "INSERT", insert_data, insert_data
        except Exception as e:
            log.exception(f'Exception while getting enriched data: {e}', e)
            return None
    '''
    Method to fetch records from the DB
    params: query (query for search)
    '''
    def get_ocr_dataset_internal(self, query):
        try:
            data = repo.search(query, None, None, None)
            if data:
                return data[0]
            else:
                return None
        except Exception as e:
            log.exception(e)
            return None

    '''
    Method to fetch OCR dataset from the DB based on various criteria
    params: query (query for search)
    '''
    def get_ocr_dataset(self, query):
        log.info(f'Fetching OCR datasets for SRN -- {query["serviceRequestNumber"]}')
        pt.task_event_search(query, None, dataset_type_ocr)
        try:
            off = query["offset"] if 'offset' in query.keys() else offset
            lim = query["limit"] if 'limit' in query.keys() else limit
            db_query, tags = {}, []
            if 'sourceLanguage' in query.keys():
                db_query["sourceLanguage"] = {"$in": query["sourceLanguage"]}
            if 'collectionMethod' in query.keys():
                tags.extend(query["collectionMethod"])
            if 'collectionDescription' in query.keys():
                tags.extend(query["collectionDescription"])
            if 'ocrTool' in query.keys():
                tags.extend(query["ocrTool"])
            if 'license' in query.keys():
                tags.extend(query["license"])
            if 'domain' in query.keys():
                tags.extend(query["domain"])
            if 'format' in query.keys():
                tags.extend(query["format"])
            if 'dpi' in query.keys():
                tags.extend(query["dpi"])
            if 'imageTextType' in query.keys():
                tags.extend(query["imageTextType"])
            if 'datasetId' in query.keys():
                tags.append(query["datasetId"])
            if 'collectionSource' in query.keys():
                coll_source = [re.compile(cs, re.IGNORECASE) for cs in query["collectionSource"]]
                db_query["collectionSource"] = {"$in": coll_source}
            if 'submitterName' in query.keys():
                db_query["submitter"] = {"$elemMatch": {"name": query["submitterName"]}}
            if 'multipleContributors' in query.keys():
                if query['multipleContributors']:
                    db_query[f'collectionMethod.1'] = {"$exists": True}
            if tags:
                db_query["tags"] = {"$all": tags}
            exclude = {"_id": False}
            for key in ocr_search_ignore_keys:
                exclude[key] = False
            result = repo.search(db_query, exclude, off, lim)
            count = len(result)
            log.info(f'Result --- Count: {count}, Query: {query}')
            if result:
                size = sample_size if count > sample_size else count
                path, path_sample = utils.push_result_to_object_store(result, query["serviceRequestNumber"], size)
                if path:
                    op = {"serviceRequestNumber": query["serviceRequestNumber"], "userID": query["userId"],
                          "count": count, "dataset": path, "datasetSample": path_sample}
                    pt.task_event_search(op, None, dataset_type_ocr)
                else:
                    log.error(f'There was an error while pushing result to S3')
                    error = {"code": "OS_UPLOAD_FAILED", "datasetType": dataset_type_ocr, "serviceRequestNumber": query["serviceRequestNumber"],
                                                   "message": "There was an error while pushing result to object store"}
                    op = {"serviceRequestNumber": query["serviceRequestNumber"], "userID": query["userId"],
                          "count": 0, "sample": [], "dataset": None, "datasetSample": None}
                    pt.task_event_search(op, error, dataset_type_ocr)
            else:
                log.info(f'No records retrieved for SRN -- {query["serviceRequestNumber"]}')
                op = {"serviceRequestNumber": query["serviceRequestNumber"], "userID": query["userId"],
                      "count": 0, "sample": [], "dataset": None, "datasetSample": None}
                pt.task_event_search(op, None, dataset_type_ocr)
            log.info(f'Done!')
            return op
        except Exception as e:
            log.exception(e)
            return {"message": str(e), "status": "FAILED", "dataset": "NA"}

    '''
    Method to delete OCR dataset from the DB based on various criteria
    params: delete_req (request for deletion)
    '''
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