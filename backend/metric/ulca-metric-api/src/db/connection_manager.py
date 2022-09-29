import sqlalchemy as db
from config import MONGO_CONNECTION_URL,MONGO_DB_SCHEMA,MONGO_MODEL_COLLECTION,DRUID_CONNECTION_URL, MONGO_BM_COLLECTION
import pymongo
import logging
import config
from logging.config import dictConfig

log = logging.getLogger('fie')

def get_data_store():
    log.info("Establishing connection with druid")
    engine      = db.create_engine(DRUID_CONNECTION_URL)  
    connection  = engine.connect()
    return connection


mongo_instance = None
mongo_bm_instance = None

class ModelRepo:
    
    def __init__(self):
       pass
    #method to instantiate mongo client object
    def instantiate(self):
        global mongo_instance
        client = pymongo.MongoClient(MONGO_CONNECTION_URL)
        mongo_instance = client[MONGO_DB_SCHEMA][MONGO_MODEL_COLLECTION]
        return mongo_instance

    #geting the mongo clent object
    def get_mongo_instance(self):
        global mongo_instance
        if not mongo_instance:
            return self.instantiate()
        else:
            return mongo_instance

    def aggregate(self, query):
        try:
            col = self.get_mongo_instance()
            res =   col.aggregate(query) 
            result = []
            for record in res:
                result.append(record)
            return result
        except Exception as e:
            log.exception(f'Exception in repo search: {e}', e)
            return []

    def count(self, query):
        try:
            col = self.get_mongo_instance()
            res =   col.count(query) 
            return res
        except Exception as e:
            log.exception(f'Exception in repo search: {e}', e)
            return 0

    def count_data_col(self, query,schema,collection):
        log.info(f"Mongo count calculation : {query},{schema},{collection}")
        print(query,schema,collection,"****counting**")
        print(config.data_connection_url)
        try:
            log.info(f"Mongo count calculation : {query},{schema},{collection}")
            client = pymongo.MongoClient(config.data_connection_url)
            mongo_instance = client[schema][collection]
            res =   mongo_instance.count(query) 
            return res
        except Exception as e:
            log.exception(f'Exception in repo search: {e}', e)
            return []

    def aggregate_data_col(self, query,schema,collection):
        log.info(f"Mongo aggregation : {query},{schema},{collection}")
        print(query,schema,collection,"***aggregating***")
        print(config.data_connection_url)
        try:
            log.info(f"Mongo count calculation : {query},{schema},{collection}")
            client = pymongo.MongoClient(config.data_connection_url)

            mongo_instance = client[schema][collection]
            res =   mongo_instance.aggregate(query) 
            result = []
            for record in res:
                result.append(record)
            return result
        except Exception as e:
            log.exception(f'Exception in repo search: {e}', e)
            return []

class BenchRepo:
    
    def __init__(self):
       pass
    #method to instantiate mongo client object
    def instantiate(self):
        global mongo_bm_instance
        client = pymongo.MongoClient(MONGO_CONNECTION_URL)
        mongo_bm_instance = client[MONGO_DB_SCHEMA][MONGO_BM_COLLECTION]
        return mongo_bm_instance

    #geting the mongo clent object
    def get_mongo_instance(self):
        global mongo_bm_instance
        if not mongo_bm_instance:
            return self.instantiate()
        else:
            return mongo_bm_instance

    def aggregate(self, query):
        try:
            col = self.get_mongo_instance()
            res =   col.aggregate(query) 
            result = []
            for record in res:
                result.append(record)
            return result
        except Exception as e:
            log.exception(f'Exception in repo search: {e}', e)
            return []

    def count(self, query):
        try:
            col = self.get_mongo_instance()
            res =   col.count(query) 
            return res
        except Exception as e:
            log.exception(f'Exception in repo search: {e}', e)
            return 0

   

#  Log config
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