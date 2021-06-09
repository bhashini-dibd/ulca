from models.abstract_handler import BaseValidator
import hashlib

class CaseDedup(BaseValidator):
    """
    Stores the hash for sentence with all lower case to facilitate deduplication based on case
    """

    def execute(self, request):

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