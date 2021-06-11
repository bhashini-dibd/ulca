from models.abstract_handler import BaseValidator

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
        src_txt = request['record']['sourceText']
        tgt_txt = request['record']['targetText']

        request['record']['sourceText'] = self.remove_special(src_txt)
        request['record']['targetText'] = self.remove_special(tgt_txt)

        return super().execute(request)