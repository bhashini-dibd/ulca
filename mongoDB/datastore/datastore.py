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
                data_json = data_json[request["slice"]["start"]:request["slice"]["end"]]
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
                        batch_data.extend(result[0])
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
            pool.close()
            log.info(f'Done! -- INPUT: {total}, INSERTS: {count}, "DUPLICATES": {duplicates} | {datetime.now()}')
        except Exception as e:
            log.exception(e)
            return {"message": "EXCEPTION while loading dataset!!", "status": "FAILED"}
        return {"message": 'loaded dataset to DB', "status": "SUCCESS", "total": total, "inserts": count, "duplicates": duplicates}

    def get_enriched_data(self, data, request):
        if 'sourceText' not in data.keys() or 'targetText' not in data.keys():
            return None, 0
        insert_records, new_records = [], []
        src_hash = str(hashlib.sha256(data["sourceText"].encode('utf-16')).hexdigest())
        tgt_hash = str(hashlib.sha256(data["targetText"].encode('utf-16')).hexdigest())
        records = self.get_dataset_internal({"hash": [src_hash, tgt_hash]})
        if records:
            for record in records:
                new_data = {}
                if src_hash in record["tags"] and tgt_hash in record["tags"]:
                    return None, 1
                elif src_hash == record["srcHash"]:
                    new_data = {"sourceText": data["targetText"], "targetText": record["data"]["targetText"],
                                "sourceLanguage": request["details"]["targetLanguage"], "targetLanguage": record["targetLanguage"]}
                elif src_hash == record["tgtHash"]:
                    new_data = {"sourceText": data["targetText"], "targetText": record["data"]["sourceText"],
                                "sourceLanguage": request["details"]["targetLanguage"], "targetLanguage": record["sourceLanguage"]}
                elif tgt_hash == record["srcHash"]:
                    new_data = {"sourceText": data["sourceText"], "targetText": record["data"]["targetText"],
                                "sourceLanguage": request["details"]["sourceLanguage"], "targetLanguage": record["targetLanguage"]}
                elif tgt_hash == record["tgtHash"]:
                    new_data = {"sourceText": data["sourceText"], "targetText": record["data"]["sourceText"],
                                "sourceLanguage": request["details"]["sourceLanguage"], "targetLanguage": record["sourceLanguage"]}
                new_records.append(new_data)
        new_records.append(data)
        for record in new_records:
            record["score"] = random.uniform(0, 1)
            tag_details, details = {}, request["details"]
            src_hash = str(hashlib.sha256(record["sourceText"].encode('utf-16')).hexdigest())
            tgt_hash = str(hashlib.sha256(record["targetText"].encode('utf-16')).hexdigest())
            if "sourceLanguage" in record.keys() and "targetLanguage" in record.keys():
                details["sourceLanguage"] = record["sourceLanguage"]
                details["targetLanguage"] = record["targetLanguage"]
            tag_details = {
                "sourceLanguage": details["sourceLanguage"], "targetLanguage": details["sourceLanguage"], "collectionMode": details["collectionMode"],
                "domain": details["domain"], "licence": details["licence"], "srcHash": src_hash, "tgtHash": tgt_hash
            }
            tags = list(self.get_tags(tag_details))
            langs = [details["sourceLanguage"], details["targetLanguage"]]
            shard_key = ','.join(map(str, sorted(langs)))
            data_dict = {"id": str(uuid.uuid4()), "contributors": request["contributors"],
                         "submitter": request["submitter"], "sourceLanguage": details["sourceLanguage"], "targetLanguage": details["targetLanguage"],
                         "timestamp": eval(str(time.time()).replace('.', '')[0:13]),
                         "data": data, "srcHash": src_hash, "tgtHash": tgt_hash, 'shardKey': shard_key, "tags": tags}
            insert_records.append(data_dict)
        return insert_records, 0

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
            db_query = {"$in": query["hash"]}
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

    def set_mongo_cluster(self):
        if "localhost" not in mongo_server_host:
            log.info(f'Setting the Mongo M1 Shard Cluster up..... | {datetime.now()}')
            client = pymongo.MongoClient(mongo_server_host)
            client.drop_database(mongo_ulca_db)
            ulca_db = client[mongo_ulca_db]
            ulca_col = ulca_db[mongo_ulca_dataset_col]
            ulca_col.create_index([("tags", -1)])
            ulca_col.create_index([("sourceLanguage", -1)])
            ulca_col.create_index([("targetLanguage", -1)])
            ulca_col.create_index([("shardKey", "hashed")])
            db = client.admin
            db.command('enableSharding', mongo_ulca_db)
            key = OrderedDict([("shardKey", "hashed")])
            db.command({'shardCollection': f'{mongo_ulca_db}.{mongo_ulca_dataset_col}', 'key': key})
            log.info(f'Done! | {datetime.now()}')
        else:
            log.info(f'Setting the Mongo DB M1.... | {datetime.now()}')
            client = pymongo.MongoClient(mongo_server_host)
            client.drop_database(mongo_ulca_db)
            ulca_db = client[mongo_ulca_db]
            ulca_col = ulca_db[mongo_ulca_dataset_col]
            ulca_col.create_index([("tags", -1)])
            ulca_col.create_index([("sourceLanguage", -1)])
            ulca_col.create_index([("targetLanguage", -1)])
            log.info(f'Done! | {datetime.now()}')

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
            if "$in" in query.keys():
                pipeline = []
                pipeline.append({"$match": {"tags": query}})
                pipeline.append({"$project": {"_id": 0}})
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
                        res = col.find({"srcHash": {"$in": hashes}}, {"_id": False, "srcHash": True, "data": True})
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