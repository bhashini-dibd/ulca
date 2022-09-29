from models.abstract_handler import BaseValidator
from configs.configs import dataset_type_parallel, dataset_type_asr, dataset_type_ocr, dataset_type_monolingual, dataset_type_tts, dataset_type_transliteration, dataset_type_glossary, validate_profanity_reference_en, validate_profanity_reference_hi

from fuzzywuzzy import fuzz
from fuzzywuzzy import process
from polyglot.text import Text
import urllib.request

import logging
from logging.config import dictConfig
log = logging.getLogger('file')

class ProfanityCheck(BaseValidator):
    """
    Verifies the sentence for profanity using reference words
    Currently works for English and Hindi only
    """

    def __init__(self):
        self.refs = []
        self.read_refs()

    def read_refs(self):
        ref_links = [validate_profanity_reference_en, validate_profanity_reference_hi]
        log.info('----Loading profanity references----')
        try:
            for link in ref_links:
                for line in urllib.request.urlopen(link):
                    self.refs.append(line.decode().strip())
        except Exception as e:
            log.exception(f"Exception while reading profanity reference data: {str(e)}")

    def transliterate_to_en(self, blob):
        log.info('----Transliterating to English----')
        txt = Text(blob)
        try:
            return " ".join([x for x in txt.transliterate("en")])
        except Exception as e:
            log.exception(f"Exception while transliterating to English: {str(e)}")
            return None

    def execute(self, request):
        log.info('----Executing the profanity check----')
        if not self.refs:
            log.error("Unable to fetch reference data , skipping profanity check")
            return super().execute(request)

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
                if lang in ['en', 'hi', 'transliterated-en']:
                    # Run the scorer for Hindi text as is and also run for transliterated to En
                    if lang == 'hi':
                        trnsltrtd_txt = self.transliterate_to_en(text)
                        if trnsltrtd_txt:
                            text_list.append(trnsltrtd_txt)
                            lang_list.append('transliterated-en')
                    try:
                        possible_match = process.extractOne(text, self.refs, scorer=fuzz.token_set_ratio)
                    except Exception as e:
                        log.exception(f"Exception while executing profanity model: {str(e)}")
                        return {"message": "Exception while executing profanity check", "code": "SERVER_PROCESSING_ERROR", "status": "FAILED"}

                    offensive_txt, similarity_perc = possible_match
                    if similarity_perc > 90:
                        error_msg = 'Offensive text found'
                        return {"message": error_msg, "code": "OFFENSIVE_TEXT_FOUND", "status": "FAILED"}

            log.info('---- Profanity check  -> Passed----')
            return super().execute(request)
        except Exception as e:
            log.exception(f"Exception while executing profanity check: {str(e)}")
            return {"message": "Exception while executing profanity check", "code": "SERVER_PROCESSING_ERROR", "status": "FAILED"}



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