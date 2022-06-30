import json
import logging
from logging.config import dictConfig

import redis

from configs.configs import ulca_db_cluster, pt_db, pt_task_collection, redis_server_host, redis_server_port, redis_server_pass, pt_redis_db

import pymongo
log = logging.getLogger('file')

redis_client = None
mongo_instance = None

class PTRepo:
    def __init__(self):
        pass

    def instantiate(self):
        global mongo_instance
        client = pymongo.MongoClient(ulca_db_cluster)
        mongo_instance = client[pt_db][pt_task_collection]
        return mongo_instance

    def get_mongo_instance(self):
        global mongo_instance
        if not mongo_instance:
            log.info(f'getting mongo connection............')
            return self.instantiate()
        else:
            return mongo_instance

    def insert(self, data):
        col = self.get_mongo_instance()
        if isinstance(data, dict):
            data = [data]
        col.insert_many(data)
        return len(data)

    # Updates the object in the mongo collection
    def update(self, object_in):
        col = self.get_mongo_instance()
        col.replace_one({"id": object_in["id"]}, object_in)

    # Updates the object in the mongo collection
    def delete(self, rec_id):
        col = self.get_mongo_instance()
        col.delete_one({"id": rec_id})

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


    # Initialises and fetches redis client
    def redis_instantiate(self):
        global redis_client
        redis_client = redis.Redis(host=redis_server_host, port=redis_server_port, db=pt_redis_db, password=redis_server_pass)
        return redis_client

    def get_redis_instance(self):
        global redis_client
        if not redis_client:
            log.info(f'getting redis connection............')
            return self.redis_instantiate()
        else:
            return redis_client

    def redis_upsert(self, key, value):
        try:
            client = self.get_redis_instance()
            client.set(key, json.dumps(value))
            return True
        except Exception as e:
            log.exception(f'Exception in redis upsert: {e}', e)
            return None

    def redis_delete(self, key):
        try:
            client = self.get_redis_instance()
            client.delete(key)
            return 1
        except Exception as e:
            log.exception(f'Exception in redis delete: {e}', e)
            return None

    def redis_search(self, key_list):
        try:
            client = self.get_redis_instance()
            result = []
            for key in key_list:
                val = client.get(key)
                if val:
                    result.append(json.loads(val))
            return result
        except Exception as e:
            log.exception(f'Exception in redis search: {e}', e)
            return None

    def redis_key_inc(self, key, duration, error):
        try:
            client = self.get_redis_instance()
            value = "validateSuccess"
            if error:
                value = "validateError"
            val = client.hgetall(key)
            if val:
                client.hincrby(key, value, 1)

            if duration:
                valueDuration = "validateSuccessSeconds"
                if error:
                    valueDuration = "validateErrorSeconds"
                valduration = client.hgetall(key)
                if valduration:
                    client.hincrbyfloat(key, valueDuration, duration)
        except Exception as e:
            log.exception(f'Exception in redis search: {e}', e)
            return None

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