import logging
import pymongo
from logging.config import dictConfig
from config import ulca_db_cluster, mongo_db_name, mongo_collection_name
from bson.objectid import ObjectId

log = logging.getLogger('file')
mongo_instance = None

class ASRMongodbComputeRepo:
    def __init__(self):
        pass

    def instantiate(self):
        global mongo_instance
        client = pymongo.MongoClient(ulca_db_cluster)
        mongo_instance = client[mongo_db_name][mongo_collection_name]
        log.info(f'mongo db and collection{mongo_db_name},{mongo_collection_name}')
        return mongo_instance

    def get_mongo_instance(self):
        global mongo_instance
        if not mongo_instance:
            log.info(f'getting mongo connection............')
            return self.instantiate()
        else:
            return mongo_instance

    def find_doc(self,modelId):
       col = self.get_mongo_instance()
       model_details = []
       try:
           res = col.find({"_id":ObjectId(modelId)})
           for r in res:
               model_details.append(r)
           return model_details
       except Exception as e:
            log.exception(f"Exception while finding modelId: {str(e)}")


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

