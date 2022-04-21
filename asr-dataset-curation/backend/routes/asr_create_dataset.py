from flask import Blueprint
from flask_restful import Api
from resources import ASRBreakdownAudio, ASRStoreBenchmark

# end-point to breakdown audio
ASR_CREATE_BENCHMARK_DATASET_BLUEPRINT = Blueprint("asr-create-benchmark-dataset", __name__)
api = Api(ASR_CREATE_BENCHMARK_DATASET_BLUEPRINT)
api.add_resource(ASRBreakdownAudio, "/v1/audio/breakdown")
api.add_resource(ASRStoreBenchmark, "/v1/audio/submit")
