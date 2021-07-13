from flask import Blueprint
from flask_restful import Api

from src.resources.dataset import  DatasetSearchResource, DatasetAggregateResource

CORPUS_BLUEPRINT = Blueprint("corpus", __name__)


Api(CORPUS_BLUEPRINT).add_resource(
    DatasetSearchResource, "/v0/store/attributes"
)

Api(CORPUS_BLUEPRINT).add_resource(
    DatasetAggregateResource, "/v0/store/search"
)