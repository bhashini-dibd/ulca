from models.abstract_handler import BaseValidator
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
            record = request["record"]
            src_txt = record['sourceText']
            tgt_txt = record['targetText']
            words_src = len(list(src_txt.split(" ")))
            words_tgt = len(list(tgt_txt.split(" ")))
            if words_src < 4 or words_tgt < 4:
                return {"message": "Sentence Length too short", "code": "TEXT_LENGTH_TOO_SHORT", "status": "FAILED"}
            else:
                log.info('----sentence length check  -> Passed----')
                return super().execute(request)
        except Exception as e:
            log.exception('Exception while executing sentence length check', e)

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