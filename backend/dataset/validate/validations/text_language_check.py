from models.abstract_handler import BaseValidator
from langdetect import detect_langs

class TextLanguageCheck(BaseValidator):
    """
    Verifies the characters to match the specified source or target language
    """

    def execute(self, request):
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

        return super().execute(request)