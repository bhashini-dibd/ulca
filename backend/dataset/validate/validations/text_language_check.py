from models.abstract_handler import BaseValidator
from langdetect import detect_langs
import logging
from logging.config import dictConfig
log = logging.getLogger('file')

class TextLanguageCheck(BaseValidator):
    """
    Verifies the characters to match the specified source or target language
    """

    def execute(self, request):
        log.info('----Executing the text language check----')
        try:
            record = request["record"]
            src_txt = record['sourceText']
            tgt_txt = record['targetText']

            src_lang, target_lang = record['sourceLanguage'], record['targetLanguage']

            res = detect_langs(src_txt)
            detected_lang = str(res[0]).split(':')[0]
            prob = str(res[0]).split(':')[1]
            if detected_lang != src_lang or float(prob) < 0.8:
                return {"message": "Source sentence does not match the specified language", "code": "LANGUAGE_MISMATCH", "status": "FAILED"}

            res = detect_langs(tgt_txt)
            detected_lang = str(res[0]).split(':')[0]
            prob = str(res[0]).split(':')[1]
            if detected_lang != target_lang or float(prob) < 0.8:
                return {"message": "Target sentence does not match the specified language", "code": "LANGUAGE_MISMATCH", "status": "FAILED"}

            log.info('----text language check  -> Passed----')
            return super().execute(request)
        except Exception as e:
            log.exception('Exception while executing text language check', e)


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