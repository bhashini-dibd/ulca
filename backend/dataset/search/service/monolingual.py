import logging
from datetime import datetime
from logging.config import dictConfig
from configs.configs import offset, limit, search_output_topic, sample_size
from repository.monolingual import MonolingualRepository
from utils.datasetutils import DatasetUtils
from kafkawrapper.producer import Producer

log = logging.getLogger('file')

mongo_instance = None
repo = MonolingualRepository()
utils = DatasetUtils()
prod = Producer()

class MonolingualService:
    def __init__(self):
        pass

    # Method for searching asr datasets
    def get_monolingual_dataset(self, query):
        log.info(f'Fetching datasets..... | {datetime.now()}')
        try:
            off = query["offset"] if 'offset' in query.keys() else offset
            lim = query["limit"] if 'limit' in query.keys() else limit
            db_query, tags = {}, []
            if 'age' in query.keys():
                db_query["age"] = query["age"]
            if 'language' in query.keys():
                tags.append(query["language"])
            if 'collectionMode' in query.keys():
                tags.append(query["collectionMode"])
            if 'collectionSource' in query.keys():
                tags.append(query["collectionMode"])
            if 'license' in query.keys():
                tags.append(query["licence"])
            if 'domain' in query.keys():
                tags.append(query["domain"])
            if 'channel' in query.keys():
                tags.append(query["channel"])
            if 'gender' in query.keys():
                tags.append(query["gender"])
            if 'datasetId' in query.keys():
                tags.append(query["datasetId"])
            if 'datasetType' in query.keys():
                tags.append(query["datasetType"])
            if tags:
                db_query["tags"] = tags
            exclude = {"_id": False}
            data = repo.search(db_query, exclude, off, lim)
            result, query, count = data[0], data[1], data[2]
            log.info(f'Result --- Count: {count}, Query: {query}')
            path = utils.push_result_to_s3(result, query["serviceRequestNumber"])
            if path:
                size = sample_size
                if count <= 10:
                    size = count
                op = {"serviceRequestNumber": query["serviceRequestNumber"], "count": count, "sample": result[:size], "dataset": path}
            else:
                log.error(f'There was an error while pushing result to S3')
                op = {"serviceRequestNumber": query["serviceRequestNumber"], "count": 0, "sample": [], "dataset": None}
            prod.produce(op, search_output_topic, None)
            log.info(f'Done!')
            return op
        except Exception as e:
            log.exception(e)
            return {"message": str(e), "status": "FAILED", "dataset": "NA"}


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