from flask_restful import Resource
from flask import request
from flask import send_file
from models.response import CustomResponse, post_error
from models.status import Status
import config
import logging
from mongo_util import MongoUtil

from logging.config import dictConfig

log = logging.getLogger('file')
mongo_repo = MongoUtil()

class ASRStoreBenchmark(Resource):

    def post(self):
        log.info("Request received for Audio Storage")
        body = request.get_json()
        audio_list = body["audioList"]
        if isinstance(audio_list, dict):
            audio_list = [audio_list]
        benchmark_count = {"datasetsCreated": 0}
        for audio_data in audio_list:
            if audio_data["store"]:
                condition = {"audioId": audio_data["audioId"]}
                update_rec = {"$set":{"inference": audio_data["inference"]}}
                mongo_repo.update(condition, update_rec, False)
                benchmark_count["datasetsCreated"] = benchmark_count["datasetsCreated"] + 1
            else:
                mongo_repo.delete(audio_data["audioId"])

        res = CustomResponse(Status.SUCCESS.value,benchmark_count,None)
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



