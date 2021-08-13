from models.abstract_handler import BaseValidator
from configs.configs import dataset_type_parallel, dataset_type_asr, dataset_type_ocr, dataset_type_monolingual, validate_text_length_threshold
import logging
from logging.config import dictConfig
log = logging.getLogger('file')

class SentenceLengthCheck(BaseValidator):
    """
    Verifies the threshold for minimum number of words to be present in a sentence
    """

    def execute(self, request):
        log.info('----Executing the sentence length check----')
        try:
            text_list = []
            record = request["record"]
            if request["datasetType"] == dataset_type_parallel:
                text_list.append(record['sourceText'])
                text_list.append(record['targetText'])
            if request["datasetType"] == dataset_type_asr:
                text_list.append(record['text'])
            if request["datasetType"] == dataset_type_ocr:
                text_list.append(record['groundTruth'])
            if request["datasetType"] == dataset_type_monolingual:
                text_list.append(record['text'])

            for text in text_list:
                words = len(list(text.split()))
                if words < validate_text_length_threshold:
                    return {"message": "Sentence Length too short", "code": "TEXT_LENGTH_TOO_SHORT", "status": "FAILED"}

            log.info('----sentence length check  -> Passed----')
            return super().execute(request)
        except Exception as e:
            log.exception(f"Exception while executing sentence length check: {str(e)}")
            return {"message": "Exception while executing sentence length check", "code": "SERVER_PROCESSING_ERROR", "status": "FAILED"}

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