from models.abstract_handler import BaseValidator
from configs.configs import dataset_type_parallel, dataset_type_asr, dataset_type_ocr, dataset_type_monolingual
#from langdetect import detect_langs
from polyglot.detect import Detector
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
            text_list = []
            lang_list = []
            record = request["record"]
            if request["datasetType"] == dataset_type_parallel:
                text_list.append(record['sourceText'])
                text_list.append(record['targetText'])
                lang_list.append(record['sourceLanguage'])
                lang_list.append(record['targetLanguage'])
            if request["datasetType"] == dataset_type_asr:
                text_list.append(record['text'])
                lang_list.append(record['sourceLanguage'])
            if request["datasetType"] == dataset_type_ocr:
                text_list.append(record['groundTruth'])
                lang_list.append(record['sourceLanguage'])
            if request["datasetType"] == dataset_type_monolingual:
                text_list.append(record['text'])
                lang_list.append(record['sourceLanguage'])

            for text, lang in zip(text_list, lang_list):
                # Skipping for Assamese(as) and Oriya(or) as the current model doesnt support them
                #if lang == 'as' or lang == 'or':
                 #   continue
                #res = detect_langs(text)
                try:
                    detector = Detector(text)
                # detected_lang = str(res[0]).split(':')[0]
                # prob = str(res[0]).split(':')[1]
                # if detected_lang != lang or float(prob) < 0.75:
                    if detector.language.code != lang or detector.language.confidence<50:
                        return {"message": "Sentence does not match the specified language", "code": "LANGUAGE_MISMATCH", "status": "FAILED"}
                except Exception as e:
                    return {"message": "Unable to detect language, text snippet too small", "code": "SERVER_PROCESSING_ERROR", "status": "FAILED"}

            log.info('----text language check  -> Passed----')
            return super().execute(request)
        except Exception as e:
            log.exception(f"Exception while executing text language check: {str(e)}")
            return {"message": "Exception while executing text language check", "code": "SERVER_PROCESSING_ERROR", "status": "FAILED"}


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