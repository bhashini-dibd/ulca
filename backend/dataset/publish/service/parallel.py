import hashlib
import logging
import multiprocessing
import threading
import time
import uuid
from datetime import datetime
from functools import partial
from logging.config import dictConfig
from configs.configs import parallel_ds_batch_size, no_of_parallel_processes, offset, limit, search_output_topic, \
    sample_size, parallel_immutable_keys, parallel_non_tag_keys
from repository.parallel import ParallelRepo
from utils.datasetutils import DatasetUtils
from kafkawrapper.producer import Producer


log = logging.getLogger('file')

mongo_instance = None
repo = ParallelRepo()
utils = DatasetUtils()
prod = Producer()


class ParallelService:
    def __init__(self):
        pass

    def load_parallel_dataset(self, request):
        log.info("Loading Dataset..... | {}".format(datetime.now()))
        try:
            ip_data = request["data"]
            metadata = ip_data
            metadata.pop("record")
            ip_data = [ip_data["record"]]
            batch_data, error_list = [], []
            total, count, duplicates, batch = len(ip_data), 0, 0, parallel_ds_batch_size
            clean_data = self.get_clean_data(ip_data, error_list)
            if clean_data:
                func = partial(self.get_enriched_data, metadata=metadata)
                pool_enrichers = multiprocessing.Pool(no_of_parallel_processes)
                enrichment_processors = pool_enrichers.map_async(func, clean_data).get()
                for result in enrichment_processors:
                    if result:
                        if isinstance(result, list):
                            if len(batch_data) == batch:
                                persist_thread = threading.Thread(target=repo.insert, args=(batch_data,))
                                persist_thread.start()
                                persist_thread.join()
                                count += len(batch_data)
                                batch_data = []
                            batch_data.extend(result)
                        else:
                            error_list.append({"record": result, "cause": "DUPLICATE_RECORD",
                                               "description": "This record is already available in the system"})
                pool_enrichers.close()
                if batch_data:
                    persist_thread = threading.Thread(target=repo.insert, args=(batch_data,))
                    persist_thread.start()
                    persist_thread.join()
                    count += len(batch_data)
            log.info(f'Done! -- INPUT: {total}, INSERTS: {count}, "INVALID": {len(error_list)}')
        except Exception as e:
            log.exception(e)
            return {"message": "EXCEPTION while loading dataset!!", "status": "FAILED"}
        lang_code = f'{request["details"]["sourceLanguage"]}|{request["details"]["targetLanguage"]}'
        return {"message": f'loaded {lang_code} dataset to DB', "status": "SUCCESS", "total": total, "inserts": count,
                "invalid": len(error_list)}

    # Method to perform basic checks on the input
    def get_clean_data(self, ip_data, error_list):
        duplicate_records, clean_data = set([]), []
        for data in ip_data:
            if 'sourceText' not in data.keys() or 'targetText' not in data.keys():
                error_list.append({"record": data, "cause": "INVALID_RECORD",
                                   "description": "either sourceText or the targetText is missing"})
                continue
            tup = (data['sourceTextHash'], data['targetTextHash'])
            if tup in duplicate_records:
                error_list.append({"record": data, "cause": "DUPLICATE_RECORD",
                                   "description": "This record is repeated multiple times in the input"})
                continue
            else:
                duplicate_records.add(tup)
                clean_data.append(data)
        duplicate_records.clear()
        return clean_data

    def get_enriched_data(self, data, metadata):
        insert_records, new_records = [], []
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
                        return data
                derived_data = self.enrich_derived_data(data, record, data["sourceTextHash"], data["targetTextHash"], metadata)
                new_records.append(derived_data)
        new_records.append(data)
        for record in new_records:
            data_dict = record
            src_lang, tgt_lang = record["sourceLanguage"], record["targetLanguage"]
            lang_map = {src_lang: record["sourceText"], tgt_lang: record["targetText"]}
            if 'derived' not in data_dict.keys():
                data_dict["derived"] = False
                hashed_key = ''
                for key in sorted(lang_map.keys()):
                    hashed_key = hashed_key + str(lang_map[key])
                data_dict["hashedKey"] = str(hashlib.sha256(hashed_key.encode('utf-16')).hexdigest())
                data_dict["sk"] = ','.join(map(str, sorted([src_lang, tgt_lang])))
                data_dict["datasetType"] = metadata["datasetType"]
                data_dict["datasetId"] = [metadata["datasetId"]]
            for key in data_dict.keys():
                if key not in parallel_immutable_keys:
                    data_dict[key] = [data_dict[key]]
            data_dict["tags"] = self.get_tags(data_dict)
            insert_records.append(data_dict)
        return insert_records

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
        record["datasetId"].append(metadata["datasetId"])
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
        record["tags"] = self.get_tags(record)
        return record

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
        derived_data["derived"] = True
        derived_data["datasetId"] = [record["datasetId"], metadata["datasetId"]]
        derived_data["datasetType"] = metadata["datasetType"]
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
        return derived_data

    def get_tags(self, insert_data):
        tag_details = insert_data
        for key in parallel_non_tag_keys:
            if key in tag_details.keys():
                tag_details.pop(key)
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