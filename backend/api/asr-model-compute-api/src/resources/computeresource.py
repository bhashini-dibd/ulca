from flask_restful import Resource
from flask import request
from models.response import CustomResponse
from models.status import Status
import config
import logging
from repositories import ASRComputeRepo
from logging.config import dictConfig
log = logging.getLogger('file')

asrrepo = ASRComputeRepo()
# rest request for block merging individual service
class ASRComputeResource(Resource):

    # reading json request and reurnung final response
    def post(self):
        log.info("Request received for asr computing")
        body    =   request.get_json()
        # modelId =   body["modelId"]
        userId  =   body["userId"]
        task    =   body["task"]
        lang    =   body["source"]
        inference   =   body["inferenceEndPoint"]
        if "audioContent" in body:
            audio = body["audioContent"]
        if "audioUri" in body:
            audio = body["audioUri"]
        try:
            result = asrrepo.process_asr(lang,audio,userId,inference)
            res = CustomResponse(Status.SUCCESS.value,result,None)
            log.info("response successfully generated.")
            return res.getres()
        except Exception as e:
            log.info(f'Exception on ASRComputeResource {e}')




dictConfig({
    'version': 1,
    'formatters': {'default': {
        'format': '[%(asctime)s] {%(filename)s:%(lineno)d} %(threadName)s %(levelname)s in %(module)s: %(message)s',
    }},
    'handlers': {
        'info': {
            'class': 'logging.FileHandler',
            'level': 'DEBUG',
            'formatter': 'default',
            'filename': 'info.log'
        },
        'console': {
            'class': 'logging.StreamHandler',
            'level': 'DEBUG',
            'formatter': 'default',
            'stream': 'ext://sys.stdout',
        }
    },
    'loggers': {
        'file': {
            'level': 'DEBUG',
            'handlers': ['info', 'console'],
            'propagate': ''
        }
    },
    'root': {
        'level': 'DEBUG',
        'handlers': ['info', 'console']
    }
})