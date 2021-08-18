from models.abstract_handler import BaseValidator
from configs.configs import dataset_type_parallel, dataset_type_asr, dataset_type_ocr, dataset_type_monolingual
import logging
from logging.config import dictConfig
log = logging.getLogger('file')

class SpecialCharacterCheck(BaseValidator):
    """
    Removes special characters from beginning
    """
    # TO-DO : Check and add punctuation at the end(which punctuation to add ?)

    def remove_special(self, txt):
        if txt[0].isalnum():
            return txt

        return self.remove_special(txt[1:])

    def execute(self, request):
        log.info('----Removing special characters from beginning----')
        try:
            if request["datasetType"] == dataset_type_parallel:
                request['record']['sourceText'] = self.remove_special(request['record']['sourceText'])
                request['record']['targetText'] = self.remove_special(request['record']['targetText'])
            if request["datasetType"] == dataset_type_asr:
                request['record']['text'] = self.remove_special(request['record']['text'])
            if request["datasetType"] == dataset_type_ocr:
                request['record']['groundTruth'] = self.remove_special(request['record']['groundTruth'])
            if request["datasetType"] == dataset_type_monolingual:
                request['record']['text'] = self.remove_special(request['record']['text'])

            return super().execute(request)
        except Exception as e:
            log.exception(f"Exception while removing special characters: {str(e)}")
            return {"message": "Exception while removing special characters", "code": "SERVER_PROCESSING_ERROR", "status": "FAILED"}


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