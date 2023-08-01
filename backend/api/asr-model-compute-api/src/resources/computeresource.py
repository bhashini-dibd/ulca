from flask_restful import Resource
from flask import request
from models.response import CustomResponse, post_error
from models.status import Status
import config
import logging
from repositories import ASRComputeRepo
from utils.mongo_utils import ASRMongodbComputeRepo
from bson.objectid import ObjectId

from logging.config import dictConfig
log = logging.getLogger('file')

asrrepo = ASRComputeRepo()
asrmongorepo = ASRMongodbComputeRepo()

# class to navigate audio requests in the form of urls and encoded asr 
class ASRComputeResource(Resource):
    # reading json request and reurnung final response
    def post(self):
        log.info("Request received for asr computing")
        body    =   request.get_json()
        userId  =   body["userId"]
        task    =   body["task"]
        lang    =   body["source"]
        inf = asrmongorepo.find_doc(body["modelId"])
        inf_callbackurl = inf[0]["inferenceEndPoint"]
        uri         =   False
        if "audioContent" in body:
            audio   =   body["audioContent"]
        if "audioUri" in body:
            audio   =   body["audioUri"]
            uri     =   True

        try:
            result = asrrepo.process_asr(lang,audio,userId,inf_callbackurl,uri)
            if "output" in result.keys():
                if len(result['output']) != 0:
                    res = CustomResponse(Status.SUCCESS.value,result["output"][0],None)
                    log.info(f"response successfully generated. res ==> {res}")
                    return res.getres()
                elif "status" in result.keys() and result["status"] == "ERROR":

                    return post_error("Request Failed",result['statusText']), 400
        except Exception as e:
            log.info(f'Exception on ASRComputeResource {e}')

# class to navigate asr file requests
class ComputeAudioResource(Resource):
    # reading json request and reurnung final response
    def post(self):
        log.info("Request received for asr computing")
        body                =   request.get_json()
        audio_file_path     =   body["filePath"]
        lang                =   body["sourceLanguage"]
        callback_url        =   body["callbackUrl"]
        transformat         =   "transcript"
        audioformat         =   "wav"
        try:
            result = asrrepo.process_asr_from_audio_file(lang,audio_file_path,callback_url,transformat,audioformat)
            if result.get("status") == "SUCCESS":
                res = CustomResponse(Status.SUCCESS.value,result["output"][0],None)              
            else:
                res = CustomResponse(Status.SUCCESS.value,None,None)
            log.info("response successfully generated.")
            return res.getres()
        except Exception as e:
            log.info(f'Exception on ComputeAudioResource {e}')




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