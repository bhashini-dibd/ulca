import logging
import re
import time
from logging.config import dictConfig
from configs.configs import ds_batch_size, \
    sample_size, offset, limit, asr_unlabeled_immutable_keys, asr_unlabeled_non_tag_keys, dataset_type_asr, \
    user_mode_pseudo, \
    asr_unlabeled_search_ignore_keys, asr_unlabeled_updatable_keys, dataset_type_asr_unlabeled, asr_unlabeled_prefix, submiter_name_whitelist_enabled, submitter_names_to_whitelist
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
            # log.info(f"Test50: Start {request}")
            metadata, record = request, request["record"]
            error_list, pt_list, metric_list = [], [], []
            count, updates, batch = 0, 0, ds_batch_size
            if record:
                result = self.get_enriched_asr_unlabeled_data(record, metadata)
                #log.info(f"Test60 Result: {result}")
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
            log.info(f'ASR UNLABELED - {metadata["userMode"]} - {metadata["serviceRequestNumber"]} - {record["id"]} -- I: {count}, U: {updates}, "E": {len(error_list)}')
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
            imageHashExists = False
            #check if age is missing but exactAge is present, autofill it
            if 'exactAge' in data.keys():
                if 'age' not in data.keys() or data['age'] is None:
                    if data['exactAge'] in range(1,11):
                        data["age"] = "1-10"
                    elif data['exactAge'] in range(1,21):
                        data["age"] = "11-20"
                    elif data['exactAge'] in range(21,61):
                        data["age"] = "21-60"
                    elif data['exactAge'] in range(61,101):
                        data["age"] = "61-100"
                    
            # log.info(f"Test55 {data}")
            if 'imageHash' in data.keys():
                record = self.get_asr_unlabeled_dataset_internal({"$or": [{"tags": data["imageHash"]},
                                                                {"tags": data["audioHash"]}]
                                                        })           
            else: 
                record = self.get_asr_unlabeled_dataset_internal({"tags": {"$all": [data["audioHash"]]}})
            #if 'exactAge' in data.keys():
            #    data['age'] = service.get_age(data['exactAge'])
            if record:
                for each_record in record:
                    #log.info(f"Test58 {each_record}")
                    if 'imageHash' in data.keys():
                        if data['imageHash'] in each_record['tags']:
                            imageHashExists = True
                            data['refImgStorePath'] = each_record['refImgStorePath']
                    if data['audioHash'] in each_record['tags']:
                        if isinstance(each_record, list):
                            each_record = each_record[0]
                        #log.info(f"Test60 {each_record}")
                        dup_data = service.enrich_duplicate_data(data, each_record, metadata, asr_unlabeled_immutable_keys,
                                                                asr_unlabeled_updatable_keys, asr_unlabeled_non_tag_keys)
                        #log.info(f"Test60 {dup_data}")                        
                        if dup_data:
                            if metadata["userMode"] != user_mode_pseudo:
                                dup_data["lastModifiedOn"] = eval(str(time.time()).replace('.', '')[0:13])
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
                #insert_data["objStorePath"] = "Something"
                #insert_data["refImgStorePath"] = "Else"
                epoch = eval(str(time.time()).replace('.', '')[0:13])
                data['audioFilename'] = data['audioFilename'].split('/')[-1]
                s3_file_name = f'{metadata["datasetId"]}|{epoch}|{data["audioFilename"]}'
                object_store_path = utils.upload_file(data["fileLocation"], asr_unlabeled_prefix, s3_file_name)
                if not object_store_path:
                    return "FAILED", insert_data, insert_data
                insert_data["objStorePath"] = object_store_path
                insert_data["lastModifiedOn"] = insert_data["createdOn"] = eval(str(time.time()).replace('.', '')[0:13])
                if 'imageFileLocation' in data.keys() and imageHashExists == False:
                    epoch = eval(str(time.time()).replace('.', '')[0:13])
                    if isinstance(data['imageFilename'],list):
                        data['imageFilename'] = data['imageFilename'][0]
                    imageFileName = data['imageFilename'].split('/')[-1]
                    s3_img_file_name = f'{metadata["datasetId"]}|{epoch}|{imageFileName}'
                    img_object_store_path = utils.upload_file(data["imageFileLocation"], asr_unlabeled_prefix, s3_img_file_name)
                    # log.info(f"Test57 {img_object_store_path}")
                    if not img_object_store_path:
                        return "FAILED", insert_data, insert_data
                    else:
                        insert_data["refImgStorePath"] = img_object_store_path
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
            data = repo.search(query, None, None, None)
            if data:
                asr_data = data[0]
                if asr_data:
                    return asr_data
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
        pt.task_event_search(query, None, dataset_type_asr_unlabeled)
        try:
            off = query["offset"] if 'offset' in query.keys() else offset
            lim = query["limit"] if 'limit' in query.keys() else limit
            db_query, tags = {}, []
            if 'sourceLanguage' in query.keys():
                db_query["sourceLanguage"] = {"$in": query["sourceLanguage"]}
            if 'mixedDataSource' in query.keys():
                db_query["mixedDataSource"] = query["mixedDataSource"]
                if 'assertLanguage' in query.keys() and len(query["assertLanguage"])> 0 :
                    db_query["assertLanguage"] = {"$in": query["assertLanguage"]}
            if 'collectionMethod' in query.keys():
                tags.extend(query["collectionMethod"])
            if 'license' in query.keys():
                tags.extend(query["license"])
            if 'domain' in query.keys():
                tags.extend(query["domain"])
            if 'channel' in query.keys():
                tags.extend(query["channel"])
            if 'gender' in query.keys():
                tags.extend(query["gender"])
            if 'format' in query.keys():
                tags.extend(query["format"])
            if 'bitsPerSample' in query.keys():
                tags.extend(query["bitsPerSample"])
            if 'dialect' in query.keys():
                tags.extend(query["dialect"])
            if 'snrTool' in query.keys():
                tags.extend(query["snrTool"])
            if 'datasetId' in query.keys():
                tags.extend(query["datasetId"])
            if 'collectionSource' in query.keys():
                coll_source = [re.compile(cs, re.IGNORECASE) for cs in query["collectionSource"]]
                db_query["collectionSource"] = {"$in": coll_source}
            if 'submitterName' in query.keys():
                db_query["submitter"] = {"$elemMatch": {"name": query["submitterName"]}}
            if 'samplingRate' in query.keys():
                db_query["samplingRate"] = query["samplingRate"]
            no_of_speakers_query, age_query = {}, {}
            if 'minNoOfSpeakers' in query.keys():
                no_of_speakers_query["$gte"] = query["minNoOfSpeakers"]
            if 'maxNoOfSpeakers' in query.keys():
                no_of_speakers_query["$lte"] = query["maxNoOfSpeakers"]
            if no_of_speakers_query:
                db_query["numberOfSpeakers"] = no_of_speakers_query
            if 'noOfSpeakers' in query.keys():
                db_query["numberOfSpeakers"] = query["noOfSpeakers"]
            if 'minAge' in query.keys():
                no_of_speakers_query["$gte"] = query["minAge"]
            if 'maxAge' in query.keys():
                no_of_speakers_query["$lte"] = query["maxAge"]
            if age_query:
                db_query["age"] = age_query
            if 'age' in query.keys():
                db_query["age"] = query["age"]
            if 'multipleContributors' in query.keys():
                if query['multipleContributors']:
                    db_query[f'collectionMethod.1'] = {"$exists": True}
            if tags:
                db_query["tags"] = {"$all": tags}
            exclude = {"_id": False}
            for key in asr_unlabeled_search_ignore_keys:
                exclude[key] = False

            log.info(f"old Db query: {db_query}")
            #logic to whitelist few data based on submitername
            if submiter_name_whitelist_enabled:
                if 'collectionSource' in db_query.keys():
                  del db_query["collectionSource"]
                if 'submitter' in db_query.keys():
                  del db_query["submitter"]
                coll_source_to_whitelist = [re.compile(cs, re.IGNORECASE)
                               for cs in query["collectionSource"]]
                             
                names_to_whitelist = [re.compile(wsn, re.IGNORECASE)
                                       for wsn in submitter_names_to_whitelist]
                new_db_query = {
                    "$and": [
                        {"$or": [{"collectionSource": {"$in": coll_source_to_whitelist}}, {
                            "submitter": {"$elemMatch": {"name": {"$in": names_to_whitelist}}}}]},db_query
                    ]
                }
                log.info(f"new Db query: {new_db_query}")
                db_query = new_db_query

            result, hours = repo.search(db_query, exclude, off, lim)
            count = len(result)
            log.info(f'Result --- Count: {count}, Query: {query}')
            log.info(f'Result --- Hours: {hours}, Query: {query}')
            if result:
                size = sample_size if count > sample_size else count
                path, path_sample = utils.push_result_to_object_store(result, query["serviceRequestNumber"], size)
                if path:
                    op = {"serviceRequestNumber": query["serviceRequestNumber"], "userID": query["userId"],
                          "count": hours, "dataset": path, "datasetSample": path_sample}
                    pt.task_event_search(op, None, dataset_type_asr_unlabeled)
                else:
                    log.error(f'There was an error while pushing result to S3')
                    error = {"code": "OS_UPLOAD_FAILED", "datasetType": dataset_type_asr_unlabeled, "serviceRequestNumber": query["serviceRequestNumber"],
                                                   "message": "There was an error while pushing result to object store"}
                    op = {"serviceRequestNumber": query["serviceRequestNumber"], "userID": query["userId"],
                          "count": 0, "sample": [], "dataset": None, "datasetSample": None}
                    pt.task_event_search(op, error, dataset_type_asr_unlabeled)
            else:
                log.info(f'No records retrieved for SRN -- {query["serviceRequestNumber"]}')
                op = {"serviceRequestNumber": query["serviceRequestNumber"], "userID": query["userId"],
                      "count": 0, "sample": [], "dataset": None, "datasetSample": None}
                pt.task_event_search(op, None, dataset_type_asr_unlabeled)
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