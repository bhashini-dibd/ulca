import logging
from datetime import datetime
from logging.config import dictConfig
from configs.configs import offset, limit, search_output_topic, sample_size
from repository.parallel import ParallelRepo
from utils.datasetutils import DatasetUtils
from kafkawrapper.producer import Producer

log = logging.getLogger('file')

mongo_instance = None
repo = ParallelRepo()
utils = DatasetUtils()
prod = Producer()


class ParallelService:
    def __init__(self):
        pass

    def get_parallel_dataset(self, query):
        log.info(f'Fetching datasets..... | {datetime.now()}')
        try:
            off = query["offset"] if 'offset' in query.keys() else offset
            lim = query["limit"] if 'limit' in query.keys() else limit
            db_query = {}
            score_query = {}
            if 'minScore' in query.keys():
                score_query["$gte"] = query["minScore"]
            if 'maxScore' in query.keys():
                score_query["$lte"] = query["maxScore"]
            if score_query:
                db_query["scoreQuery"] = {"data.score": score_query}
            if 'score' in query.keys():
                db_query["scoreQuery"] = {"data.score": query["score"]}
            tags, src_lang, tgt_lang = [], None, []
            if 'sourceLanguage' in query.keys():
                src_lang = query["sourceLanguage"]
            if 'targetLanguage' in query.keys():
                for tgt in query["targetLanguage"]:
                    tgt_lang.append(tgt)
            if 'collectionMode' in query.keys():
                tags.append(query["collectionMode"])
            if 'license' in query.keys():
                tags.append(query["licence"])
            if 'domain' in query.keys():
                tags.append(query["domain"])
            if 'datasetId' in query.keys():
                tags.append(query["datasetId"])
            if tags:
                db_query["tags"] = tags
            if src_lang:
                db_query["sourceLanguage"] = src_lang
            if tgt_lang:
                db_query["targetLanguage"] = tgt_lang
            if 'groupBy' in query.keys():
                db_query["groupBy"] = True
                if 'countOfTranslations' in query.keys():
                    db_query["countOfTranslations"] = query["countOfTranslations"]
            exclude = {"_id": False}
            data = repo.search(db_query, exclude, offset, limit)
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