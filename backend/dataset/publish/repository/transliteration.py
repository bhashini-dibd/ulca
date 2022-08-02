import logging
from collections import OrderedDict
from datetime import datetime
from logging.config import dictConfig

from bson import ObjectId

from configs.configs import db_cluster, db, transliteration_collection, transliteration_search_ignore_keys

import pymongo

log = logging.getLogger('file')

mongo_instance_transliteration = None


class TransliterationRepo:
    def __init__(self):
        pass

    # Method to set Parallel Dataset Mongo DB collection
    def set_transliteration_collection(self):
        if "localhost" not in db_cluster:
            log.info(f'Setting the Mongo Transliteration DS Shard Cluster up..... | {datetime.now()}')
            client = pymongo.MongoClient(db_cluster)
            ulca_db = client[db]
            ulca_db.drop_collection(transliteration_collection)
            ulca_col = ulca_db[transliteration_collection]
            ulca_col.create_index([("tags", -1)])
            db_cli = client.admin
            key = OrderedDict([("_id", "hashed")])
            db_cli.command({'shardCollection': f'{db}.{transliteration_collection}', 'key': key})
            log.info(f'Done! | {datetime.now()}')
        else:
            log.info(f'Setting the Mongo DB Local for Transliteration DS.... | {datetime.now()}')
            client = pymongo.MongoClient(db_cluster)
            ulca_db = client[db]
            ulca_db.drop_collection(transliteration_collection)
            ulca_col = ulca_db[transliteration_collection]
            ulca_col.create_index([("tags", -1)])
            ulca_col.create_index([("sourceLanguage", -1)])
            ulca_col.create_index([("targetLanguage", -1)])
            log.info(f'Done! | {datetime.now()}')

    # Initialises and fetches mongo db client
    def instantiate(self):
        global mongo_instance_transliteration
        client = pymongo.MongoClient(db_cluster)
        mongo_instance_transliteration = client[db][transliteration_collection]
        return mongo_instance_transliteration

    def get_mongo_instance(self):
        global mongo_instance_transliteration
        if not mongo_instance_transliteration:
            return self.instantiate()
        else:
            return mongo_instance_transliteration

    def insert(self, data):
        col = self.get_mongo_instance()
        col.insert_many(data)
        return len(data)

    def delete(self, rec_id):
        col = self.get_mongo_instance()
        col.delete_one({"id": rec_id})

    def update(self, object_in):
        col = self.get_mongo_instance()
        try:
            object_in["_id"] = ObjectId(object_in["_id"])
            col.replace_one({"_id": object_in["_id"]}, object_in, False)
        except Exception as e:
            log.exception(f"Exception while updating: {e}", e)

    def search_internal(self, query, exclude, offset, res_limit):
        try:
            col = self.get_mongo_instance()
            if offset is None and res_limit is None:
                if exclude:
                    res = col.find(query, exclude).sort([('_id', 1)])
                else:
                    res = col.find(query).sort([('_id', 1)])
            else:
                if exclude:
                    res = col.find(query, exclude).sort([('_id', -1)]).skip(offset).limit(res_limit)
                else:
                    res = col.find(query).sort([('_id', -1)]).skip(offset).limit(res_limit)
            result = []
            for record in res:
                if "_id" in record.keys():
                    record["_id"] = str(record["_id"])
                result.append(record)
            return result
        except Exception as e:
            log.exception(e)
            return []

    def search(self, query, offset, res_limit):
        result, res_count, pipeline, langs = [], 0, [], []
        if not query:
            log.info(f'Empty Query: {query}')
            return result, pipeline, res_count
        try:
            col = self.get_mongo_instance()
            if 'sourceLanguage' in query.keys() and 'targetLanguage' in query.keys():
                if len(query["targetLanguage"]) == 1:
                    if not query['groupBy']:
                        if query['originalSourceSentence']:
                            pipeline.append({"$match": {"$and": [{"sourceLanguage": query["sourceLanguage"]},
                                                                 {"targetLanguage": query["targetLanguage"][0]}]}})
                            query["derived"] = False
                        else:
                            langs = [query["sourceLanguage"], query["targetLanguage"][0]]
                            pipeline.append({"$match": {
                                "$and": [{"sourceLanguage": {"$in": langs}}, {"targetLanguage": {"$in": langs}}]}})
                    else:
                        pipeline.append({"$match": {"$and": [{"sourceLanguage": query["sourceLanguage"]},
                                                             {"targetLanguage": query["targetLanguage"][0]}]}})
                        if query['originalSourceSentence']:
                            query["derived"] = False
                else:
                    pipeline.append({"$match": {"$and": [{"sourceLanguage": query["sourceLanguage"]},
                                                         {"targetLanguage": {"$in": query["targetLanguage"]}}]}})
                    query["groupBy"] = True
                    if query['originalSourceSentence']:
                        query["derived"] = False
            if "derived" in query.keys():
                pipeline.append({"$match": {"derived": query["derived"]}})
            if "tags" in query.keys():
                pipeline.append({"$match": {"tags": {"$all": query["tags"]}}})
            if "scoreQuery" in query.keys():
                pipeline.append({"$match": query["scoreQuery"]})
            if "collectionSourceQuery" in query.keys():
                pipeline.append({"$match": query["collectionSourceQuery"]})
            if "submitterNameQuery" in query.keys():
                pipeline.append({"$match": query["submitterNameQuery"]})
            if query['multipleContributors']:
                pipeline.append({"$match": {f'collectionMethod.1': {"$exists": True}}})
            if query['groupBy']:
                pipeline.append({"$group": {"_id": {"sourceHash": "$sourceTextHash"}, "count": {"$sum": 1}}})
                count = 2
                if 'countOfTranslations' in query.keys():
                    count = query["countOfTranslations"]
                pipeline.append(
                    {"$group": {"_id": {"$cond": [{"$gte": ["$count", count]}, "$_id.sourceHash", "$$REMOVE"]}}})
            else:
                project = {"_id": 0}
                for key in transliteration_search_ignore_keys:
                    project[key] = 0
                pipeline.append({"$project": project})
            if offset is not None and res_limit is not None:
                pipeline.append({"$sort": {"_id": -1}})
                pipeline.append({"$skip": offset})
                pipeline.append({"$limit": res_limit})
            if "$in" in query.keys():
                pipeline = []
                pipeline.append({"$match": {"tags": query}})
                pipeline.append({"$project": {"_id": 0}})
            res = col.aggregate(pipeline, allowDiskUse=True)
            if query['groupBy']:
                if res:
                    hashes = []
                    for record in res:
                        if record:
                            if record["_id"]:
                                hashes.append(record["_id"])
                    if hashes:
                        res_count = len(hashes)
                        project = {"_id": False}
                        for key in transliteration_search_ignore_keys:
                            project[key] = False
                        res = col.find({"sourceTextHash": {"$in": hashes}}, project)
                    if not res:
                        return result, pipeline, res_count
                    map, tgt_lang = {}, []
                    if isinstance(query["targetLanguage"], str):
                        tgt_lang = [query["targetLanguage"]]
                    else:
                        tgt_lang = query["targetLanguage"]
                    log.info(f'Grouping with {len(tgt_lang)} target languages')
                    for record in res:
                        if record:
                            if record["sourceTextHash"] in map.keys():
                                data_list = map[record["sourceTextHash"]]
                                if record["sourceLanguage"] == query["sourceLanguage"]:
                                    if record["targetLanguage"] in tgt_lang:
                                        data_list.append(record)
                                map[record["sourceTextHash"]] = data_list
                            else:
                                map[record["sourceTextHash"]] = [record]
                    else:
                        res_count = 0
                        for srcHash in map.keys():
                            tgt = set([])
                            for record in map[srcHash]:
                                tgt.add(record["targetLanguage"])
                            if len(tgt) == len(tgt_lang):
                                result.append(map[srcHash])
                                res_count += 1
            else:
                if res:
                    for record in res:
                        if record:
                            result.append(record)
                res_count = len(result)
        except Exception as e:
            log.exception(f'Exception while executing search query: {e}', e)
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
