import logging
from collections import OrderedDict
from logging.config import dictConfig
from configs.configs import db_cluster, db, monolingual_collection

import pymongo
log = logging.getLogger('file')


mongo_instance = None

class MonolingualRepo:
    def __init__(self):
        pass

    def set_monolingual_collection(self):
        if "localhost" not in db_cluster:
            log.info(f'Setting the Mongo Monolingual DS Shard Cluster up.....')
            client = pymongo.MongoClient(db_cluster)
            ulca_db = client[db]
            ulca_db.drop_collection(monolingual_collection)
            ulca_col = ulca_db[monolingual_collection]
            ulca_col.create_index([("tags", -1)])
            ulca_col.create_index([("language", -1)])
            ulca_col.create_index([("textHash", -1)])
            db_cli = client.admin
            key = OrderedDict([("_id", "hashed")])
            db_cli.command({'shardCollection': f'{db}.{monolingual_collection}', 'key': key})
            log.info(f'Done!')
        else:
            log.info(f'Setting the Mongo DB Local for Monolingual DS....')
            client = pymongo.MongoClient(db_cluster)
            ulca_db = client[db]
            ulca_db.drop_collection(monolingual_collection)
            ulca_col = ulca_db[monolingual_collection]
            ulca_col.create_index([("tags", -1)])
            ulca_col.create_index([("language", -1)])
            ulca_col.create_index([("textHash", -1)])
            log.info(f'Done!')

    def instantiate(self):
        client = pymongo.MongoClient(db_cluster)
        mongo_instance = client[db][monolingual_collection]
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

    # Updates the object in the mongo collection
    def update(self, object_in):
        col = self.get_mongo_instance()
        col.replace_one({"id": object_in["id"]}, object_in)

    # Searches the object into mongo collection
    def search(self, query, exclude, offset, res_limit):
        col = self.get_mongo_instance()
        if offset is None and res_limit is None:
            res = col.find(query, exclude).sort([('_id', 1)])
        else:
            res = col.find(query, exclude).sort([('_id', -1)]).skip(offset).limit(res_limit)
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