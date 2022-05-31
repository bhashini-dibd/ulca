from models.abstract_handler import BaseValidator
from configs.configs import dataset_type_parallel, dataset_type_asr, dataset_type_ocr, dataset_type_monolingual, dataset_type_asr_unlabeled
import logging
from logging.config import dictConfig
log = logging.getLogger('file')

class BasicSchemaCheck(BaseValidator):
    """
    Verifies the the schema for record
    """

    def execute(self, request):
        # check for mandatory keys in record
        log.info('----Executing the basic schema check----')
        try:
            required_keys = set()
            if request["datasetType"] == dataset_type_parallel:
                required_keys = {'sourceText', 'targetText', 'sourceLanguage', 'targetLanguage'}
            if request["datasetType"] == dataset_type_asr:
                required_keys = {'text', 'sourceLanguage'}
            if request["datasetType"] == dataset_type_ocr:
                required_keys = {'groundTruth', 'sourceLanguage'}
            if request["datasetType"] == dataset_type_monolingual:
                required_keys = {'text', 'sourceLanguage'}
            if request["datasetType"] == dataset_type_asr_unlabeled:
                required_keys = {'fileLocation'}

            if required_keys <= request["record"].keys():
                return super().execute(request)
            else:
                return {"message": "Mandatory keys missing", "code": "KEYS_MISSING", "status": "FAILED"}
        except Exception as e:
            log.exception(f"Exception while executing basic schema check: {str(e)}")
            return {"message": "Exception while executing basic schema check", "code": "SERVER_PROCESSING_ERROR", "status": "FAILED"}


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