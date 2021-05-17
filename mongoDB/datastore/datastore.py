import hashlib
import json
import logging
import multiprocessing
import time
import uuid
import random
from collections import OrderedDict
from datetime import datetime
from functools import partial
from logging.config import dictConfig

from configs import file_path, file_name, default_offset, default_limit, mongo_server_host, mongo_ulca_db
from configs import mongo_ulca_dataset_col, no_of_enrich_process, no_of_dump_process

import pymongo
log = logging.getLogger('file')


mongo_instance = None

class Datastore:
    def __init__(self):
        pass

    def load_dataset(self, request):
        log.info("Loading Dataset..... | {}".format(datetime.now()))
        try:
            if 'path' not in request.keys():
                path = f'{file_path}' + f'{file_name}'
            else:
                path = request["path"]
            log.info("File -- {} | {}".format(path, datetime.now()))
            dataset = open(path, "r")
            data_json = json.load(dataset)
            if 'slice' in request.keys():
                data_json = data_json[:request["slice"]]
            enriched_data, duplicates, batch_data = [], 0, []
            total, count, duplicates, batch = len(data_json), 0, 0, request["batch"]
            log.info(f'Enriching dataset..... | {datetime.now()}')
            func = partial(self.get_enriched_data, request=request)
            pool_enrichers = multiprocessing.Pool(no_of_enrich_process)
            enrichment_processors = pool_enrichers.map_async(func, data_json).get()
            for result in enrichment_processors:
                if result:
                    if result[0]:
                        if len(batch_data) == batch:
                            log.info(f'Adding batch of {len(batch_data)} to the BULK INSERT list... | {datetime.now()}')
                            enriched_data.append(batch_data)
                            batch_data = []
                        else:
                            batch_data.append(result[0])
                    duplicates += result[1]
            pool_enrichers.close()
            if batch_data:
                log.info(f'Adding batch of {len(batch_data)} to the BULK INSERT list... | {datetime.now()}')
                enriched_data.append(batch_data)
            log.info(f'Dumping enriched dataset..... | {datetime.now()}')
            pool = multiprocessing.Pool(no_of_dump_process)
            processors = pool.map_async(self.insert, enriched_data).get()
            for result in processors:
                count += result
                if (count + duplicates) == total:
                    log.info(f'Dumping COMPLETE! records -- {count} | {datetime.now()}')
                    break
            pool.close()
            log.info(f'Done! -- INSERTS: {count}, "DUPLICATES": {duplicates} | {datetime.now()}')
        except Exception as e:
            log.exception(e)
            return {"message": "EXCEPTION while loading dataset!!", "status": "FAILED"}
        return {"message": f'loaded {total} no. of records to DB', "status": "SUCCESS"}

    def get_enriched_data(self, data, request):
        if 'sourceText' not in data.keys() or 'targetText' not in data.keys():
            return None, 0
        src_hash = str(hashlib.sha256(data["sourceText"].encode('utf-16')).hexdigest())
        tgt_hash = str(hashlib.sha256(data["targetText"].encode('utf-16')).hexdigest())
        record = self.get_dataset_internal({"hash": [src_hash, tgt_hash]})
        if record:
            return None, 1
        data["score"] = random.uniform(0, 1)
        tag_details, details = {}, request["details"]
        tag_details = {
            "sourceLanguage": details["sourceLanguage"], "targetLanguage": details["targetLanguage"], "collectionMode": details["collectionMode"],
            "domain": details["domain"], "licence": details["licence"]
        }
        tags = list(self.get_tags(tag_details))
        data_dict = {"id": str(uuid.uuid4()), "contributors": request["contributors"],
                     "submitter": request["submitter"], "sourceLanguage": details["sourceLanguage"], "targetLanguage": details["targetLanguage"],
                     "timestamp": eval(str(time.time()).replace('.', '')[0:13]),
                     "data": data, "srcHash": src_hash, "tgtHash": tgt_hash, "tags": tags}
        return data_dict, 0

    def get_tags(self, d):
        for v in d.values():
            if isinstance(v, dict):
                yield from self.get_tags(v)
            elif isinstance(v, list):
                for entries in v:
                    yield entries
            else:
                yield v

    def get_dataset_internal(self, query):
        try:
            db_query = {"$and": [{"srcHash": {"$in": query["hash"]}}, {"tgtHash": {"$in": query["hash"]}}]}
            exclude = {"_id": False}
            data = self.search(db_query, exclude, None, None)
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
            offset = query["offset"] if 'offset' in query.keys() else default_offset
            limit = query["limit"] if 'limit' in query.keys() else default_limit
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
            if 'srcLang' in query.keys():
                src_lang = query["srcLang"]
            if 'tgtLang' in query.keys():
                for tgt in query["tgtLang"]:
                    tgt_lang.append(tgt)
            if 'collectionMode' in query.keys():
                tags.append(query["collectionMode"])
            if 'licence' in query.keys():
                tags.append(query["licence"])
            if 'domain' in query.keys():
                tags.append(query["domain"])
            if 'srcText' in query.keys():
                db_query["srcHash"] = str(hashlib.sha256(query["srcText"].encode('utf-16')).hexdigest())
            if tags:
                db_query["tags"] = tags
            if src_lang:
                db_query["srcLang"] = src_lang
            if tgt_lang:
                db_query["tgtLang"] = tgt_lang
            if 'groupBySource' in query.keys():
                db_query["groupBySource"] = True
                if 'countOfTranslations' in query.keys():
                    db_query["countOfTranslations"] = query["countOfTranslations"]
            exclude = {"_id": False}
            data = self.search(db_query, exclude, offset, limit)
            result, query, count = data[0], data[1], data[2]
            if count > 30:
                result = result[:30]
            log.info(f'Result count: {count} | {datetime.now()}')
            log.info(f'Done! | {datetime.now()}')
            return {"count": count, "query": query, "dataset": result}
        except Exception as e:
            log.exception(e)
            return {"message": str(e), "status": "FAILED", "dataset": "NA"}

    ####################### DB ##############################

    def set_mongo_cluster(self, create):
        if create:
            log.info(f'Setting the Mongo Shard Cluster up..... | {datetime.now()}')
            client = pymongo.MongoClient(mongo_server_host)
            client.drop_database(mongo_ulca_db)
            ulca_db = client[mongo_ulca_db]
            ulca_col = ulca_db[mongo_ulca_dataset_col]
            ulca_col.create_index([("data.score", -1)])
            ulca_col.create_index([("tags", -1)])
            ulca_col.create_index([("srcHash", -1)])
            ulca_col.create_index([("tgtHash", -1)])
            ulca_col.create_index([("sourceLanguage", 1)])
            ulca_col.create_index([("targetLanguage", "hashed")])
            db = client.admin
            db.command('enableSharding', mongo_ulca_db)
            key = OrderedDict([("sourceLanguage", 1), ("targetLanguage", "hashed")])
            db.command({'shardCollection': f'{mongo_ulca_db}.{mongo_ulca_dataset_col}', 'key': key})
            log.info(f'Done! | {datetime.now()}')
        else:
            return None

    def instantiate(self):
        client = pymongo.MongoClient(mongo_server_host)
        db = client[mongo_ulca_db]
        mongo_instance = db[mongo_ulca_dataset_col]
        return mongo_instance

    def get_mongo_instance(self):
        if not mongo_instance:
            return self.instantiate()
        else:
            return mongo_instance

    def insert(self, data):
        col = self.get_mongo_instance()
        col.insert_many(data)
        return len(data)

    # Searches the object into mongo collection
    def search(self, query, exclude, offset, res_limit):
        result, res_count, pipeline = [], 0, []
        try:
            col = self.get_mongo_instance()
            if 'srcLang' in query.keys() and 'tgtLang' in query.keys():
                langs = [query["srcLang"]]
                langs.extend(query["tgtLang"])
                pipeline.append({"$match": {"$and": [{"sourceLanguage": {"$in": langs}}, {"targetLanguage": {"$in": langs}}]}})
            elif 'srcLang' in query.keys():
                pipeline.append({"$match": {"$or": [{"sourceLanguage": query["srcLang"]}, {"targetLanguage": query["srcLang"]}]}})
            if "tags" in query.keys():
                pipeline.append({"$match": {"tags": {"$all": query["tags"]}}})
            if "scoreQuery" in query.keys():
                pipeline.append({"$match": query["scoreQuery"]})
            if 'groupBySource' in query.keys():
                pipeline.append({"$group": {"_id": {"sourceHash": "$srcHash"}, "count": {"$sum": 1}}})
                if 'countOfTranslations' in query.keys():
                    pipeline.append({"$group": {"_id": {"$cond": [{"$gt": ["$count", query["countOfTranslations"]]}, "$_id.sourceHash", "$$REMOVE"]}}})
                else:
                    pipeline.append({"$group": {"_id": {"$cond": [{"$gt": ["$count", 1]}, "$_id.sourceHash", "$$REMOVE"]}}})
            else:
                pipeline.append({"$project": {"_id": 0, "data": 1}})
            res = col.aggregate(pipeline, allowDiskUse=True)
            if 'groupBySource' in query.keys():
                if res:
                    hashes = []
                    for record in res:
                        if record:
                            if record["_id"]:
                                hashes.append(record["_id"])
                    if hashes:
                        res_count = len(hashes)
                        res = col.find({"srcHash": {"$in": hashes[:100]}}, {"_id": False, "srcHash": True, "data": True})
                    map = {}
                    if not res:
                        return result, pipeline, res_count
                    for record in res:
                        if record:
                            if record["srcHash"] in map.keys():
                                data_list = map[record["srcHash"]]
                                data_list.append(record["data"])
                                map[record["srcHash"]] = data_list
                            else:
                                map[record["srcHash"]] = [record["data"]]
                    result = list(map.values())
            else:
                if res:
                    for record in res:
                        if record:
                            result.append(record)
                res_count = len(result)
        except Exception as e:
            log.exception(e)
            return [], pipeline, 0
        return result, pipeline, res_count


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