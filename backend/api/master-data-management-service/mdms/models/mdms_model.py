import json
import redis
from config import redis_server_host, redis_server_port, redis_server_db, redis_server_password
import logging
log = logging.getLogger('file')


redis_client = None
mongo_client = None


class StoreModel:

    def __init__(self):
        pass

    # Initialises and fetches redis client
    def redis_instantiate(self):
        redis_client = redis.Redis(host=redis_server_host, port=redis_server_port, db=redis_server_db, password=redis_server_password)
        log.info(f'redis connection {redis_client}')
        return redis_client

    #geting the redis clent object
    def get_redis_instance(self):
        if not redis_client:
            return self.redis_instantiate()
        else:
            return redis_client

    #storing records against keys
    def upsert(self, key, value, expiry_seconds):
        try:
            client = self.get_redis_instance()     
            client.set(key, json.dumps(value),ex=expiry_seconds)
            log.info(f'Redis Client {client}')
            return 1
        except Exception as e:
            log.exception("Exception in REPO: upsert | Cause: " + str(e))
            return None

    #deleting record by key
    def delete(self, keys):
        try:
            client = self.get_redis_instance()
            for key in keys:
                client.delete(key)
            return 1
        except Exception as e:
            log.exception("Exception in REPO: delete | Cause: " + str(e))
            return None

    #searching record by key
    def search(self, key_list):
        try:
            client = self.get_redis_instance()
            result = {}
            for key in key_list:
                val = client.get(key)
                log.info(f'result of redis output {val}')
                if val:
                    result[key]=json.loads(val)
                if result["languages"]:
                    result["languages"] = sorted(result['languages'], key=lambda d: d['code'])
                    result["languages"] = sorted(result['languages'], key=lambda d: d['label'])
            return result
        except Exception as e:
            log.exception("Exception in REPO: search | Cause: " + str(e))
            return None



  