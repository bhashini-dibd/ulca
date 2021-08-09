from collections import Counter
from flask_restful import  Resource
from flask import request
from src.models.api_response import APIResponse, post_error
from src.models.api_enums import APIStatus
from src.repositories import SummarizeDatasetRepo
import logging

log = logging.getLogger('file')
summarizeDatasetRepo = SummarizeDatasetRepo()


class DatasetSearchResource(Resource):
    def get(self):
        search_result = summarizeDatasetRepo.search()
        res = APIResponse(APIStatus.SUCCESS.value, search_result)
        return res.getresjson(), 200

class DatasetAggregateResource(Resource):
    def post(self):
        body = request.get_json()
        log.info("Metric request received for datasets")
        try:
            search_result, count = summarizeDatasetRepo.aggregate(body)
        except Exception as e:
            log.exception("Exception at DatasetAggregateResource:{}".format(str(e)))
            return post_error("Data Missing","Mandatory key checks failed",None), 400
        res = APIResponse(APIStatus.SUCCESS.value, search_result,count)
        return res.getresjson(), 200

class ModelAggregateResource(Resource):
    def post(self):
        body = request.get_json()
        log.info("Metric request received for models")
        try:
            search_result,count = summarizeDatasetRepo.aggregate_models(body)
        except Exception as e:
            log.exception("Exception at DatasetAggregateResource:{}".format(str(e)))
            return post_error("Data Missing","Mandatory key checks failed",None), 400
        res = APIResponse(APIStatus.SUCCESS.value, search_result,count)
        return res.getresjson(), 200
