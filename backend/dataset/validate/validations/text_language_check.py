from models.abstract_handler import BaseValidator

class TextLanguageCheck(BaseValidator):
    """
    Verifies the characters to match the specified source or target language
    """

    def execute(self, request):
        src_txt = request['src_text']
        target_txt = request['target_text']
        src_lang, target_lang = request['src_lang'], request['target_lang']