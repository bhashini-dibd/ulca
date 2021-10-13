from flask_restful import Resource
from flask import request
import logging
from utilities import post_error,CustomResponse,Status
from services import MasterDataServices

log = logging.getLogger('file')

mdserve = MasterDataServices()

class MasterDataResource(Resource):

    # reading json request and reurnung final response
    def post(self):
        body = request.get_json()
        log.info(f"Request for master data received")
        if body.get("masterName") == None :
            log.error("Data Missing-masterName and locale are mandatory")
            return post_error("Request Failed","Data Missing-masterName and locale are mandatory"), 400
        attribute    =  body["masterName"]
        jsonpath     =  None
        if "jsonPath" in body:
            jsonpath     =   body["jsonPath"]  
        try:
            result = mdserve.get_attributes_data(attribute,jsonpath)
            if "errorID" not in result:
                log.info(f"Request to mdms fetch succesfull ")
                return CustomResponse(Status.SUCCESS.value,result).getresjson(), 200
            return result, 400
        except Exception as e:
            log.error(f"Request to mdms failed due to  {e}")
            return post_error("Service Exception",f"Exception occurred:{e}"), 400

