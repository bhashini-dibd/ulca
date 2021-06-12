from models.abstract_handler import BaseValidator
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
            request['record']['sourceText'] = " ".join(request['record']['sourceText'].split())
            request['record']['targetText'] = " ".join(request['record']['targetText'].split())

            return super().execute(request)
        except Exception as e:
            log.exception('Exception while removing extra whitespaces', e)

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