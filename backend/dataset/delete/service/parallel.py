import hashlib
import logging
import multiprocessing
import threading
import time
import uuid
from datetime import datetime
from functools import partial
from logging.config import dictConfig
from configs.configs import parallel_ds_batch_size, offset, limit
from repository.parallel import ParallelRepo
from utils.datasetutils import DatasetUtils

log = logging.getLogger('file')

mongo_instance = None
repo = ParallelRepo()
utils = DatasetUtils()


class ParallelService:
    def __init__(self):
        pass

    def load_dataset(self, request):
        log.info("Loading Dataset..... | {}".format(datetime.now()))
        try:
            ip_data = request["data"]
            batch_data, error_list = [], []
            total, count, duplicates, batch = len(ip_data), 0, 0, parallel_ds_batch_size
            log.info(f'Enriching and dumping dataset.....')
            clean_data = self.get_clean_data(ip_data, error_list)
            log.info(f'Actual Data: {len(ip_data)}, Clean Data: {len(clean_data)}')
            if clean_data:
                func = partial(self.get_enriched_data, request=request, sequential=False)
                no_of_m1_process = request["processors"]
                pool_enrichers = multiprocessing.Pool(no_of_m1_process)
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
                    log.info(f'Processing final batch.....')
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
            data['sourceText'] = str(data['sourceText']).strip()
            data['targetText'] = str(data['targetText']).strip()
            tup = (data['sourceText'], data['targetText'])
            if tup in duplicate_records:
                error_list.append({"record": data, "cause": "DUPLICATE_RECORD",
                                   "description": "This record is repeated multiple times in the input"})
                continue
            else:
                duplicate_records.add(tup)
                clean_data.append(data)
        duplicate_records.clear()
        return clean_data

    def get_enriched_data(self, data):
        insert_records, new_records = [], []
        src_hash = str(hashlib.sha256(data["sourceText"].encode('utf-16')).hexdigest())
        tgt_hash = str(hashlib.sha256(data["targetText"].encode('utf-16')).hexdigest())
        records = self.get_dataset_internal({"hash": [src_hash, tgt_hash]})
        if records:
            for record in records:
                new_data = {}
                if src_hash in record["tags"] and tgt_hash in record["tags"]:
                    return data
                elif src_hash == record["srcHash"]:
                    if data["targetLanguage"] != record["targetLanguage"]:
                        new_data = {"sourceText": data["targetText"], "targetText": record["data"]["targetText"],
                                    "sourceLanguage": data["targetLanguage"], "targetLanguage": record["targetLanguage"]}
                elif src_hash == record["tgtHash"]:
                    if data["targetLanguage"] != record["sourceLanguage"]:
                        new_data = {"sourceText": data["targetText"], "targetText": record["data"]["sourceText"],
                                    "sourceLanguage": data["targetLanguage"], "targetLanguage": record["sourceLanguage"]}
                elif tgt_hash == record["srcHash"]:
                    if data["sourceLanguage"] != record["targetLanguage"]:
                        new_data = {"sourceText": data["sourceText"], "targetText": record["data"]["targetText"],
                                    "sourceLanguage": data["sourceLanguage"], "targetLanguage": record["targetLanguage"]}
                elif tgt_hash == record["tgtHash"]:
                    if data["sourceLanguage"] != record["sourceLanguage"]:
                        new_data = {"sourceText": data["sourceText"], "targetText": record["data"]["sourceText"],
                                    "sourceLanguage": data["sourceLanguage"], "targetLanguage": record["sourceLanguage"]}
                if new_data:
                    modified = data
                    modified["sourceText"], modified["targetText"] = new_data["sourceText"], new_data["targetText"]
                    modified["sourceLanguage"], modified["targetLanguage"] = new_data["sourceLanguage"], new_data["targetLanguage"]
                    new_records.append(modified)
        new_records.append(data)
        for record in new_records:
            data_dict = record
            src_lang, tgt_lang = record["sourceLanguage"], record["targetLanguage"]
            src_hash = str(hashlib.sha256(record["sourceText"].encode('utf-16')).hexdigest())
            tgt_hash = str(hashlib.sha256(record["targetText"].encode('utf-16')).hexdigest())
            lang_map = {src_lang: record["sourceText"], tgt_lang: record["targetText"]}
            hashed_key = ''
            for key in sorted(lang_map.keys()):
                hashed_key = hashed_key + str(lang_map[key])
            hashed_key = str(hashlib.sha256(hashed_key.encode('utf-16')).hexdigest())
            tag_details = {
                "sourceLanguage": src_lang, "targetLanguage": tgt_lang, "collectionMode": record["collectionMode"],
                "collectionSource": record["collectionSource"], "dataset": record["dataset"], "datasetType": record["datasetType"],
                "domain": record["domain"], "licence": record["licence"], "srcHash": src_hash, "tgtHash": tgt_hash
            }
            tags = list(utils.get_tags(tag_details))
            langs = [src_lang, tgt_lang]
            shard_key = ','.join(map(str, sorted(langs)))
            data_dict["id"], data_dict["timestamp"] = str(uuid.uuid4()), eval(str(time.time()).replace('.', '')[0:13])
            data_dict["sk"], data_dict["hashedKey"], data_dict["tags"] = shard_key, hashed_key, tags
            data_dict["sourceTextHash"], data_dict["targetTextHash"] = src_hash, tgt_hash
            insert_records.append(data_dict)
        return insert_records


    def get_dataset_internal(self, query):
        try:
            db_query = {"$in": query["hash"]}
            exclude = {"_id": False}
            data = repo.search(db_query, exclude, None, None)
            if data:
                return data[0]
            else:
                return None
        except Exception as e:
            log.exception(e)
            return None


    def get_dataset(self, query):
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
            if 'datasetType' in query.keys():
                tags.append(query["datasetType"])
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
            if count > 100:
                result = result[:100]
            log.info(f'Result count: {count} | {datetime.now()}')
            log.info(f'Done! | {datetime.now()}')
            return {"count": count, "query": query, "dataset": result}
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