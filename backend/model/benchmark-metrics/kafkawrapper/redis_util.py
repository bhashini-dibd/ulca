import json
import logging
# import pymongo
from datetime import datetime
from logging.config import dictConfig

import redis

from configs.configs import redis_server_host, redis_server_port, redis_server_pass, record_expiry_in_sec, benchmark_metrics_dedup_redis_db

log = logging.getLogger('file')

# mongo_instance = None
redis_dedup_records = None

class RedisUtil:
    def __init__(self):
        pass

    # # Method to set Dataset Mongo DB
    # def set_dataset_db(self):
    #     if "localhost" not in db_cluster:
    #         log.info(f'Setting the Mongo Sharded DB up.....')
    #         client = pymongo.MongoClient(db_cluster)
    #         client.drop_database(db)
    #         ulca_db = client[db]
    #         db_cli = client.admin
    #         db_cli.command('enableSharding', db)
    #         log.info(f'Done! | {datetime.now()}')
    #     else:
    #         log.info(f'Setting the Mongo DB Local...')

    # Initialises and fetches redis client
    def redis_instantiate(self):
        global redis_dedup_records
        redis_dedup_records = redis.Redis(host=redis_server_host, port=redis_server_port, db=benchmark_metrics_dedup_redis_db,
                                   password=redis_server_pass)
        return redis_dedup_records

    def get_redis_instance(self):
        global redis_dedup_records
        if not redis_dedup_records:
            return self.redis_instantiate()
        else:
            return redis_dedup_records

    def upsert(self, key, value, expiry):
        try:
            client = self.get_redis_instance()
            if expiry:
                client.set(key, json.dumps(value), ex=record_expiry_in_sec)
            else:
                client.set(key, json.dumps(value))
            return True
        except Exception as e:
            log.exception(f'Exception in redis upsert: {e}', e)
            return None

    def search(self, key_list):
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