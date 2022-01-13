import logging
from logging.config import dictConfig
from config import ulca_db_cluster, process_db, process_collection
import pymongo
log = logging.getLogger('file')


mongo_instance = None

class ProcessRepo:
    
    def __init__(self):
        pass
        
    #method to instantiate mongo client object
    def instantiate(self):
        global mongo_instance
        client = pymongo.MongoClient(ulca_db_cluster)
        mongo_instance = client[process_db]
        return mongo_instance

    #geting the mongo clent object
    def get_mongo_instance(self):
        global mongo_instance
        if not mongo_instance:
            return self.instantiate()
        else:
            return mongo_instance

    #aggregate operation on mongo
    def aggregate(self, query,collection):
        log.info(f"Mongo aggregation : {query}")
        try:
            client  =   self.get_mongo_instance()
            col     =   client[collection]
            res     =   col.aggregate(query) 
            result  =   []
            for record in res:
                result.append(record)
            return result
        except Exception as e:
            log.exception(f'Exception in repo search: {e}')
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