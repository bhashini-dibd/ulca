from models.abstract_handler import BaseValidator
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
            src_txt = request['record']['sourceText']
            tgt_txt = request['record']['targetText']

            request['record']['sourceText'] = self.remove_special(src_txt)
            request['record']['targetText'] = self.remove_special(tgt_txt)

            return super().execute(request)
        except Exception as e:
            log.exception('Exception while removing special characters', e)


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