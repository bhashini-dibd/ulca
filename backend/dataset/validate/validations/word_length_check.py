from models.abstract_handler import BaseValidator
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
            record = request["record"]
            src_txt = record['sourceText']
            t_txt = record['targetText']
            words_src = list(src_txt.split())
            words_target = list(t_txt.split())
            sum_src = 0
            for word in words_src:
                sum_src = sum_src + len(word)

            if sum_src/len(words_src) < 3:
                return {"message": "Source sentence:Average Word length too short", "code": "WORD_LENGTH_TOO_SHORT", "status": "FAILED"}

            sum_t = 0
            for word in words_target:
                sum_t = sum_t + len(word)

            if sum_t/len(words_target) < 3:
                return {"message": "Target sentence:Average Word length too short", "code": "WORD_LENGTH_TOO_SHORT", "status": "FAILED"}

            log.info('----word length check  -> Passed----')
            return super().execute(request)
        except Exception as e:
            log.exception('Exception while executing word length check', e)


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