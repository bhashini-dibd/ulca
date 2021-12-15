from flask import Blueprint
from flask_restful import Api

from src.resources.dataset import DatasetAggregateResource

CORPUS_BLUEPRINT = Blueprint("corpus", __name__)

#end point for chart data
Api(CORPUS_BLUEPRINT).add_resource(
    DatasetAggregateResource, "/v0/store/search"
)

