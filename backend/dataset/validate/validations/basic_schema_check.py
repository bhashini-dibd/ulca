from models.abstract_handler import BaseValidator
from configs.configs import dataset_type_parallel, dataset_type_asr, dataset_type_ocr, dataset_type_monolingual

class BasicSchemaCheck(BaseValidator):
    """
    Verifies the the schema for record
    """

    def execute(self, request):
        # check for mandatory keys in record
        required_keys = set()
        if request["datasetType"] == dataset_type_parallel:
            required_keys = {'sourceText', 'targetText', 'sourceLanguage', 'targetLanguage'}
        if request["datasetType"] == dataset_type_asr:
            required_keys = {'text', 'sourceLanguage', 'samplingRate', 'bitsPerSample'}
        if request["datasetType"] == dataset_type_ocr:
            required_keys = {'groundTruth', 'sourceLanguage'}

        if required_keys <= request["record"].keys():
            return super().execute(request)
        else:
            return {"message": "Mandatory keys missing", "code": "KEYS_MISSING", "status": "FAILED"}
