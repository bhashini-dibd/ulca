import logging
from logging.config import dictConfig
from configs.configs import db_cluster, db, parallel_collection

import pymongo
log = logging.getLogger('file')


mongo_instance = None

class ParallelRepo:
    def __init__(self):
        pass

    def instantiate(self):
        client = pymongo.MongoClient(db_cluster)
        mongo_instance = client[db][parallel_collection]
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
        result, res_count, pipeline, langs = [], 0, [], []
        try:
            col = self.get_mongo_instance()
            if not query:
                res = col.count({})
                result = [res]
                res_count = len(result)
                return result, pipeline, res_count
            if 'sourceLanguage' in query.keys() and 'targetLanguage' in query.keys():
                langs.append(query["sourceLanguage"])
                langs.extend(query["targetLanguage"])
                pipeline.append({"$match": {"$and": [{"sourceLanguage": {"$in": langs}}, {"targetLanguage": {"$in": langs}}]}})
            elif 'sourceLanguage' in query.keys():
                pipeline.append({"$match": {"$or": [{"sourceLanguage": query["sourceLanguage"]}, {"targetLanguage": query["sourceLanguage"]}]}})
            if "tags" in query.keys():
                pipeline.append({"$match": {"tags": {"$all": query["tags"]}}})
            if "scoreQuery" in query.keys():
                pipeline.append({"$match": query["scoreQuery"]})
            if 'groupBy' in query.keys():
                pipeline.append({"$group": {"_id": {"sourceHash": "$sourceTextHash"}, "count": {"$sum": 1}}})
                if 'countOfTranslations' in query.keys():
                    pipeline.append({"$group": {"_id": {"$cond": [{"$gt": ["$count", query["countOfTranslations"]]}, "$_id.sourceHash", "$$REMOVE"]}}})
                else:
                    pipeline.append({"$group": {"_id": {"$cond": [{"$gt": ["$count", 1]}, "$_id.sourceHash", "$$REMOVE"]}}})
            else:
                pipeline.append({"$project": {"_id": 0}})
            if "$in" in query.keys():
                pipeline = []
                pipeline.append({"$match": {"tags": query}})
                pipeline.append({"$project": {"_id": 0}})
            res = col.aggregate(pipeline, allowDiskUse=True)
            if 'groupBy' in query.keys():
                if res:
                    hashes = []
                    for record in res:
                        if record:
                            if record["_id"]:
                                hashes.append(record["_id"])
                    if hashes:
                        res_count = len(hashes)
                        res = col.find({"sourceTextHash": {"$in": hashes}}, {"_id": False})
                    map = {}
                    if not res:
                        return result, pipeline, res_count
                    for record in res:
                        if record:
                            if record["sourceTextHash"] in map.keys():
                                data_list = map[record["sourceTextHash"]]
                                if langs:
                                    if record["sourceLanguage"] in langs and record["targetLanguage"] in langs:
                                        data_list.append(record)
                                map[record["sourceTextHash"]] = data_list
                            else:
                                map[record["sourceTextHash"]] = [record]
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