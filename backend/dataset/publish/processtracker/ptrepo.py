import json
import logging
from logging.config import dictConfig

import redis

from configs.configs import ulca_db_cluster, pt_db, pt_task_collection, redis_server_host, \
    redis_server_port, redis_server_pass, pt_redis_db

import pymongo
log = logging.getLogger('file')


redis_client = None
mongo_instance_pt = None

class PTRepo:
    def __init__(self):
        pass

    # Initialises and fetches mongo db client
    def instantiate(self):
        global mongo_instance_pt
        client = pymongo.MongoClient(ulca_db_cluster)
        mongo_instance_pt = client[pt_db][pt_task_collection]
        return mongo_instance_pt

    def get_mongo_instance(self):
        global mongo_instance_pt
        if not mongo_instance_pt:
            return self.instantiate()
        else:
            return mongo_instance_pt

    def insert(self, data):
        col = self.get_mongo_instance()
        if isinstance(data, dict):
            data = [data]
        col.insert_many(data)
        return len(data)

    def update(self, object_in):
        col = self.get_mongo_instance()
        col.replace_one({"id": object_in["id"]}, object_in)

    def delete(self, rec_id):
        col = self.get_mongo_instance()
        col.delete_one({"id": rec_id})

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
        redis_client = redis.Redis(host=redis_server_host, port=redis_server_port, db=pt_redis_db,
                                   password=redis_server_pass)
        return redis_client

    def get_redis_instance(self):
        global redis_client
        if not redis_client:
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

    def redis_key_inc(self, key, seconds, error):
        try:
            key = f'ServiceRequestNumber_{key}'
            client = self.get_redis_instance()
            value = "publishSuccess"
            sec_value = "publishSuccessSeconds"
            if error:
                value = "publishError"
                sec_value = "publishErrorSeconds"
            val = client.hgetall(key)
            if val:
                client.hincrby(key, value, 1)
                if seconds:
                    client.hincrbyfloat(key, sec_value, seconds)
        except Exception as e:
            log.exception(f'Exception in redis_key_inc: {e}', e)
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

