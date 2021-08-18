import pymongo
import logging
import config
from logging.config import dictConfig

log = logging.getLogger('fie')



class NotifierRepo:
    
    def __init__(self):
       pass

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

    def count_process_col(self, query,schema,collection):
        log.info(f"Mongo count calculation : {query},{schema},{collection}")
        print(query,schema,collection,"****counting**")
        print(config.process_connection_url)
        try:
            log.info(f"Mongo count calculation : {query},{schema},{collection}")
            client = pymongo.MongoClient(config.process_connection_url)
            mongo_instance = client[schema][collection]
            res =   mongo_instance.count(query) 
            return res
        except Exception as e:
            log.exception(f'Exception in repo search: {e}', e)
            return []

    def aggregate_process_col(self, query,schema,collection):
        log.info(f"Mongo aggregation : {query},{schema},{collection}")
        print(query,schema,collection,"***aggregating***")
        print(config.process_connection_url)
        try:
            log.info(f"Mongo count calculation : {query},{schema},{collection}")
            client = pymongo.MongoClient(config.process_connection_url)

            mongo_instance = client[schema][collection]
            
            res =   mongo_instance.aggregate(query)
            result = []
            for record in res:
                result.append(record)
            return result
        except Exception as e:
            log.exception(f'Exception in repo search: {e}', e)
            return []

    

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