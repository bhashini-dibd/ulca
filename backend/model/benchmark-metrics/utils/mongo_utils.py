import logging
import pymongo
from logging.config import dictConfig
from configs.configs import ulca_db_cluster, mongo_db_name, mongo_collection_name, mongo_pt_collection_name
from datetime import datetime, timezone
from pymongo import ReturnDocument
from time import time

log = logging.getLogger('file')
mongo_instance = None
pt_mongo_instance = None

class BenchMarkingProcessRepo:
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


    def insert(self, data):
        
        col = self.get_mongo_instance()
        # benchmark_docs = []
        try:
            # docs = col.find({'benchmarkingProcessId':data['benchmarkingProcessId'], 'benchmarkDatasetId':data['benchmarkDatasetId']})
            # if docs:
            #     for doc in docs:
            #         log.info(f"Document found: {doc}")
            #         if doc:
            #             benchmark_docs.append(doc)
            #     # doc_id = doc[0]['_id']
            #     if benchmark_docs:
            # res = col.update({'benchmarkingProcessId':data['benchmarkingProcessId'], 'benchmarkDatasetId':data['benchmarkDatasetId']}, {"$set": {"score": data['eval_score'], "status": "Completed"} }, False, False, True)
            #curr_time = datetime.now(timezone.utc).strftime("%a %b %d %H:%M:%S %Z %Y")
            curr_time = eval(str(time.time()).replace('.', '')[0:13])
            log.info(f'data {data}')
            if data['eval_score'] is not None:
                res = col.find_one_and_update({"benchmarkProcessId": data["benchmarkingProcessId"], "benchmarkDatasetId": data["benchmarkDatasetId"]}, {"$set": {"score": data['eval_score'],  "status": "Completed", "lastModifiedOn": curr_time} },return_document =  ReturnDocument.AFTER)#, False, False, None, None)
                log.info(f'result of update data in collection benchmarkprocess {res}')
                #for re in res:
                #log.info(f'updated result of modified count {res.modified_count}')
                #log.info(f'updated result of matched count {res.matched_count}')
                #log.info(f'updated result of matched count {res.raw_result}')
                #fin = col.find({"benchmarkProcessId": data["benchmarkingProcessId"]})
                #log.info(f'fin is {fin}')
                    
            else:
                res = col.update_one({"benchmarkProcessId": data["benchmarkingProcessId"], "benchmarkDatasetId": data["benchmarkDatasetId"]}, {"$set": {"score": data['eval_score'], "status": "Failed", "lastModifiedOn": curr_time} }, False, False, None, None)

            # col.update_one({"_id":doc_id}, {"$set": {"status": "Completed" }}, False, True)
            # log.info(res)
            #if res["nModified"] == 1:
             #   log.info(f"Updated evaluation score for becnhmarkingProcessId: {data['benchmarkingProcessId']}")
            #else:
            #    log.error(f"Document not found for benchmarkingProcessId: {data['benchmarkingProcessId']} and datasetId: {data['benchmarkDatasetId']} eval_score: {data['eval_score']}")
            #     else:
            #         log.error(f"Document not found for benchmarkingProcessId: {data['benchmarkingProcessId']} and datasetId: {data['benchmarkDatasetId']}")
            # else:
            #     log.error(f"Document not found for benchmarkingProcessId: {data['benchmarkingProcessId']} and datasetId: {data['benchmarkDatasetId']}")
        except Exception as e:
            log.exception(f"Exception while updating database with evaluation score: {str(e)}")

    def pt_instantiate(self):
        global pt_mongo_instance
        client = pymongo.MongoClient(ulca_db_cluster)
        pt_mongo_instance = client[mongo_db_name][mongo_pt_collection_name]
        return pt_mongo_instance

    def get_mongo_pt_instance(self):
        global pt_mongo_instance
        if not pt_mongo_instance:
            log.info(f'getting mongo process tracker connection............')
            return self.pt_instantiate()
        else:
            return pt_mongo_instance

    def insert_pt(self, data):

        col = self.get_mongo_pt_instance()

        try:
            #curr_time = datetime.now(timezone.utc).strftime("%a %b %d %H:%M:%S %Z %Y")
            curr_time = eval(str(time.time()).replace('.', '')[0:13])
            res = col.find_one_and_update({"benchmarkProcessId": data["benchmarkingProcessId"], "tool": "benchmark"}, {"$set": {"status": data["status"], "endTime": curr_time} },return_document =  ReturnDocument.AFTER)#False, False, None, None)
            log.info(f'result of update data in collection ulca-bm-tasks {res}')
            log.info(f' updated data in collection ulca-bm-tasks {data}')
            #if res["nModified"] == 1:
            #    log.info(f"Updated process tracker for becnhmarkingProcessId: {data['benchmarkingProcessId']}")
            ##else:
              #  log.error(f"Document not found in process tracker for benchmarkingProcessId: {data['benchmarkingProcessId']}")

        except Exception as e:
            log.exception(f"Exception while updating process tracker for benchmarkingProcessId: {data['benchmarkingProcessId']}: {str(e)}")

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