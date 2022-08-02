import logging
import time
from logging.config import dictConfig
from configs.configs import ds_batch_size, offset, limit, user_mode_pseudo, \
    sample_size, transliteration_immutable_keys, transliteration_non_tag_keys, dataset_type_transliteration, \
    transliteration_updatable_keys
from repository.transliteration import TransliterationRepo
from utils.datasetutils import DatasetUtils
from kafkawrapper.producer import Producer
from events.error import ErrorEvent
from processtracker.processtracker import ProcessTracker
from events.metrics import MetricEvent
from .datasetservice import DatasetService
import re

log = logging.getLogger('file')

mongo_instance = None
repo = TransliterationRepo()
utils = DatasetUtils()
prod = Producer()
error_event = ErrorEvent()
pt = ProcessTracker()
metrics = MetricEvent()
service = DatasetService()


class TransliterationService:
    def __init__(self):
        pass

    '''
    Method to load Transliteration dataset into the mongo db
    params: request (record to be inserted)
    '''

    def load_transliteration_dataset(self, request):
        try:
            metadata, record = request, request["record"]
            error_list, pt_list, metric_list = [], [], []
            count, updates, batch = 0, 0, ds_batch_size
            if record:
                result = self.get_enriched_data(record, metadata)
                if result:
                    if result[0] == "INSERT":
                        if metadata["userMode"] != user_mode_pseudo:
                            repo.insert(result[1])
                            count += len(result[1])
                            metrics.build_metric_event(result[1], metadata, None, None)
                        pt.update_task_details(
                            {"status": "SUCCESS", "serviceRequestNumber": metadata["serviceRequestNumber"]})
                    elif result[0] == "UPDATE":
                        pt.update_task_details(
                            {"status": "SUCCESS", "serviceRequestNumber": metadata["serviceRequestNumber"]})
                        metric_record = (result[1], result[2])
                        metrics.build_metric_event(metric_record, metadata, None, True)
                        updates += 1
                    else:
                        error_list.append({"record": result[1], "originalRecord": result[2], "code": "DUPLICATE_RECORD",
                                           "datasetType": dataset_type_transliteration,
                                           "datasetName": metadata["datasetName"],
                                           "serviceRequestNumber": metadata["serviceRequestNumber"],
                                           "message": "This record is already available in the system"})
                        pt.update_task_details(
                            {"status": "FAILED", "serviceRequestNumber": metadata["serviceRequestNumber"]})
                else:
                    log.error(
                        f'INTERNAL ERROR: Failing record due to internal error: ID: {record["id"]}, SRN: {metadata["serviceRequestNumber"]}')
                    error_list.append(
                        {"record": record, "code": "INTERNAL_ERROR", "originalRecord": record,
                         "datasetType": dataset_type_transliteration, "datasetName": metadata["datasetName"],
                         "serviceRequestNumber": metadata["serviceRequestNumber"],
                         "message": "There was an exception while processing this record!"})
                    pt.update_task_details(
                        {"status": "FAILED", "serviceRequestNumber": metadata["serviceRequestNumber"]})
            if error_list:
                error_event.create_error_event(error_list)
            log.info(
                f'Transliteration - {metadata["userMode"]} - {metadata["serviceRequestNumber"]} - {record["id"]} -- I: {count}, U: {updates}, "E": {len(error_list)}')
        except Exception as e:
            log.exception(e)
            return {"message": "EXCEPTION while loading Transliteration dataset!!", "status": "FAILED"}
        return {"status": "SUCCESS", "total": 1, "inserts": count, "updates": updates, "invalid": error_list}

    '''
    Method to run dedup checks on the input record and enrich if needed.
    params: data (record to be inserted)
    params: metadata (metadata of record to be inserted)
    '''

    def get_enriched_data(self, data, metadata):
        insert_records, new_records = [], []
        records = self.get_dataset_internal({"hash": [data["sourceTextHash"], data["targetTextHash"]]}, False)
        try:
            if records:
                for record in records:
                    if record:
                        if data["sourceTextHash"] in record["tags"] and data["targetTextHash"] in record["tags"]:
                            dup_data = self.enrich_duplicate_data(data, record, metadata)
                            if dup_data:
                                if metadata["userMode"] != user_mode_pseudo:
                                    dup_data["lastModifiedOn"] = eval(str(time.time()).replace('.', '')[0:13])
                                    repo.update(dup_data)
                                return "UPDATE", dup_data, record
                            else:
                                return "DUPLICATE", data, record
            new_records.append(data)
            for obj in new_records:
                for key in obj.keys():
                    if key not in transliteration_immutable_keys and key not in transliteration_updatable_keys:
                        if not isinstance(obj[key], list):
                            obj[key] = [obj[key]]
                obj["datasetType"] = metadata["datasetType"]
                obj["datasetId"] = [metadata["datasetId"]]
                obj["tags"] = service.get_tags(obj, transliteration_non_tag_keys)
                obj["lastModifiedOn"] = obj["createdOn"] = eval(str(time.time()).replace('.', '')[0:13])
                insert_records.append(obj)
            return "INSERT", insert_records, insert_records
        except Exception as e:
            log.exception(f'Exception while getting enriched data: {e}', e)
            log.info(f'Data: {data}')
            i = 0
            for rec in records:
                log.info(f'Records {i}: {rec}')
                i += 1
            return None

    '''
    Method to fetch records from the DB
    params: query (query for search)
    '''

    def get_dataset_internal(self, query, all):
        try:
            if all:
                db_query = {"tags": {"$all": query["hash"]}}
            else:
                db_query = {"tags": {"$in": query["hash"]}}
            data = repo.search_internal(db_query, None, None, None)
            if data:
                return data
            else:
                return None
        except Exception as e:
            log.exception(e)
            return None

    '''
    Method to check and process duplicate records.
    params: data (record to be inserted)
    params: record (duplicate record found in the DB)
    params: data (record to be inserted)
    '''

    def enrich_duplicate_data(self, data, record, metadata):
        db_record = {}
        for key in record.keys():
            db_record[key] = record[key]
        found = False
        for key in data.keys():
            if key in transliteration_updatable_keys:
                if key not in db_record.keys():
                    found = True
                    db_record[key] = data[key]
                else:
                    if db_record[key] != data[key]:
                        found = True
                        db_record[key] = data[key]
                continue
            if key not in transliteration_immutable_keys:
                if key not in db_record.keys():
                    found = True
                    db_record[key] = [data[key]]
                elif isinstance(data[key], list):
                    val = data[key][0]
                    if isinstance(val, dict):
                        pairs = zip(data[key], db_record[key])
                        if any(x != y for x, y in pairs):
                            found = True
                            db_record[key].extend(data[key])
                    else:
                        for entry in data[key]:
                            if entry not in db_record[key]:
                                found = True
                                db_record[key].append(entry)
                else:
                    if isinstance(db_record[key], list):
                        eq = False
                        for r in db_record[key]:
                            eq = data[key] == r
                            if eq:
                                break
                        if not eq:
                            found = True
                            db_record[key].append(data[key])
                    else:
                        if db_record[key] != data[key]:
                            found = True
                            db_record[key] = [db_record[key]]
                            db_record[key].append(data[key])
                            db_record[key] = list(set(db_record[key]))
                        else:
                            db_record[key] = [db_record[key]]
        if found:
            db_record["datasetId"].append(metadata["datasetId"])
            dataset_ids = []
            for entry in db_record["datasetId"]:
                if entry not in dataset_ids:
                    dataset_ids.append(entry)
            db_record["datasetId"] = dataset_ids
            db_record["derived"] = False
            db_record["tags"] = service.get_tags(db_record, transliteration_non_tag_keys)
            return db_record
        else:
            return False

    '''
    Method to fetch Transliteration dataset from the DB based on various criteria
    params: query (query for search)
    '''

    def get_transliteration_dataset(self, query):
        log.info(f'Fetching Transliteration datasets for SRN -- {query["serviceRequestNumber"]}')
        pt.task_event_search(query, None, dataset_type_transliteration)
        try:
            off = query["offset"] if 'offset' in query.keys() else offset
            lim = query["limit"] if 'limit' in query.keys() else limit
            db_query, score_query = {}, {}
            tags, tgt_lang = [], []
            if 'sourceLanguage' in query.keys():
                db_query["sourceLanguage"] = query["sourceLanguage"][0] #source is always single
            if 'targetLanguage' in query.keys():
                for tgt in query["targetLanguage"]:
                    tgt_lang.append(tgt)
            if 'collectionMethod' in query.keys():
                tags.extend(query["collectionMethod"])
            if 'alignmentTool' in query.keys():
                tags.extend(query["alignmentTool"])
            if 'editingTool' in query.keys():
                tags.extend(query["editingTool"])
            if 'translationModel' in query.keys():
                tags.extend(query["translationModel"])
            if 'license' in query.keys():
                tags.extend(query["license"])
            if 'domain' in query.keys():
                tags.extend(query["domain"])
            if 'datasetId' in query.keys():
                tags.extend(query["datasetId"])
                db_query["derived"] = False
            if tags:
                db_query["tags"] = tags
            if tgt_lang:
                db_query["targetLanguage"] = tgt_lang
            if 'collectionSource' in query.keys():
                coll_source = [re.compile(cs, re.IGNORECASE) for cs in query["collectionSource"]]
                db_query["collectionSourceQuery"] = {"collectionSource": {"$in": coll_source}}
            if 'submitterName' in query.keys():
                db_query["submitterNameQuery"] = {"submitter": {"$elemMatch": {"name": query["submitterName"]}}}
            if 'minScore' in query.keys():
                score_query["$gte"] = query["minScore"]
            if 'maxScore' in query.keys():
                score_query["$lte"] = query["maxScore"]
            if score_query:
                db_query["scoreQuery"] = {"collectionMethod": {"$elemMatch": {"collectionDetails.alignmentScore": score_query}}}
            if 'score' in query.keys():
                db_query["scoreQuery"] = {"collectionMethod": {"$elemMatch": {"collectionDetails.alignmentScore": query["score"]}}}
            if 'multipleContributors' in query.keys():
                db_query["multipleContributors"] = query["multipleContributors"]
            else:
                db_query["multipleContributors"] = False
            if 'originalSourceSentence' in query.keys():
                db_query['originalSourceSentence'] = query['originalSourceSentence']
            else:
                db_query['originalSourceSentence'] = False
            if 'groupBy' in query.keys():
                db_query["groupBy"] = query["groupBy"]
                if 'countOfTranslations' in query.keys():
                    db_query["countOfTranslations"] = query["countOfTranslations"]
            else:
                db_query["groupBy"] = False
            data = repo.search(db_query, off, lim)
            result, pipeline, count = data[0], data[1], data[2]
            log.info(f'Result --- Count: {count}, Query: {query}, Pipeline: {pipeline}')
            if result:
                size = sample_size if count > sample_size else count
                path, path_sample = utils.push_result_to_object_store(result, query["serviceRequestNumber"], size)
                if path:
                    op = {"serviceRequestNumber": query["serviceRequestNumber"], "userID": query["userId"],
                          "count": count, "dataset": path, "datasetSample": path_sample}
                    pt.task_event_search(op, None, dataset_type_transliteration)
                else:
                    log.error(f'There was an error while pushing result to object store!')
                    error = {"code": "OS_UPLOAD_FAILED", "datasetType": dataset_type_transliteration,
                             "serviceRequestNumber": query["serviceRequestNumber"],
                             "message": "There was an error while pushing result to object store"}
                    op = {"serviceRequestNumber": query["serviceRequestNumber"], "userID": query["userId"],
                          "count": 0, "sample": [], "dataset": None, "datasetSample": None}
                    pt.task_event_search(op, error, dataset_type_transliteration)
            else:
                log.info(f'No records retrieved for SRN -- {query["serviceRequestNumber"]}')
                op = {"serviceRequestNumber": query["serviceRequestNumber"], "userID": query["userId"],
                      "count": 0, "sample": [], "dataset": None,
                      "datasetSample": None}
                pt.task_event_search(op, None, dataset_type_transliteration)
            log.info(f'Done!')
            op["pipeline"] = pipeline
            return op
        except Exception as e:
            log.exception(f'Exception in search: {e}', e)
            op = {"serviceRequestNumber": query["serviceRequestNumber"]}
            error = {"code": "EXCEPTION", "serviceRequestNumber": query["serviceRequestNumber"],
                     "message": f'Exception in search: {e}'}
            pt.task_event_search(op, error)
            return {"message": str(e), "status": "FAILED", "dataset": "NA"}

    '''
    Method to delete Transliteration dataset from the DB based on various criteria
    params: delete_req (request for deletion)
    '''

    def delete_transliteration_dataset(self, delete_req):
        log.info(f'Deleting Transliteration datasets....')
        d, u = 0, 0
        try:
            records = self.get_transliteration_dataset({"datasetId": delete_req["datasetId"]})
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
            error = {"code": "DELETE_FAILED", "datasetType": dataset_type_transliteration,
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
