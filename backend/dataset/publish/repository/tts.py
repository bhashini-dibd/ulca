import logging
from collections import OrderedDict
from datetime import datetime
from logging.config import dictConfig

from bson import ObjectId

from configs.configs import db_cluster, db, tts_collection

import pymongo
log = logging.getLogger('file')


mongo_instance_tts = None

class TTSRepo:
    def __init__(self):
        pass

    # Method to set ASR Mongo DB collection
    def set_tts_collection(self):
        if "localhost" not in db_cluster:
            log.info(f'Setting the Mongo TTS DS Shard Cluster up..... | {datetime.now()}')
            client = pymongo.MongoClient(db_cluster)
            ulca_db = client[db]
            ulca_db.drop_collection(tts_collection)
            ulca_col = ulca_db[tts_collection]
            ulca_col.create_index([("tags", -1)])
            db_cli = client.admin
            key = OrderedDict([("_id", "hashed")])
            db_cli.command({'shardCollection': f'{db}.{tts_collection}', 'key': key})
            log.info(f'Done! | {datetime.now()}')
        else:
            log.info(f'Setting the Mongo DB Local for TTS DS.... | {datetime.now()}')
            client = pymongo.MongoClient(db_cluster)
            ulca_db = client[db]
            ulca_db.drop_collection(tts_collection)
            ulca_col = ulca_db[tts_collection]
            ulca_col.create_index([("tags", -1)])
            log.info(f'Done! | {datetime.now()}')

    # Initialises and fetches mongo db client
    def instantiate(self):
        global mongo_instance_tts
        client = pymongo.MongoClient(db_cluster)
        mongo_instance_tts = client[db][tts_collection]
        return mongo_instance_tts

    def get_mongo_instance(self):
        global mongo_instance_tts
        if not mongo_instance_tts:
            return self.instantiate()
        else:
            return mongo_instance_tts

    def insert(self, data):
        col = self.get_mongo_instance()
        col.insert_many(data)
        return len(data)

    def update(self, object_in):
        col = self.get_mongo_instance()
        try:
            object_in["_id"] = ObjectId(object_in["_id"])
            col.replace_one({"_id": object_in["_id"]}, object_in, False)
        except Exception as e:
            log.exception(f"Exception while updating: {e}", e)

    def delete(self, rec_id):
        col = self.get_mongo_instance()
        col.delete_one({"id": rec_id})

    def search(self, query, exclude, offset, res_limit):
        try:
            seconds, hours = 0, 0
            col = self.get_mongo_instance()
            if offset is None and res_limit is None:
                if exclude:
                    res = col.find(query, exclude).sort([('_id', 1)])
                    log.info(f'with exclude and without offset')
                    res_list = list(res)
                    log.info(f'query result count:: {len(res_list)}')
                    log.info(f'Query: {query}')
                    log.info(f'Projection:{exclude}')
                else:
                    res = col.find(query).sort([('_id', 1)])
                    log.info(f'without exclude and without offset')
                    res_list = list(res)
                    log.info(f'query result count:: {len(res_list)}')
                    log.info(f'Query: {query}')
                    log.info(f'Projection:{exclude}') 
            else:
                if exclude:
                    res = col.find(query, exclude).sort([('_id', -1)]).skip(offset).limit(res_limit)
                    log.info(f'with exclude and with offset')
                    res_list = list(res)
                    log.info(f'query result count:: {len(res_list)}')
                    log.info(f'Query: {query}')
                    log.info(f'Projection:{exclude}') 
                else:
                    res = col.find(query).sort([('_id', -1)]).skip(offset).limit(res_limit)
                    log.info(f'without exclude and with offset')
                    res_list = list(res)
                    log.info(f'query result count:: {len(res_list)}')
                    log.info(f'Query: {query}')
                    log.info(f'Projection:{exclude}')
                    
            result = []
            for record in res:
                if "_id" in record.keys():
                    record["_id"] = str(record["_id"])
                if 'durationInSeconds' in record.keys():
                    log.info(f'seconds :: {record["durationInSeconds"]}')
                    seconds += record["durationInSeconds"]
                result.append(record)
            if seconds != 0:
                hours = seconds/3600

            log.info(f'final result:: {result}')    
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