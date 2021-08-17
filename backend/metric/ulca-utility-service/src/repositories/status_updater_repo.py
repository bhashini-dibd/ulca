import logging
from logging.config import dictConfig
from config import process_connection_url , process_db_schema, process_col
import pymongo
log = logging.getLogger('file')


process_mongo_instance = None

class StatusUpdaterRepo:
    
    def __init__(self):
        pass
        
    #method to instantiate mongo client object
    def instantiate(self):
        global mongo_instance
        client = pymongo.MongoClient(process_connection_url)
        process_mongo_instance = client[process_db_schema][process_col]
        return process_mongo_instance

    #geting the mongo clent object
    def get_mongo_instance(self):
        global process_mongo_instance
        if not process_mongo_instance:
            return self.instantiate()
        else:
            return process_mongo_instance

    # Updates the object in the mongo collection
    def update(self, cond,query):
        try:
            col = self.get_mongo_instance()
            col.update(cond, query)
        except Exception as e:
            print(e)
            log.info(f'Exception while updating document : {e}')

    def insert(self, object_in):
        col = self.get_mongo_instance()
        col.insert(object_in)



    def aggregate(self, query):
        
        try:
            col = self.get_mongo_instance()
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