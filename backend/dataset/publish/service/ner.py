import logging
import time
from logging.config import dictConfig




log = logging.getLogger('file')


class NERService:
    def __init__(self):
        pass

    '''
    Method to load NER dataset into the mongo db
    params: request (record to be inserted)
    '''

    def load_ner_dataset(self, request):
        try:
            metadata, record = request, request["record"]
            if record:
                result = self.get_enriched_data(record, metadata)
        except Exception as e:
            log.exception(e)
            return {"message": "EXCEPTION while loading Glossary dataset!!", "status": "FAILED"}
        return {"status": "SUCCESS", "total": 1, "inserts": count, "updates": updates, "invalid": error_list}


    def get_enriched_data(self, data, metadata):








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
