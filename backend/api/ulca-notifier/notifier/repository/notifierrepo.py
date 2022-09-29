import logging
from logging.config import dictConfig
from configs.configs import ulca_db_cluster, user_db, user_collection
import pymongo
log = logging.getLogger('file')


mongo_instance = None

class NotifierRepo:
        
    #method to instantiate mongo client object
    def instantiate(self):
        global mongo_instance
        client = pymongo.MongoClient(ulca_db_cluster)
        mongo_instance = client[user_db][user_collection]
        return mongo_instance

    #geting the mongo clent object
    def get_mongo_instance(self):
        global mongo_instance
        if not mongo_instance:
            return self.instantiate()
        else:
            return mongo_instance

    #insert operation on mongo
    def insert(self, data):
        col = self.get_mongo_instance()
        if isinstance(data, dict):
            data = [data]
        col.insert_many(data)
        return len(data)

    # Updates the object in the mongo collection
    def update(self, cond,object_in,upsert_flag):
        col = self.get_mongo_instance()
        col.update(cond, object_in,upsert = upsert_flag)

    # delete a single object in the mongo collection
    def delete(self, rec_id):
        col = self.get_mongo_instance()
        col.delete_one({"id": rec_id})

    # delete multiple objects in the mongo collection
    def remove(self, query):
        col = self.get_mongo_instance()
        col.delete_many(query)

    # Searches the object from mongo collection
    def search(self, query, exclude, offset, res_limit):
        try:
            col = self.get_mongo_instance()
            if offset is None and res_limit is None:
                res = col.find(query, exclude).sort([('_id', 1)])
            else:
                res = col.find(query, exclude).sort([('_id', -1)]).skip(offset).limit(res_limit)
            result = []
            for record in res:
                result.append(record)
            return result
        except Exception as e:
            log.exception(f'Exception in repo search: {e}', e)
            return []

    #mongo record count     
    def count(self,query):
        try:
            col = self.get_mongo_instance()
            count = col.count(query)
            return count
        except Exception as e:
            log.exception(f'Exception in repo count: {e}', e)

    #mongo upsert 
    def upsert(self, object_in):
        try:
            col = self.get_mongo_instance()
            col.update(object_in,{ '$inc': { 'count': 1 } }, upsert=True)
        except Exception as e:
            log.exception(f'Exception in repo upsert: {e}', e)



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