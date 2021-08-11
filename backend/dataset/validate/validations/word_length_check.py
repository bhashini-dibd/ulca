from models.abstract_handler import BaseValidator
from configs.configs import dataset_type_parallel, dataset_type_asr, dataset_type_ocr, dataset_type_monolingual
import logging
from logging.config import dictConfig
log = logging.getLogger('file')

class WordLengthCheck(BaseValidator):
    """
    Verifies all words to have minimum number of characters
    """

    def execute(self, request):
        log.info('----Executing the word length check----')
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
                words = list(text.split())
                word_sum = 0
                for word in words:
                    word_sum = word_sum + len(word)
                if word_sum/len(words) < 3:
                    return {"message": "Average Word length too short", "code": "WORD_LENGTH_TOO_SHORT", "status": "FAILED"}

            log.info('----word length check  -> Passed----')
            return super().execute(request)
        except Exception as e:
            log.exception(f"Exception while executing word length check: {str(e)}")
            return {"message": "Exception while executing word length check", "code": "SERVER_PROCESSING_ERROR", "status": "FAILED"}


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