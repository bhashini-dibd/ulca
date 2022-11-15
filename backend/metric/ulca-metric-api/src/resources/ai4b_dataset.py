from flask_restful import  Resource
from flask import request
from src.models.api_response import APIResponse, post_error
from src.models.api_enums import APIStatus
from src.repositories import SummarizeAi4bDatasetRepo
import logging

log = logging.getLogger('file')

summarizeAi4bDatasetRepo = SummarizeAi4bDatasetRepo()


class AI4BharatDatasetAggregateResource(Resource):
    def post(self):
        body = request.get_json()
        log.info("Metric request received for AI4B datasets")
        try:
            search_result, count = summarizeAi4bDatasetRepo.ai4b_aggregate(body)
        except Exception as e:
            log.exception("Exception at DatasetAggregateResource:{}".format(str(e)))
            return post_error("Data Missing","Mandatory key checks failed",None), 400
        res = APIResponse(APIStatus.SUCCESS.value, search_result,count)
        return res.getresjson(), 200