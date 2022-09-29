from config import data_connection_url
import pymongo
import logging
from logging.config import dictConfig
log = logging.getLogger('file')


class DataRepo:
    
    def __init__(self):
        pass
        
    #method to instantiate mongo client object
    def get_mongo_instance(self,database,collection):
        global mongo_instance
        client = pymongo.MongoClient(data_connection_url)
        mongo_instance = client[database][collection]
        return mongo_instance

    # Updates the object in the mongo collection
    def update(self, cond,query,db,col):
        try:
            col = self.get_mongo_instance(db,col)
            col.update(cond, query)
        except Exception as e:
            print(e)
            log.info(f'Exception while updating document : {e}')

    def distinct(self, field,db,col):
        try:
            col = self.get_mongo_instance(db,col)
            res = col.distinct(field)
            return res
        except Exception as e:
            print(e)
            log.info(f'Exception while updating document : {e}')


    def aggregate(self, query,db,col):
        
        try:
            col = self.get_mongo_instance(db,col)
            res = col.aggregate(query)
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