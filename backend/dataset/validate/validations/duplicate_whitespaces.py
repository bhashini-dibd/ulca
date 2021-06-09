from models.abstract_handler import BaseValidator

class DuplicateWhitespaces(BaseValidator):
    """
    Removes all additional whitespaces
    """

    def execute(self, request):

        request['record']['sourceText'] = " ".join(request['record']['sourceText'].split())
        request['record']['targetText'] = " ".join(request['record']['targetText'].split())

        return super().execute(request)