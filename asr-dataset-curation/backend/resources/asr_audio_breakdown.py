from flask_restful import Resource
from flask import request
from flask import send_file
from models.response import CustomResponse, post_error
from models.status import Status
import config
import logging
from repositories import ASRAudioChunk
from logging.config import dictConfig
log = logging.getLogger('file')

chunk_audio_repo = ASRAudioChunk()

class ASRBreakdownAudio(Resource):

    def post(self):
        log.info("Request received for Audio Breakdown")
        body = request.get_json()
        audio_url = body["audioUrl"]

        result = chunk_audio_repo.create_audio_chunks(audio_url)
        
        if result:
            res = CustomResponse(Status.SUCCESS.value,result,None)
            log.info("response successfully generated.")
            return res.getres()
        
        #path_to_file = "/home/kafka/asr_dataset_creation_test/chunk_dir/audio_chunk_0.wav"
        #return send_file(path_to_file, mimetype="audio/wav", as_attachment=True, download_name="audio_chunk_0.wav")

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



