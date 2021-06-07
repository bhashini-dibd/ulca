import hashlib
import logging
import multiprocessing
import threading
from datetime import datetime
from functools import partial
from logging.config import dictConfig
from configs.configs import parallel_ds_batch_size, no_of_parallel_processes, offset, limit, search_output_topic, \
    sample_size, parallel_immutable_keys, parallel_non_tag_keys, delete_output_topic, dataset_type_parallel
from repository.parallel import ParallelRepo
from utils.datasetutils import DatasetUtils
from kafkawrapper.producer import Producer
from events.error import ErrorEvent


log = logging.getLogger('file')

mongo_instance = None
repo = ParallelRepo()
utils = DatasetUtils()
prod = Producer()
error_event = ErrorEvent()


class ParallelService:
    def __init__(self):
        pass

    def load_parallel_dataset(self, request):
        log.info("Loading Dataset.....")
        try:
            metadata = request
            record = request["record"]
            ip_data = [record]
            batch_data, error_list = [], []
            total, count, duplicates, batch = len(ip_data), 0, 0, parallel_ds_batch_size
            if ip_data:
                func = partial(self.get_enriched_data, metadata=metadata)
                pool_enrichers = multiprocessing.Pool(no_of_parallel_processes)
                enrichment_processors = pool_enrichers.map_async(func, ip_data).get()
                for result in enrichment_processors:
                    if result:
                        if isinstance(result[0], list):
                            if len(batch_data) == batch:
                                if metadata["datasetMode"] != 'pseudo':
                                    persist_thread = threading.Thread(target=repo.insert, args=(batch_data,))
                                    persist_thread.start()
                                    persist_thread.join()
                                count += len(batch_data)
                                batch_data = []
                            batch_data.extend(result[0])
                        else:
                            error_list.append({"record": result[0], "originalRecord": result[1], "code": "DUPLICATE_RECORD",
                                               "datasetType": dataset_type_parallel,
                                               "serviceRequestNumber": metadata["serviceRequestNumber"],
                                               "message": "This record is already available in the system"})
                pool_enrichers.close()
                if batch_data:
                    if metadata["datasetMode"] != 'pseudo':
                        persist_thread = threading.Thread(target=repo.insert, args=(batch_data,))
                        persist_thread.start()
                        persist_thread.join()
                    count += len(batch_data)
            if error_list:
                error_event.create_error_event(error_list)
            log.info(f'Done! -- INPUT: {total}, INSERTS: {count}, "INVALID": {len(error_list)}')
        except Exception as e:
            log.exception(e)
            return {"message": "EXCEPTION while loading dataset!!", "status": "FAILED"}
        lang_code = f'{record["sourceLanguage"]}|{record["targetLanguage"]}'
        return {"message": f'loaded {lang_code} dataset to DB', "status": "SUCCESS", "total": total, "inserts": count,
                "invalid": len(error_list)}

    def get_enriched_data(self, data, metadata):
        insert_records, new_records = [], []
        try:
            hashes = [data["sourceTextHash"], data["targetTextHash"]]
            records = self.get_dataset_internal({"hash": hashes})
            if records:
                for record in records:
                    if data["sourceTextHash"] in record["tags"] and data["targetTextHash"] in record["tags"]:
                        dup_data = self.enrich_duplicate_data(data, record, metadata)
                        if dup_data:
                            repo.update(dup_data)
                            return None
                        else:
                            return data, record
                    derived_data = self.enrich_derived_data(data, record, data["sourceTextHash"], data["targetTextHash"], metadata)
                    new_records.append(derived_data)
            new_records.append(data)
            for record in new_records:
                src_lang, tgt_lang = record["sourceLanguage"], record["targetLanguage"]
                lang_map = {src_lang: record["sourceText"], tgt_lang: record["targetText"]}
                if 'derived' not in record.keys():
                    for key in record.keys():
                        if key not in parallel_immutable_keys:
                            if not isinstance(record[key], list):
                                record[key] = [record[key]]
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
        if db_record != record:
            if record["derived"]:
                record["datasetId"] = [metadata["datasetId"]]
                record["derived"] = False
            else:
                record["datasetId"].append(metadata["datasetId"])
            record["tags"] = self.get_tags(record)
            return record
        else:
            return False

    def enrich_derived_data(self, data, record, src_hash, tgt_hash, metadata):
        derived_data = {}
        if src_hash == record["srcHash"]:
            if data["targetLanguage"] != record["targetLanguage"]:
                derived_data = {"sourceText": data["targetText"], "targetText": record["data"]["targetText"],
                                "sourceTextHash": data["targetTextHash"], "targetTextHash": record["data"]["targetTextHash"],
                            "sourceLanguage": data["targetLanguage"], "targetLanguage": record["targetLanguage"]}
        elif src_hash == record["tgtHash"]:
            if data["targetLanguage"] != record["sourceLanguage"]:
                derived_data = {"sourceText": data["targetText"], "targetText": record["data"]["sourceText"],
                                "sourceTextHash": data["targetTextHash"], "targetTextHash": record["data"]["sourceTextHash"],
                            "sourceLanguage": data["targetLanguage"], "targetLanguage": record["sourceLanguage"]}
        elif tgt_hash == record["srcHash"]:
            if data["sourceLanguage"] != record["targetLanguage"]:
                derived_data = {"sourceText": data["sourceText"], "targetText": record["data"]["targetText"],
                                "sourceTextHash": data["sourceTextHash"], "targetTextHash": record["data"]["targetTextHash"],
                            "sourceLanguage": data["sourceLanguage"], "targetLanguage": record["targetLanguage"]}
        elif tgt_hash == record["tgtHash"]:
            if data["sourceLanguage"] != record["sourceLanguage"]:
                derived_data = {"sourceText": data["sourceText"], "targetText": record["data"]["sourceText"],
                                "sourceTextHash": data["sourceTextHash"], "targetTextHash": record["data"]["sourceTextHash"],
                            "sourceLanguage": data["sourceLanguage"], "targetLanguage": record["sourceLanguage"]}
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
        derived_data["datasetId"] = [record["datasetId"], metadata["datasetId"]]
        derived_data["datasetType"] = metadata["datasetType"]
        derived_data["derived"] = True
        derived_data["tags"] = self.get_tags(derived_data)
        return derived_data

    def get_tags(self, insert_data):
        tag_details = {}
        for key in insert_data:
            if key not in parallel_non_tag_keys:
                tag_details[key] = insert_data[key]
        return list(utils.get_tags(tag_details))

    def get_parallel_dataset(self, query):
        log.info(f'Fetching datasets..... | {datetime.now()}')
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
            tags, src_lang, tgt_lang = [], None, []
            if 'sourceLanguage' in query.keys():
                src_lang = query["sourceLanguage"]
            if 'targetLanguage' in query.keys():
                for tgt in query["targetLanguage"]:
                    tgt_lang.append(tgt)
            if 'collectionMode' in query.keys():
                tags.append(query["collectionMode"])
            if 'license' in query.keys():
                tags.append(query["licence"])
            if 'domain' in query.keys():
                tags.append(query["domain"])
            if 'datasetId' in query.keys():
                tags.append(query["datasetId"])
                db_query["derived"] = False
            if tags:
                db_query["tags"] = tags
            if src_lang:
                db_query["sourceLanguage"] = src_lang
            if tgt_lang:
                db_query["targetLanguage"] = tgt_lang
            if 'groupBy' in query.keys():
                db_query["groupBy"] = True
                if 'countOfTranslations' in query.keys():
                    db_query["countOfTranslations"] = query["countOfTranslations"]
            exclude = {"_id": False}
            data = repo.search(db_query, exclude, offset, limit)
            result, query, count = data[0], data[1], data[2]
            log.info(f'Result --- Count: {count}, Query: {query}')
            path = utils.push_result_to_s3(result, query["serviceRequestNumber"])
            if path:
                size = sample_size
                if count <= 10:
                    size = count
                op = {"serviceRequestNumber": query["serviceRequestNumber"], "count": count, "sample": result[:size], "dataset": path}
            else:
                log.error(f'There was an error while pushing result to S3')
                op = {"serviceRequestNumber": query["serviceRequestNumber"], "count": 0, "sample": [], "dataset": None}
            prod.produce(op, search_output_topic, None)
            log.info(f'Done!')
            return op
        except Exception as e:
            log.exception(e)
            return {"message": str(e), "status": "FAILED", "dataset": "NA"}

    def delete_parallel_dataset(self, delete_req):
        log.info(f'Deleting datasets....')
        records = self.get_parallel_dataset({"datasetId": delete_req["datasetId"]})
        d, u = 0, 0
        for record in records:
            if len(record["datasetId"]) == 1:
                repo.delete(record["id"])
                d += 1
            elif record["derived"]:
                repo.delete(record["id"])
                d += 1
            else:
                record["datasetId"].remove(delete_req["datasetId"])
                record["tags"].remove(delete_req["datasetId"])
                repo.update(record)
                u += 1
        op = {"serviceRequestNumber": delete_req["serviceRequestNumber"], "deleted": d, "updated": u}
        prod.produce(op, delete_output_topic, None)
        log.info(f'Done!')
        return op


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