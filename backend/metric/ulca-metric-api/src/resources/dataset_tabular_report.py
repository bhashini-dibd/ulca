from flask_restful import  Resource
from flask import request
from src.models.api_response import APIResponse, post_error
from src.models.api_enums import APIStatus
from src.repositories import GetTabularData
import logging

log = logging.getLogger('file')
tabularDataRepo = GetTabularData()

#Receiving json request and returning result
class DatasetTabularResource(Resource):
    def post(self):
        #body = request.get_json()
        log.info("Tabular report request received for datasets")
        try:
            tabular_report = tabularDataRepo.aggregate()
        except Exception as e:
            log.exception("Exception at DatasetTabularResource:{}".format(str(e)))
            return post_error("Data Missing","Mandatory key checks failed",None), 400
        res = APIResponse(APIStatus.SUCCESS.value, tabular_report,None)
        return res.getresjson(), 200

