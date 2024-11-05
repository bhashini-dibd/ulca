import logging
from collections import OrderedDict
from logging.config import dictConfig

from bson import ObjectId

from configs.configs import db_cluster, db, ner_collection

import pymongo
log = logging.getLogger('file')


mongo_instance_ner = None

class NERRepo:
    def __init__(self):
        pass

    # Method to set ner Mongo DB collection
    def set_ner_collection(self):
        if "localhost" not in db_cluster:
            log.info(f'Setting the Mongo ner DS Shard Cluster up.....')
            client = pymongo.MongoClient(db_cluster)
            ulca_db = client[db]
            ulca_db.drop_collection(ner_collection)
            ulca_col = ulca_db[ner_collection]
            ulca_col.create_index([("tags", -1)])
            db_cli = client.admin
            key = OrderedDict([("_id", "hashed")])
            db_cli.command({'shardCollection': f'{db}.{ner_collection}', 'key': key})
            log.info(f'Done!')
        else:
            log.info(f'Setting the Mongo DB Local for ner DS....')
            client = pymongo.MongoClient(db_cluster)
            ulca_db = client[db]
            ulca_db.drop_collection(ner_collection)
            ulca_col = ulca_db[ner_collection]
            ulca_col.create_index([("tags", -1)])
            log.info(f'Done!')

    # Initialises and fetches mongo db client
    def instantiate(self):
        global mongo_instance_ner
        client = pymongo.MongoClient(db_cluster)
        mongo_instance_ner = client[db][ner_collection]
        return mongo_instance_ner

    def get_mongo_instance(self):
        global mongo_instance_ner
        if mongo_instance_ner is None:
            return self.instantiate()
        else:
            return mongo_instance_ner

    def insert(self, data):
        col = self.get_mongo_instance()
        col.insert_many(data)
        return len(data)

    # Updates the object in the mongo collection
    def delete(self, rec_id):
        col = self.get_mongo_instance()
        col.delete_one({"id": rec_id})

    # Updates the object in the mongo collection
    def update(self, object_in):
        col = self.get_mongo_instance()
        try:
            object_in["_id"] = ObjectId(object_in["_id"])
            col.replace_one({"_id": object_in["_id"]}, object_in, False)
        except Exception as e:
            log.exception(f"Exception while updating: {e}", e)

    def search(self, query, exclude, offset, res_limit):
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