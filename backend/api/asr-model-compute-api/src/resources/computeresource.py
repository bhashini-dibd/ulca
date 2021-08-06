from flask_restful import Resource
from flask import request
from models.response import CustomResponse
from models.status import Status
import config
import logging
from repositories import ASRComputeRepo

log = logging.getLogger('file')

# rest request for block merging individual service
class ASRComputeResource(Resource):

    # reading json request and reurnung final response
    def post(self):
        log.info("Request received for asr computing")
        body    =   request.get_json()
        modelId =   body["modelId"]
        userId  =   body["userId"]
        task    =   body["task"]
        lang    =   body["language"]
        if "audioContent" in body:
            audio = body["audioContent"]
        if "audioUri" in body:
            audio = ["audioUri"]
        
        try:
            result = ASRComputeRepo.process_asr(lang,audio,userId)
            res = CustomResponse(Status.SUCCESS.value,result)
            log.info("response successfully generated.")
            return res.getres()
        except Exception as e:
            log.info(f'Exception on ASRComputeResource {e}')