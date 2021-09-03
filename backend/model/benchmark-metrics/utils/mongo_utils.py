import logging
import pymongo
from logging.config import dictConfig
from configs.configs import ulca_db_cluster, mongo_db_name, mongo_collection_name


log = logging.getLogger('file')
mongo_instance = None


class BenchMarkingProcessRepo:
    def __init__(self):
        pass

    def instantiate(self):
        global mongo_instance
        client = pymongo.MongoClient(ulca_db_cluster)
        mongo_instance = client[mongo_db_name][mongo_collection_name]
        return mongo_instance

    def get_mongo_instance(self):
        global mongo_instance
        if not mongo_instance:
            log.info(f'getting mongo connection............')
            return self.instantiate()
        else:
            return mongo_instance

    
    def insert(self, data):
        
        col = self.get_mongo_instance()
        benchmark_docs = []
        try:
            docs = col.find({'benchmarkingProcessId':data['benchmarkingProcessId'], 'benchmarkDatasetId':data['benchmarkDatasetId']})
            if docs:
                for doc in docs:
                    if doc:
                        benchmark_docs.append(doc)
                # doc_id = doc[0]['_id']
                col.update_one({"_id":benchmark_docs[0]['_id']}, {"$set": {"score": data['eval_score'], "status": "Completed"} }, False, True)
                # col.update_one({"_id":doc_id}, {"$set": {"status": "Completed" }}, False, True)
                log.info(f"Updated evaluation score for becnhmarkingProcessId: {data['benchmarkingProcessId']}")
            else:
                log.error(f"Document not found for benchmarkingProcessId: {data['benchmarkingProcessId']} and datasetId: {data['benchmarkDatasetId']}")
        except Exception as e:
            log.exception(f"Exception while updating database with evaluation score: {str(e)}")

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