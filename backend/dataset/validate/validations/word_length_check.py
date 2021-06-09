from models.abstract_handler import BaseValidator

class WordLengthCheck(BaseValidator):
    """
    Verifies all words to have minimum number of characters
    """

    def execute(self, request):
        record = request["record"]
        src_txt = record['sourceText']
        t_txt = record['targetText']
        words_src = list(src_txt.split())
        words_target = list(t_txt.split())
        sum_src = 0
        for word in words_src:
            sum_src = sum_src + len(word)

        if sum_src/len(words_src) < 3:
            return {"message": "Source sentence:Average Word length too short", "status": "FAILED"}

        sum_t = 0
        for word in words_target:
            sum_t = sum_t + len(word)

        if sum_t/len(words_target) < 3:
            return {"message": "Target sentence:Average Word length too short", "status": "FAILED"}

        return super().execute(request)