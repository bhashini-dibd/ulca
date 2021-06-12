from models.abstract_handler import BaseValidator

class SentenceLengthCheck(BaseValidator):
    """
    Verifies the threshold for minimum number of words to be present in a sentence
    """

    def execute(self, request):
        record = request["record"]
        src_txt = record['sourceText']
        tgt_txt = record['targetText']
        words_src = len(list(src_txt.split(" ")))
        words_tgt = len(list(tgt_txt.split(" ")))
        if words_src < 4 or words_tgt < 4:
            return {"message": "Sentence Length too short", "code": "TEXT_LENGTH_TOO_SHORT", "status": "FAILED"}
        else:
            return super().execute(request)