from flask_restful import fields, marshal_with, reqparse, Resource
from flask import request
from src.utilities.app_context import LOG_WITHOUT_CONTEXT
from src.models.application.dataset import ParallelDataset as AppParallelDataset
from src.models.application.summarize_dataset import SummarizeDataset as AppSummarizeDataset
from src.models.api_response import APIResponse
from src.models.api_status import APIStatus
from src.repositories import DatasetRepo, SummarizeDatasetRepo
import logging

log = logging.getLogger('file')
datasetRepo = DatasetRepo()
summarizeDatasetRepo = SummarizeDatasetRepo()


class ParallelCorpusCreateResource(Resource):
    def post(self):
        body = request.get_json()
        appParallelCorpusObj = AppParallelDataset()
        appSummarizeDataset = AppSummarizeDataset()
        status, result = appParallelCorpusObj.get_validated_dataset(body)
        if not status:
            log_info('Missing params in ParallelCorpusCreateResource {} missing: {}'.format(body, key),
                     LOG_WITHOUT_CONTEXT)
            res = APIResponse(APIStatus.ERR_GLOBAL_MISSING_PARAMETERS.value, None)
            return res.getresjson(), 400
        search_result = {}
        try:
            search_result = datasetRepo.store_parallel_corpus_dataset(result)
            tags = appParallelCorpusObj.get_tags(body)
            log_info('dataset tags {}'.format(tags), LOG_WITHOUT_CONTEXT)
            summarize_result = appSummarizeDataset.create(search_result, tags)
            summarizeDatasetRepo.store_summarize_dataset(summarize_result)
        except Exception as e:
            log_exception("Exception at ParallelCorpusCreateResource ", LOG_WITHOUT_CONTEXT, e)
            res = APIResponse(APIStatus.ERR_GLOBAL_MISSING_PARAMETERS.value, None)
            return res.getresjson(), 400

        res = APIResponse(APIStatus.SUCCESS.value, search_result)
        return res.getres()


class DatasetSearchResource(Resource):
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
