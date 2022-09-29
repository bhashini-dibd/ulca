from flask_restful import Resource
from flask import request
import logging
from utilities import post_error,CustomResponse,Status
from services import FileServices

log = logging.getLogger('file')

fileserve = FileServices()

class FileUploaderResource(Resource):

    # reading json request and reurnung final response
    def post(self):
        body = request.get_json()
        if body.get("fileName") == None or body.get("storageFolder") == None:
            post_error("Request Failed","Data Missing-fileName and storageFolder are mandatory")
        file_path   =   body["fileLocation"]
        folder      =   body["storageFolder"]
        file_name   =   body["fileName"]

        log.info(f"File upload request received for {file_path}")
        try:
            result = fileserve.upload_file(file_path,file_name,folder)
            if "errorID" not in result:
                log.info(f"File upload request succesfull for {file_path}")
                return CustomResponse(Status.SUCCESS.value,result).getresjson(), 200
            return result, 400
        except Exception as e:
            log.error(f"File upload request failed due to  {e}")
            post_error("Service Exception",f"Exception occurred:{e}"), 400

class FileDownloaderResource(Resource):

   # reading json request and reurnung final response
    def post(self):
        body = request.get_json()
        if body.get("fileName") == None:
            post_error("Request Failed","Data Missing-fileName is mandatory")

        file_name = body['fileName']
        log.info(f"File download request received for {file_name}")
        try:
            result = fileserve.download_file(file_name)
            if "errorID" not in result:
                log.info(f"File download request succesfull for {file_name}")
                return CustomResponse(Status.SUCCESS.value,None).getresjson(), 200
            return result, 400
        except Exception as e:
            log.error(f"File download request failed due to  {e}")
            post_error("Service Exception",f"Exception occurred:{e}"), 400

class FileRemoverResource(Resource):

   # reading json request and reurnung final response
    def post(self):
        body = request.get_json()
        if body.get("fileName") == None:
            post_error("Request Failed","Data Missing-fileName is mandatory")

        file_name = body["fileName"]
        log.info(f"File delete request received for {file_name}")
        try:
            result = fileserve.remove_file(file_name)
            if "errorID" not in result:
                log.info(f"File delete request successfull for {file_name}")
                return CustomResponse(Status.SUCCESS.value,None).getresjson(), 200
            return result, 400
        except Exception as e:
            log.error(f"File delete request failed due to  {e}")
            post_error("Service Exception",f"Exception occurred:{e}"), 400
        
