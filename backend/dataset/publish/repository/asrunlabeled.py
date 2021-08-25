import logging
from collections import OrderedDict
from datetime import datetime
from logging.config import dictConfig
from configs.configs import db_cluster, db, asr_unlabeled_collection

import pymongo
log = logging.getLogger('file')


mongo_instance_asr_unlabeled = None

class ASRUnlabeledRepo:
    def __init__(self):
        pass

    # Method to set ASR Mongo DB collection
    def set_asr_unlabeled_collection(self):
        if "localhost" not in db_cluster:
            log.info(f'Setting the Mongo ASR UNLABELED DS Shard Cluster up..... | {datetime.now()}')
            client = pymongo.MongoClient(db_cluster)
            ulca_db = client[db]
            ulca_db.drop_collection(asr_unlabeled_collection)
            ulca_col = ulca_db[asr_unlabeled_collection]
            ulca_col.create_index([("tags", -1)])
            db_cli = client.admin
            key = OrderedDict([("_id", "hashed")])
            db_cli.command({'shardCollection': f'{db}.{asr_unlabeled_collection}', 'key': key})
            log.info(f'Done! | {datetime.now()}')
        else:
            log.info(f'Setting the Mongo DB Local for ASR UNLABELED DS.... | {datetime.now()}')
            client = pymongo.MongoClient(db_cluster)
            ulca_db = client[db]
            ulca_db.drop_collection(asr_unlabeled_collection)
            ulca_col = ulca_db[asr_unlabeled_collection]
            ulca_col.create_index([("tags", -1)])
            log.info(f'Done! | {datetime.now()}')

    # Initialises and fetches mongo db client
    def instantiate(self):
        global mongo_instance_asr_unlabeled
        client = pymongo.MongoClient(db_cluster)
        mongo_instance_asr_unlabeled = client[db][asr_unlabeled_collection]
        return mongo_instance_asr_unlabeled

    def get_mongo_instance(self):
        global mongo_instance_asr_unlabeled
        if not mongo_instance_asr_unlabeled:
            return self.instantiate()
        else:
            return mongo_instance_asr_unlabeled

    def insert(self, data):
        col = self.get_mongo_instance()
        col.insert_many(data)
        return len(data)

    def update(self, object_in):
        col = self.get_mongo_instance()
        col.replace_one({"id": object_in["id"]}, object_in)

    def delete(self, rec_id):
        col = self.get_mongo_instance()
        col.delete_one({"id": rec_id})

    def search(self, query, exclude, offset, res_limit):
        try:
            seconds, hours = 0, 0
            col = self.get_mongo_instance()
            if offset is None and res_limit is None:
                res = col.find(query, exclude).sort([('_id', 1)])
            else:
                res = col.find(query, exclude).sort([('_id', -1)]).skip(offset).limit(res_limit)
            result = []
            for record in res:
                if 'durationInSeconds' in record.keys():
                    seconds += record["durationInSeconds"]
                result.append(record)
            if seconds != 0:
                hours = seconds/3600
            return result, round(hours, 3)
        except Exception as e:
            log.exception(e)
            return [], 0


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