from flask import Blueprint
from flask_restful import Api

from src.resources.dataset import ParallelCorpusCreateResource, DatasetSearchResource

CORPUS_BLUEPRINT = Blueprint("corpus", __name__)

Api(CORPUS_BLUEPRINT).add_resource(
    ParallelCorpusCreateResource, "/v0/dataset/parallel-corpus/submit"
)

Api(CORPUS_BLUEPRINT).add_resource(
    DatasetSearchResource, "/v0/store/search"
)