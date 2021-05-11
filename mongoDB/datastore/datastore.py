import hashlib
import json
import logging
import multiprocessing
import time
import uuid
import random
from collections import OrderedDict
from datetime import datetime
from logging.config import dictConfig
from bson.code import Code

from configs import file_path, file_name, default_offset, default_limit, mongo_server_host, mongo_ulca_db
from configs import mongo_ulca_dataset_col, no_of_process

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
            enriched_data, batch_data = [], []
            total, count, batch = len(data_json), 0, 100000
            log.info(f'Enriching dataset..... | {datetime.now()}')
            for data in data_json:
                data["score"] = random.uniform(0, 1)
                tag_details, details = {}, request["details"]
                src_hash = None
                if 'sourceText' in data.keys():
                    src_hash = str(hashlib.sha256(data["sourceText"].encode('utf-16')).hexdigest())
                tag_details["sourceLanguage"] = details["sourceLanguage"]
                tag_details["languageCode"] = f'{details["sourceLanguage"]}|{details["targetLanguage"]}'
                tag_details["name"], tag_details["category"] = details["name"], details["category"]
                tag_details["collectionMode"] = details["collectionMode"]
                tag_details["domain"], tag_details["licence"] = details["domain"], details["licence"]
                tags = list(self.get_tags(tag_details))
                data_dict = {"id": str(uuid.uuid4()), "contributors": request["contributors"],
                             "timestamp": eval(str(time.time()).replace('.', '')[0:13]), "details": details,
                             "data": data, "srcHash": src_hash, "tags": tags}
                batch_data.append(data_dict)
                if len(batch_data) == batch:
                    enriched_data.append(batch_data)
                    batch_data = []
            if batch_data:
                enriched_data.append(batch_data)
            log.info(f'Dumping enriched dataset..... | {datetime.now()}')
            pool = multiprocessing.Pool(no_of_process)
            processors = pool.map_async(self.insert, enriched_data).get()
            for result in processors:
                count += result
                if count == total:
                    log.info(f'Dumping COMPLETE! records -- {count} | {datetime.now()}')
                    break
            pool.close()
        except Exception as e:
            log.exception(e)
            return {"message": "EXCEPTION while loading dataset!!", "status": "FAILED"}
        return {"message": f'loaded {total} no. of records to DB', "status": "SUCCESS"}

    def get_tags(self, d):
        for v in d.values():
            if isinstance(v, dict):
                yield from self.get_tags(v)
            elif isinstance(v, list):
                for entries in v:
                    yield entries
            else:
                yield v

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
                db_query["data.score"] = score_query
            if 'score' in query.keys():
                db_query["data.score"] = query["score"]
            tags, lang_tags = [], []
            src_lang, tgt_lang = None, None
            if 'srcLang' in query.keys():
                src_lang = query["srcLang"]
            if 'tgtLang' in query.keys():
                tgt_lang = query["tgtLang"]
            if src_lang and tgt_lang:
                if len(tgt_lang) == 1:
                    tags.append(f'{src_lang}|{tgt_lang[0]}')
                else:
                    for lang in tgt_lang:
                        lang_tags.append(f'{src_lang}|{lang}')
            else:
                if src_lang:
                    tags.append(src_lang)
            if 'collectionMode' in query.keys():
                tags.append(query["collectionMode"])
            if 'licence' in query.keys():
                tags.append(query["licence"])
            if 'domain' in query.keys():
                tags.append(query["domain"])
            if 'srcText' in query.keys():
                tags.append(str(hashlib.sha256(query["srcText"].encode('utf-16')).hexdigest()))
            if tags and lang_tags:
                db_query["tags"] = {"$all": tags, "$in": lang_tags}
            elif tags:
                db_query["tags"] = {"$all": tags}
            elif lang_tags:
                db_query["tags"] = {"$in": lang_tags}
            if 'groupBySource' in query.keys():
                db_query["$groupBySource"] = True
            exclude = {"_id": False, "data": True}
            data = self.search(db_query, exclude, offset, limit)
            count = len(data)
            log.info(f'Result count: {count} | {datetime.now()}')
            log.info(f'Done! | {datetime.now()}')
            return {"count": count, "query": db_query, "dataset": "NA"}
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
            db = client.admin
            db.command('enableSharding', mongo_ulca_db)
            key = OrderedDict([("_id", "hashed")])
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
        col = self.get_mongo_instance()
        if "$groupBySource" in query.keys():
            query.pop("$groupBySource")
            return self.search_map_reduce(col, query, res_limit)
        if offset is None and res_limit is None:
            res = col.find(query, exclude).sort([('_id', 1)])
        else:
            res = col.find(query, exclude).sort([('_id', -1)]).skip(offset).limit(res_limit)
        result = []
        for record in res:
            result.append(record)
        return result

    def search_map_reduce(self, col, query, res_limit):
        map_func = Code("function () { emit(this.data.sourceText, this.data); }")
        reduce_func = Code("function (key, values) {"
                      "  var data = [];"
                      "  var result = [];"
                      "  for (var i = 0; i < values.length; i++) {"
                      "    data.push(values[i]);"
                      "  }"
                      "  result.push({key: key, value: data})"
                      "  return result;"
                      "}")
        res = col.map_reduce(map_func, reduce_func, "dataset", query=query, limit=res_limit)
        result = []
        for record in res:
            result.append(record)
        return result


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