from models.abstract_handler import BaseValidator

class BasicSchemaCheck(BaseValidator):
    """
    Verifies the the schema for record
    """

    def execute(self, request):
        # check for mandatory keys in record

        required_keys = {'sourceText', 'targetText', 'sourceLanguage', 'targetLanguage'}
        if required_keys <= request["record"].keys():
            return super().execute(request)
        else:
            return {"message": "Mandatory keys missing", "status": "FAILED"}
