from models.abstract_handler import BaseValidator
import hashlib
import logging
from logging.config import dictConfig
log = logging.getLogger('file')

class CaseDedup(BaseValidator):
    """
    Stores the hash for sentence with all lower case to facilitate deduplication based on case
    """

    def execute(self, request):
        log.info('----Adding hash for sentences----')
        try:
            src_txt = request['record']['sourceText']
            tgt_txt = request['record']['targetText']
            if request['record']['sourceLanguage'] == 'en':
                src_txt = src_txt.lower()
            if request['record']['targetLanguage'] == 'en':
                tgt_txt = tgt_txt.lower()
            src_hash = str(hashlib.sha256(src_txt.encode('utf-16')).hexdigest())
            tgt_hash = str(hashlib.sha256(tgt_txt.encode('utf-16')).hexdigest())
            request['record']['sourceTextHash'] = src_hash
            request['record']['targetTextHash'] = tgt_hash

            return super().execute(request)
        except Exception as e:
            log.exception('Exception while adding hash values for sentences', e)


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