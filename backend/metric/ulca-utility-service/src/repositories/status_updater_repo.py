import logging
from logging.config import dictConfig
from config import process_connection_url , process_db_schema, process_col
import pymongo
log = logging.getLogger('file')


class StatusUpdaterRepo:
    
    def __init__(self):
        pass
        
    #method to instantiate mongo client object
    def get_mongo_instance(self,database,collection):
        global mongo_instance
        client = pymongo.MongoClient(process_connection_url)
        mongo_instance = client[database][collection]
        return mongo_instance

    # Updates the object in the mongo collection
    def update(self, cond,query,multi_flag,db,col):
        try:
            col = self.get_mongo_instance(db,col)
            col.update(cond, query,multi=multi_flag)
        except Exception as e:
            print(e)
            log.info(f'Exception while updating document : {e}')

    def find(self, query, db, col):
        try:
            col = self.get_mongo_instance(db,col)
            res = col.find(query)
            results = []
            for record in res:
                results.append(record)
            return results
        except Exception as e:
            log.exception(f'Exception in repo find: {e}',e)
            return []

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