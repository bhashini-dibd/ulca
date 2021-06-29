import logging
from logging.config import dictConfig
from configs.configs import ulca_db_cluster, error_db, error_collection

import pymongo
log = logging.getLogger('file')


mongo_instance = None

class ErrorRepo:
    
    def __init__(self):
        client = pymongo.MongoClient(ulca_db_cluster)
        mongo_instance = client[error_db][error_collection]
        try:
            mongo_instance.create_index('serviceRequestNumber')
        except pymongo.errors.DuplicateKeyError:
            log.info("duplicate key, ignoring")
        except Exception as e:
            log.exception(f"Exception on index creation :{e}")
        

    def instantiate(self):
        global mongo_instance
        client = pymongo.MongoClient(ulca_db_cluster)
        mongo_instance = client[error_db][error_collection]
        return mongo_instance

    def get_mongo_instance(self):
        global mongo_instance
        if not mongo_instance:
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

    def remove(self, query):
        col = self.get_mongo_instance()
        col.remove(query)

    # Searches the object into mongo collection
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