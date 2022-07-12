from flask import Blueprint
from flask_restful import Api

from src.resources.dataset import DatasetAggregateResource
from src.resources.dataset_tabular_report import DatasetTabularResource

CORPUS_BLUEPRINT = Blueprint("corpus", __name__)

#end point for chart data
Api(CORPUS_BLUEPRINT).add_resource(
    DatasetAggregateResource, "/v0/store/search"
)

#end point for tabular report data
Api(CORPUS_BLUEPRINT).add_resource(
    DatasetTabularResource, "/v0/store/reportdata"
)

