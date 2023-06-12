from models.abstract_handler import BaseValidator
from configs.configs import dataset_type_parallel, dataset_type_asr, dataset_type_ocr, dataset_type_monolingual, dataset_type_tts, dataset_type_transliteration, dataset_type_glossary
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
            if request["datasetType"] in [dataset_type_parallel, dataset_type_transliteration, dataset_type_glossary]:
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
            if request["datasetType"] == dataset_type_tts:
                text_list.append(record['text'])
                lang_list.append(record['sourceLanguage'])

            for text, lang in zip(text_list, lang_list):
                # Skipping for few languages as the current model doesnt support them
                if lang in ['brx', 'mni', 'sat', 'lus', 'njz', 'pnr', 'grt', 'sd','unknown','mixed', 'ks', 'gom']:
                    continue
                #workaround to fix en language check for glossary dataset
                if request["datasetType"] == dataset_type_glossary and lang == 'en':
                    en_chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
                    temp = list(filter(lambda x: x in en_chars, text))
                    text_modified = "".join(temp)
                    if len(text_modified) < len(text)/3:
                        return {"message": "Sentence does not match the specified language", "code": "LANGUAGE_MISMATCH", "status": "FAILED"}
                    else:
                        continue

                try:
                    detector = Detector(text)
                    log.info(f"Text Language Detection: Text {text}")
                    # The language detection model does not support these languages,
                    # So check for closest possible language to rule out non-Indic languages
                    # TODO: Temporary implementation for now, need better solution
                    log.info(f"Detector Language Code: Text {detector.language.code}")
                    log.info(f"Detector Language Confidence: Text {detector.language.confidence}")
                    if lang in ['doi', 'kok', 'mai']:
                        if detector.language.code not in ['hi', 'mr', 'bh', 'ne', 'sa'] or detector.language.confidence<50:
                            return {"message": "Sentence does not match the specified language", "code": "LANGUAGE_MISMATCH", "status": "FAILED"}
                    else:
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