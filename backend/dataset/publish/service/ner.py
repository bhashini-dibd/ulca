import logging
import multiprocessing
import re
import time
from datetime import datetime
from functools import partial
from logging.config import dictConfig
from configs.configs import ds_batch_size, no_of_parallel_processes, offset, limit, \
    sample_size, mono_non_tag_keys, mono_immutable_keys, dataset_type_ner, user_mode_pseudo, \
    mono_search_ignore_keys, mono_updatable_keys
from repository.ner import NERRepo
from utils.datasetutils import DatasetUtils
from kafkawrapper.producer import Producer
from events.error import ErrorEvent
from processtracker.processtracker import ProcessTracker
from events.metrics import MetricEvent
from .datasetservice import DatasetService

log = logging.getLogger('file')

repo = NERRepo()
utils = DatasetUtils()
prod = Producer()
error_event = ErrorEvent()
pt = ProcessTracker()
metrics = MetricEvent()
service = DatasetService()


class NERService:
    def __init__(self):
        pass

    '''
    Method to load ner dataset into the mongo db
    params: request (record to be inserted)
    '''
    def load_ner_dataset(self, request):
        try:
            metadata, record = request, request["record"]
            error_list, pt_list, metric_list = [], [], []
            count, updates, batch = 0, 0, ds_batch_size
            if record:
                result = self.get_enriched_data(record, metadata)
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
                    else:
                        error_list.append(
                            {"record": result[1], "code": "DUPLICATE_RECORD", "originalRecord": result[2],
                             "datasetType": dataset_type_ner, "datasetName": metadata["datasetName"],
                             "serviceRequestNumber": metadata["serviceRequestNumber"],
                             "message": "This record is already available in the system"})
                        pt.update_task_details({"status": "FAILED", "serviceRequestNumber": metadata["serviceRequestNumber"]})
                else:
                    log.error(f'INTERNAL ERROR: Failing record due to internal error: ID: {record["id"]}, SRN: {metadata["serviceRequestNumber"]}')
                    error_list.append(
                        {"record": record, "code": "INTERNAL_ERROR", "originalRecord": record,
                         "datasetType": dataset_type_ner, "datasetName": metadata["datasetName"],
                         "serviceRequestNumber": metadata["serviceRequestNumber"],
                         "message": "There was an exception while processing this record!"})
                    pt.update_task_details(
                        {"status": "FAILED", "serviceRequestNumber": metadata["serviceRequestNumber"]})
            if error_list:
                error_event.create_error_event(error_list)
            log.info(f'Mono - {metadata["userMode"]} - {metadata["serviceRequestNumber"]} - {record["id"]} -- I: {count}, U: {updates}, "E": {len(error_list)}')
        except Exception as e:
            log.exception(e)
            return {"message": "EXCEPTION while loading ner dataset!!", "status": "FAILED"}
        return {"status": "SUCCESS", "total": 1, "inserts": count, "updates": updates, "invalid": error_list}

    '''
    Method to run dedup checks on the input record and enrich if needed.
    params: data (record to be inserted)
    params: metadata (metadata of record to be inserted)
    '''
    def get_enriched_data(self, data, metadata):
        try:
            record = self.get_ner_dataset_internal({"tags": {"$all": [data["sourceTextHash"],data['nerDataHash']]}})
            if record:
                dup_data = service.enrich_duplicate_data(data, record, metadata, mono_immutable_keys, mono_updatable_keys, mono_non_tag_keys)
                if dup_data:
                    if metadata["userMode"] != user_mode_pseudo:
                        dup_data["lastModifiedOn"] = eval(str(time.time()).replace('.', '')[0:13])
                        repo.update(dup_data)
                    return "UPDATE", dup_data, record
                else:
                    return "DUPLICATE", data, record
            insert_data = data
            for key in insert_data.keys():
                if key not in mono_immutable_keys and key not in mono_updatable_keys:
                    if not isinstance(insert_data[key], list):
                        insert_data[key] = [insert_data[key]]
            insert_data["datasetType"] = metadata["datasetType"]
            insert_data["datasetId"] = [metadata["datasetId"]]
            insert_data["tags"] = service.get_tags(insert_data, mono_non_tag_keys)
            insert_data["lastModifiedOn"] = insert_data["createdOn"] = eval(str(time.time()).replace('.', '')[0:13])
            return "INSERT", insert_data, insert_data
        except Exception as e:
            log.exception(f'Exception while getting enriched data: {e}', e)
            return None

    '''
    Method to fetch records from the DB
    params: query (query for search)
    '''
    def get_ner_dataset_internal(self, query):
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
    Method to fetch ner dataset from the DB based on various criteria
    params: query (query for search)
    '''
    def get_ner_dataset(self, query):
        log.info(f'Fetching ner datasets for SRN -- {query["serviceRequestNumber"]}')
        pt.task_event_search(query, None, dataset_type_ner)
        try:
            off = query["offset"] if 'offset' in query.keys() else offset
            lim = query["limit"] if 'limit' in query.keys() else limit
            db_query, tags = {}, []
            if 'sourceLanguage' in query.keys():
                db_query["sourceLanguage"] = {"$in": query["sourceLanguage"]}
            if 'collectionMethod' in query.keys():
                tags.extend(query["collectionMethod"])
            if 'license' in query.keys():
                tags.extend(query["license"])
            if 'domain' in query.keys():
                tags.extend(query["domain"])
            if 'datasetId' in query.keys():
                tags.extend(query["datasetId"])
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
            for key in mono_search_ignore_keys:
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
                    pt.task_event_search(op, None, dataset_type_ner)
                else:
                    log.error(f'There was an error while pushing result to S3')
                    error = {"code": "S3_UPLOAD_FAILED", "datasetType": dataset_type_ner, "serviceRequestNumber": query["serviceRequestNumber"],
                                                   "message": "There was an error while pushing result to S3"}
                    op = {"serviceRequestNumber": query["serviceRequestNumber"], "userID": query["userId"],
                          "count": 0, "sample": [], "dataset": None, "datasetSample": None}
                    pt.task_event_search(op, error, dataset_type_ner)
            else:
                log.info(f'No records retrieved for SRN -- {query["serviceRequestNumber"]}')
                op = {"serviceRequestNumber": query["serviceRequestNumber"], "userID": query["userId"],
                      "count": 0, "sample": [], "dataset": None, "datasetSample": None}
                pt.task_event_search(op, None, dataset_type_ner)
            log.info(f'Done!')
            return op
        except Exception as e:
            log.exception(e)
            return {"message": str(e), "status": "FAILED", "dataset": "NA"}

    '''
    Method to delete ner dataset from the DB based on various criteria
    params: delete_req (request for deletion)
    '''
    def delete_mono_dataset(self, delete_req):
        log.info(f'Deleting ner datasets....')
        d, u = 0, 0
        try:
            records = self.get_ner_dataset({"datasetId": delete_req["datasetId"]})
            for record in records:
                if len(record["datasetId"]) == 1:
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
            error = {"code": "DELETE_FAILED", "datasetType": dataset_type_ner,
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