from models.abstract_handler import BaseValidator
from configs.configs import dataset_type_parallel, dataset_type_asr, dataset_type_ocr, dataset_type_monolingual
import logging
from logging.config import dictConfig
log = logging.getLogger('file')

class DuplicateWhitespaces(BaseValidator):
    """
    Removes all additional whitespaces
    """

    def execute(self, request):
        log.info('----Removing extra whitespaces----')
        try:
            if request["datasetType"] == dataset_type_parallel:
                request['record']['sourceText'] = " ".join(request['record']['sourceText'].split())
                request['record']['targetText'] = " ".join(request['record']['targetText'].split())

            if request["datasetType"] == dataset_type_asr:
                request['record']['text'] = " ".join(request['record']['text'].split())

            if request["datasetType"] == dataset_type_ocr:
                request['record']['groundTruth'] = " ".join(request['record']['groundTruth'].split())

            if request["datasetType"] == dataset_type_monolingual:
                request['record']['text'] = " ".join(request['record']['text'].split())

            return super().execute(request)
        except Exception as e:
            log.exception(f"Exception while removing extra whitespaces: {str(e)}")
            return {"message": "Exception while removing extra whitespaces", "code": "SERVER_PROCESSING_ERROR", "status": "FAILED"}

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