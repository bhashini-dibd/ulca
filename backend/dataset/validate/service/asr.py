import logging
from datetime import datetime
from logging.config import dictConfig

from models.validation_pipeline import ValidationPipeline

log = logging.getLogger('file')

class ASRValidate:
    def __init__(self):
        pass

    def execute_validation_pipeline(self, record):
        try:
            v_pipeline = ValidationPipeline.getInstance()
            res = v_pipeline.runAsrValidators(record)
            if res == False:
                return {"message": "Validation failed", "status": "FAILED"}

            return {"message": "Validation successful", "status": "SUCCESS"}
        except Exception as e:
            return {"message": "EXCEPTION while validating dataset!!", "status": "FAILED"}



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