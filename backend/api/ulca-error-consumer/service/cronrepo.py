import json
import redis
from configs.configs import redis_server_host, redis_server_port, redis_server_db, redis_server_password
import logging
log = logging.getLogger('file')


redis_client = None
mongo_client = None


class StoreRepo:

    def __init__(self):
        pass

    # Initialises and fetches redis client
    def redis_instantiate(self):
        redis_client = redis.Redis(host=redis_server_host, port=redis_server_port, db=redis_server_db, password=redis_server_password)
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
            return 1
        except Exception as e:
            log.exception("Exception in REPO: upsert | Cause: " + str(e), None, e)
            return None

    #deleting record by key
    def delete(self, keys):
        try:
            client = self.get_redis_instance()
            for key in keys:
                client.delete(key)
            return 1
        except Exception as e:
            log.exception("Exception in REPO: delete | Cause: " + str(e), None, e)
            return None

    #searching record by key
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
            log.exception("Exception in REPO: search | Cause: " + str(e), None, e)
            return None

    #searching all records 
    def get_all_records(self, key_list,pattern):
        try:
            client = self.get_redis_instance()
            result = []
            if not key_list:
                key_list = client.keys(pattern)
            for key in key_list:
                val = client.get(key)
                if val:
                    result.append(json.loads(val))
            return result
        except Exception as e:
            log.exception("Exception in REPO: search | Cause: " + str(e), None, e)
            return []

    #searching for keys matching a pattern
    def get_keys_matching_pattern(self,pattern):
        try:
            client = self.get_redis_instance()
            key_list = client.keys(pattern)
            if key_list:
                return key_list
            return []
        except Exception as e:
            log.exception("Exception in REPO: search | Cause: " + str(e), None, e)
            return []

    #unique srns present in store 
    def get_unique_srns(self):
        try:
            client = self.get_redis_instance()
            key_list = client.keys('*')
            #getting all the srns since key pattern is srn.uuid
            srn_keys =[(key.decode('utf-8')).split('.')[0] for key in key_list]
            unique_srns = list(set(srn_keys))
            if unique_srns:
                return unique_srns
        except Exception as e:
            log.exception("Exception in REPO: search | Cause: " + str(e), None, e)
            return []

  