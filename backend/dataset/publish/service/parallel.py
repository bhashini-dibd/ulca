import hashlib
import logging
import multiprocessing
import threading
import uuid
from functools import partial
from logging.config import dictConfig
from configs.configs import ds_batch_size, no_of_parallel_processes, offset, limit, user_mode_pseudo, \
    sample_size, parallel_immutable_keys, parallel_non_tag_keys, dataset_type_parallel
from repository.parallel import ParallelRepo
from utils.datasetutils import DatasetUtils
from kafkawrapper.producer import Producer
from events.error import ErrorEvent
from processtracker.processtracker import ProcessTracker
from events.metrics import MetricEvent


log = logging.getLogger('file')

mongo_instance = None
repo = ParallelRepo()
utils = DatasetUtils()
prod = Producer()
error_event = ErrorEvent()
pt = ProcessTracker()
metrics = MetricEvent()


class ParallelService:
    def __init__(self):
        pass

    def load_parallel_dataset(self, request):
        log.info("Loading Dataset.....")
        try:
            metadata = request
            record = request["record"]
            ip_data = [record]
            batch_data, error_list, pt_list, metric_list = [], [], [], []
            total, count, updates, batch = len(ip_data), 0, 0, ds_batch_size
            if ip_data:
                func = partial(self.get_enriched_data, metadata=metadata)
                pool_enrichers = multiprocessing.Pool(no_of_parallel_processes)
                enrichment_processors = pool_enrichers.map_async(func, ip_data).get()
                for result in enrichment_processors:
                    if result:
                        if isinstance(result[0], list):
                            if len(batch_data) == batch:
                                if metadata["userMode"] != user_mode_pseudo:
                                    repo.insert(batch_data)
                                count += len(batch_data)
                                batch_data = []
                            batch_data.extend(result[0])
                            pt_list.append({"status": "SUCCESS", "serviceRequestNumber": metadata["serviceRequestNumber"],
                                            "currentRecordIndex": metadata["currentRecordIndex"]})
                            metrics.build_metric_event(result[0], metadata, None, None)
                        elif isinstance(result[0], str):
                            pt_list.append({"status": "SUCCESS", "serviceRequestNumber": metadata["serviceRequestNumber"],
                                            "currentRecordIndex": metadata["currentRecordIndex"]})
                            metrics.build_metric_event(result[1], metadata, None, True)
                            updates += 1
                        else:
                            error_list.append({"record": result[0], "originalRecord": result[1], "code": "DUPLICATE_RECORD",
                                               "datasetType": dataset_type_parallel, "datasetName": metadata["datasetName"],
                                               "serviceRequestNumber": metadata["serviceRequestNumber"],
                                               "message": "This record is already available in the system"})
                            pt_list.append({"status": "FAILED", "code": "DUPLICATE_RECORD", "serviceRequestNumber": metadata["serviceRequestNumber"],
                                            "currentRecordIndex": metadata["currentRecordIndex"]})
                pool_enrichers.close()
                if batch_data:
                    if metadata["userMode"] != user_mode_pseudo:
                        repo.insert(batch_data)
                    count += len(batch_data)
            if error_list:
                error_event.create_error_event(error_list)
            for pt_rec in pt_list:
                pt.create_task_event(pt_rec)
            log.info(f'Done! -- INPUT: {total}, INSERTS: {count}, UPDATES: {updates}, "ERROR_LIST": {len(error_list)}')
        except Exception as e:
            log.exception(e)
            return {"message": "EXCEPTION while loading dataset!!", "status": "FAILED"}
        return {"status": "SUCCESS", "total": total, "inserts": count, "updates": updates, "invalid": error_list}

    def get_enriched_data(self, data, metadata):
        insert_records, new_records = [], []
        try:
            records = self.get_dataset_internal({"hash": [data["sourceTextHash"], data["targetTextHash"]]})
            if records:
                for record in records:
                    if data["sourceTextHash"] in record["tags"] and data["targetTextHash"] in record["tags"]:
                        dup_data = self.enrich_duplicate_data(data, record, metadata)
                        if dup_data:
                            repo.update(dup_data)
                            return "UPDATE", dup_data
                        else:
                            return data, record
                    derived_data = self.enrich_derived_data(data, record, data["sourceTextHash"], data["targetTextHash"], metadata)
                    if derived_data:
                        new_records.append(derived_data)
            new_records.append(data)
            for record in new_records:
                if 'derived' not in record.keys():
                    for key in record.keys():
                        if key not in parallel_immutable_keys:
                            if not isinstance(record[key], list):
                                record[key] = [record[key]]
                    src_lang, tgt_lang = record["sourceLanguage"], record["targetLanguage"]
                    lang_map = {src_lang: record["sourceText"], tgt_lang: record["targetText"]}
                    hashed_key = ''
                    for key in sorted(lang_map.keys()):
                        hashed_key = hashed_key + str(lang_map[key])
                    record["hashedKey"] = str(hashlib.sha256(hashed_key.encode('utf-16')).hexdigest())
                    record["sk"] = ','.join(map(str, sorted([src_lang, tgt_lang])))
                    record["datasetType"] = metadata["datasetType"]
                    record["datasetId"] = [metadata["datasetId"]]
                    record["derived"] = False
                    record["tags"] = self.get_tags(record)
                insert_records.append(record)
            return insert_records, insert_records
        except Exception as e:
            log.exception(e)
            return None

    def get_dataset_internal(self, query):
        try:
            db_query = {"tags": {"$in": query["hash"]}}
            exclude = {"_id": False}
            data = repo.search_internal(db_query, exclude, None, None)
            if data:
                return data
            else:
                return None
        except Exception as e:
            log.exception(e)
            return None

    def enrich_duplicate_data(self, data, record, metadata):
        db_record = record
        if db_record["derived"]:
            for key in data.keys():
                if key not in parallel_immutable_keys:
                    if not isinstance(record[key], list):
                        db_record[key] = [data[key]]
                    else:
                        db_record[key] = data[key]
            db_record["derived"] = False
            record["tags"] = self.get_tags(record)
            return record
        else:
            found = False
            for key in data.keys():
                if key not in parallel_immutable_keys:
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
            else:
                return False

    def enrich_derived_data(self, data, record, src_hash, tgt_hash, metadata):
        derived_data = None
        if src_hash == record["sourceTextHash"]:
            if data["targetLanguage"] != record["targetLanguage"]:
                derived_data = {"sourceText": data["targetText"], "targetText": record["targetText"],
                                "sourceTextHash": data["targetTextHash"], "targetTextHash": record["targetTextHash"],
                            "sourceLanguage": data["targetLanguage"], "targetLanguage": record["targetLanguage"]}
        elif src_hash == record["targetTextHash"]:
            if data["targetLanguage"] != record["sourceLanguage"]:
                derived_data = {"sourceText": data["targetText"], "targetText": record["sourceText"],
                                "sourceTextHash": data["targetTextHash"], "targetTextHash": record["sourceTextHash"],
                            "sourceLanguage": data["targetLanguage"], "targetLanguage": record["sourceLanguage"]}
        elif tgt_hash == record["sourceTextHash"]:
            if data["sourceLanguage"] != record["targetLanguage"]:
                derived_data = {"sourceText": data["sourceText"], "targetText": record["targetText"],
                                "sourceTextHash": data["sourceTextHash"], "targetTextHash": record["targetTextHash"],
                            "sourceLanguage": data["sourceLanguage"], "targetLanguage": record["targetLanguage"]}
        elif tgt_hash == record["targetTextHash"]:
            if data["sourceLanguage"] != record["sourceLanguage"]:
                derived_data = {"sourceText": data["sourceText"], "targetText": record["sourceText"],
                                "sourceTextHash": data["sourceTextHash"], "targetTextHash": record["sourceTextHash"],
                            "sourceLanguage": data["sourceLanguage"], "targetLanguage": record["sourceLanguage"]}
        if not derived_data:
            return None
        for key in data.keys():
            if key not in parallel_immutable_keys:
                if key not in record.keys():
                    record[key] = [data[key]]
                elif isinstance(data[key], list):
                    record[key] = list(set(data[key]) | set(record[key]))
                else:
                    if isinstance(record[key], list):
                        if data[key] not in record[key]:
                            record[key].append(data[key])
                derived_data[key] = record[key]
        derived_data["datasetId"] = [metadata["datasetId"]]
        derived_data["datasetId"].extend(record["datasetId"])
        derived_data["datasetType"] = metadata["datasetType"]
        derived_data["derived"] = True
        derived_data["tags"] = self.get_tags(derived_data)
        derived_data["id"] = str(uuid.uuid4())
        return derived_data

    def get_tags(self, insert_data):
        tag_details = {}
        for key in insert_data:
            if key not in parallel_non_tag_keys:
                tag_details[key] = insert_data[key]
        return list(utils.get_tags(tag_details))

    def get_parallel_dataset(self, query):
        log.info(f'Fetching Parallel datasets for SRN -- {query["serviceRequestNumber"]}')
        pt.task_event_search(query, None)
        try:
            off = query["offset"] if 'offset' in query.keys() else offset
            lim = query["limit"] if 'limit' in query.keys() else limit
            db_query = {}
            score_query = {}
            if 'minScore' in query.keys():
                score_query["$gte"] = query["minScore"]
            if 'maxScore' in query.keys():
                score_query["$lte"] = query["maxScore"]
            if score_query:
                db_query["scoreQuery"] = {"data.score": score_query}
            if 'score' in query.keys():
                db_query["scoreQuery"] = {"data.score": query["score"]}
            tags, tgt_lang = [], []
            if 'sourceLanguage' in query.keys():
                db_query["sourceLanguage"] = query["sourceLanguage"][0]
            if 'targetLanguage' in query.keys():
                for tgt in query["targetLanguage"]:
                    tgt_lang.append(tgt)
            if 'collectionSource' in query.keys():
                tags.extend(query["collectionSource"])
            if 'collectionMode' in query.keys():
                tags.extend(query["collectionMode"])
            if 'license' in query.keys():
                tags.append(query["licence"])
            if 'domain' in query.keys():
                tags.extend(query["domain"])
            if 'datasetId' in query.keys():
                tags.append(query["datasetId"])
                db_query["derived"] = False
            if tags:
                db_query["tags"] = tags
            if tgt_lang:
                db_query["targetLanguage"] = tgt_lang
            if 'multipleContributors' in query.keys():
                db_query["multipleContributors"] = query["multipleContributors"]
            if 'groupBy' in query.keys():
                db_query["groupBy"] = True
                if 'countOfTranslations' in query.keys():
                    db_query["countOfTranslations"] = query["countOfTranslations"]
            data = repo.search(db_query, off, lim)
            result, pipeline, count = data[0], data[1], data[2]
            log.info(f'Result --- Count: {count}, Query: {query}, Pipeline: {pipeline}')
            if result:
                size = sample_size if count > sample_size else count
                path, path_sample = utils.push_result_to_s3(result, query["serviceRequestNumber"], size)
                if path:
                    op = {"serviceRequestNumber": query["serviceRequestNumber"], "count": count, "dataset": path, "datasetSample": path_sample}
                    pt.task_event_search(op, None)
                else:
                    log.error(f'There was an error while pushing result to S3')
                    error = {"code": "S3_UPLOAD_FAILED", "datasetType": dataset_type_parallel, "serviceRequestNumber": query["serviceRequestNumber"],
                                                   "message": "There was an error while pushing result to S3"}
                    op = {"serviceRequestNumber": query["serviceRequestNumber"], "count": 0, "sample": [], "dataset": None, "datasetSample": None}
                    pt.task_event_search(op, error)
            else:
                log.info(f'No records retrieved for SRN -- {query["serviceRequestNumber"]}')
                op = {"serviceRequestNumber": query["serviceRequestNumber"], "count": 0, "sample": [], "dataset": None,
                      "datasetSample": None}
                pt.task_event_search(op, None)
            log.info(f'Done!')
            op["pipeline"] = pipeline
            return op
        except Exception as e:
            log.exception(e)
            return {"message": str(e), "status": "FAILED", "dataset": "NA"}

    def delete_parallel_dataset(self, delete_req):
        log.info(f'Deleting PARALLEL datasets....')
        d, u = 0, 0
        try:
            records = self.get_parallel_dataset({"datasetId": delete_req["datasetId"]})
            for record in records:
                if len(record["datasetId"]) == 1:
                    repo.delete(record["id"])
                    metrics.build_metric_event(record, delete_req, True, None)
                    d += 1
                elif record["derived"]:
                    repo.delete(record["id"])
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
            error = {"code": "DELETE_FAILED", "datasetType": dataset_type_parallel,
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