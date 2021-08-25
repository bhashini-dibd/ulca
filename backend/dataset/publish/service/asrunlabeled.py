import logging
import time
from logging.config import dictConfig
from configs.configs import ds_batch_size, \
    sample_size, offset, limit, asr_unlabeled_immutable_keys, asr_unlabeled_non_tag_keys, dataset_type_asr, \
    user_mode_pseudo, \
    asr_unlabeled_search_ignore_keys, asr_unlabeled_updatable_keys, dataset_type_asr_unlabeled, asr_unlabeled_prefix
from repository.asrunlabeled import ASRUnlabeledRepo
from utils.datasetutils import DatasetUtils
from kafkawrapper.producer import Producer
from events.error import ErrorEvent
from processtracker.processtracker import ProcessTracker
from events.metrics import MetricEvent
from .datasetservice import DatasetService

log = logging.getLogger('file')

mongo_instance = None
repo = ASRUnlabeledRepo()
utils = DatasetUtils()
prod = Producer()
error_event = ErrorEvent()
pt = ProcessTracker()
metrics = MetricEvent()
service = DatasetService()

class ASRUnlabeledService:
    def __init__(self):
        pass

    '''
    Method to load ASR Unlabeled dataset into the mongo db
    params: request (record to be inserted)
    '''
    def load_asr_unlabeled_dataset(self, request):
        try:
            metadata, record = request, request["record"]
            error_list, pt_list, metric_list = [], [], []
            count, updates, batch = 0, 0, ds_batch_size
            if record:
                result = self.get_enriched_asr_unlabeled_data(record, metadata)
                if result:
                    if result[0] == "INSERT":
                        if metadata["userMode"] != user_mode_pseudo:
                            repo.insert([result[1]])
                            count += 1
                            metrics.build_metric_event(result[1], metadata, None, None)
                        pt.update_task_details({"status": "SUCCESS", "serviceRequestNumber": metadata["serviceRequestNumber"],
                                                "durationInSeconds": record["durationInSeconds"], "datasetType": dataset_type_asr_unlabeled})
                    elif result[0] == "UPDATE":
                        pt.update_task_details({"status": "SUCCESS", "serviceRequestNumber": metadata["serviceRequestNumber"],
                                                "durationInSeconds": record["durationInSeconds"], "datasetType": dataset_type_asr_unlabeled, "isUpdate": True})
                        metric_record = (result[1], result[2])
                        metrics.build_metric_event(metric_record, metadata, None, True)
                        updates += 1
                    elif result[0] == "FAILED":
                        error_list.append(
                            {"record": result[1], "code": "UPLOAD_FAILED", "datasetName": metadata["datasetName"],
                             "datasetType": dataset_type_asr_unlabeled, "serviceRequestNumber": metadata["serviceRequestNumber"],
                             "message": "Upload of audio file to object store failed"})
                        pt.update_task_details({"status": "FAILED", "serviceRequestNumber": metadata["serviceRequestNumber"],
                                                "durationInSeconds": record["durationInSeconds"], "datasetType": dataset_type_asr_unlabeled})
                    else:
                        error_list.append({"record": result[1], "code": "DUPLICATE_RECORD", "originalRecord": result[2],
                                           "datasetType": dataset_type_asr_unlabeled,
                                           "serviceRequestNumber": metadata["serviceRequestNumber"],
                                           "message": "This record is already available in the system",
                                           "datasetName": metadata["datasetName"]})
                        pt.update_task_details({"status": "FAILED", "serviceRequestNumber": metadata["serviceRequestNumber"],
                                                "durationInSeconds": record["durationInSeconds"], "datasetType": dataset_type_asr_unlabeled})
                else:
                    log.error(f'INTERNAL ERROR: Failing record due to internal error: ID: {record["id"]}, SRN: {metadata["serviceRequestNumber"]}')
                    error_list.append(
                        {"record": record, "code": "INTERNAL_ERROR", "originalRecord": record,
                         "datasetType": dataset_type_asr_unlabeled, "datasetName": metadata["datasetName"],
                         "serviceRequestNumber": metadata["serviceRequestNumber"],
                         "message": "There was an exception while processing this record!"})
                    pt.update_task_details(
                        {"status": "FAILED", "serviceRequestNumber": metadata["serviceRequestNumber"], "durationInSeconds": record["durationInSeconds"],
                         "datasetType": dataset_type_asr_unlabeled})
            if error_list:
                error_event.create_error_event(error_list)
            log.info(f'ASR UNLABELED - {metadata["serviceRequestNumber"]} - {record["id"]} -- I: {count}, U: {updates}, "E": {len(error_list)}')
        except Exception as e:
            log.exception(e)
            return {"message": "EXCEPTION while loading ASR UNLABELED dataset!!", "status": "FAILED"}
        return {"status": "SUCCESS", "total": 1, "inserts": count, "updates": updates, "invalid": error_list}

    '''
    Method to run dedup checks on the input record and enrich if needed.
    params: data (record to be inserted)
    params: metadata (metadata of record to be inserted)
    '''
    def get_enriched_asr_unlabeled_data(self, data, metadata):
        try:
            record = self.get_asr_unlabeled_dataset_internal({"tags": {"$all": [data["audioHash"]]}})
            if record:
                dup_data = service.enrich_duplicate_data(data, record, metadata, asr_unlabeled_immutable_keys,
                                                         asr_unlabeled_updatable_keys, asr_unlabeled_non_tag_keys)
                if dup_data:
                    dup_data["lastModifiedOn"] = eval(str(time.time()).replace('.', '')[0:13])
                    if metadata["userMode"] != user_mode_pseudo:
                        repo.update(dup_data)
                    return "UPDATE", dup_data, record
                else:
                    return "DUPLICATE", data, record
            insert_data = data
            for key in insert_data.keys():
                if key not in asr_unlabeled_immutable_keys and key not in asr_unlabeled_updatable_keys:
                    if not isinstance(insert_data[key], list):
                        insert_data[key] = [insert_data[key]]
            insert_data["datasetType"] = metadata["datasetType"]
            insert_data["datasetId"] = [metadata["datasetId"]]
            insert_data["tags"] = service.get_tags(insert_data, asr_unlabeled_non_tag_keys)
            if metadata["userMode"] != user_mode_pseudo:
                epoch = eval(str(time.time()).replace('.', '')[0:13])
                s3_file_name = f'{metadata["datasetId"]}|{epoch}|{data["audioFilename"]}'
                object_store_path = utils.upload_file(data["fileLocation"], asr_unlabeled_prefix, s3_file_name)
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
    def get_asr_unlabeled_dataset_internal(self, query):
        try:
            exclude = {"_id": False}
            data = repo.search(query, exclude, None, None)
            if data:
                asr_data = data[0]
                if asr_data:
                    return asr_data[0]
                else:
                    return None
            else:
                return None
        except Exception as e:
            log.exception(e)
            return None

    '''
    Method to fetch ASR Unlabeled dataset from the DB based on various criteria
    params: query (query for search)
    '''
    def get_asr_unlabeled_dataset(self, query):
        log.info(f'Fetching ASR UNLABELED datasets for SRN -- {query["serviceRequestNumber"]}')
        pt.task_event_search(query, None)
        try:
            off = query["offset"] if 'offset' in query.keys() else offset
            lim = query["limit"] if 'limit' in query.keys() else limit
            db_query, tags = {}, []
            if 'sourceLanguage' in query.keys():
                db_query["sourceLanguage"] = {"$in": query["sourceLanguage"]}
            if 'collectionMode' in query.keys():
                tags.extend(query["collectionMode"])
            if 'collectionSource' in query.keys():
                tags.extend(query["collectionMode"])
            if 'license' in query.keys():
                tags.extend(query["licence"])
            if 'domain' in query.keys():
                tags.extend(query["domain"])
            if 'channel' in query.keys():
                tags.append(query["channel"])
            if 'gender' in query.keys():
                tags.append(query["gender"])
            if 'datasetId' in query.keys():
                tags.append(query["datasetId"])
            if 'multipleContributors' in query.keys():
                if query['multipleContributors']:
                    db_query[f'collectionMethod.1'] = {"$exists": True}
            if tags:
                db_query["tags"] = {"$all": tags}
            exclude = {"_id": False}
            for key in asr_unlabeled_search_ignore_keys:
                exclude[key] = False
            result, hours = repo.search(db_query, exclude, off, lim)
            count = len(result)
            log.info(f'Result --- Count: {count}, Query: {query}')
            log.info(f'Result --- Hours: {hours}, Query: {query}')
            if result:
                size = sample_size if count > sample_size else count
                path, path_sample = utils.push_result_to_object_store(result, query["serviceRequestNumber"], size)
                if path:
                    op = {"serviceRequestNumber": query["serviceRequestNumber"], "count": hours, "dataset": path, "datasetSample": path_sample}
                    pt.task_event_search(op, None)
                else:
                    log.error(f'There was an error while pushing result to S3')
                    error = {"code": "OS_UPLOAD_FAILED", "datasetType": dataset_type_asr_unlabeled, "serviceRequestNumber": query["serviceRequestNumber"],
                                                   "message": "There was an error while pushing result to object store"}
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

    '''
    Method to delete ASR Unlabeled dataset from the DB based on various criteria
    params: delete_req (request for deletion)
    '''
    def delete_asr_unlabeled_dataset(self, delete_req):
        log.info(f'Deleting ASR Unlabeled datasets....')
        d, u = 0, 0
        try:
            records = self.get_asr_unlabeled_dataset({"datasetId": delete_req["datasetId"]})
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
            error = {"code": "DELETE_FAILED", "datasetType": dataset_type_asr,
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