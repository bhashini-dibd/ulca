from flask_restful import  Resource
from flask import request
from src.models.application.summarize_dataset import SummarizeDataset as AppSummarizeDataset
from src.models.api_response import APIResponse
from src.models.api_status import APIStatus
from src.repositories import SummarizeDatasetRepo
import logging

log = logging.getLogger('file')
summarizeDatasetRepo = SummarizeDatasetRepo()


class DatasetSearchResource(Resource):
<<<<<<< Updated upstream
    def post(self):
        
        body = request.get_json()
        appSummarizeDataset = AppSummarizeDataset()
        status, result = appSummarizeDataset.get_validated_data(body)
        if not status:
            log.info('Missing params in DatasetSearchResource {} missing: {}'.format(body, result))
            res = APIResponse(APIStatus.ERR_GLOBAL_MISSING_PARAMETERS.value, None)
            return res.getresjson(), 400
        search_result = []
        try:
            status, search_result = summarizeDatasetRepo.search(result)
        except Exception as e:
            log.exception("Exception at DatasetSearchResource:{}".format(str(e)))
            res = APIResponse(APIStatus.ERR_GLOBAL_MISSING_PARAMETERS.value, None)
            return res.getresjson(), 400
        res = APIResponse(APIStatus.SUCCESS.value, search_result)
        return res.getres()

class DatasetAggregateResource(Resource):
=======
>>>>>>> Stashed changes
    def post(self):
        body = request.get_json()
        # appSummarizeDataset = AppSummarizeDataset()
        # status, result = appSummarizeDataset.get_validated_data(body)
        # if not status:
        #     log.info('Missing params in DatasetSearchResource {} missing: {}'.format(body, result))
        #     res = APIResponse(APIStatus.ERR_GLOBAL_MISSING_PARAMETERS.value, None)
        #     return res.getresjson(), 400
        # search_result = []
        try:
            status, search_result = summarizeDatasetRepo.aggregate(body)
        except Exception as e:
            log.exception("Exception at DatasetSearchResource:{}".format(str(e)))
            res = APIResponse(APIStatus.ERR_GLOBAL_MISSING_PARAMETERS.value, None)
            return res.getresjson(), 400
        res = APIResponse(APIStatus.SUCCESS.value, search_result)
        return res.getres()