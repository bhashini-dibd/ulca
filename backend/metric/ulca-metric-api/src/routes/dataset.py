from flask import Blueprint
from flask_restful import Api

from src.resources.dataset import DatasetAggregateResource
from src.resources.dataset_tabular_report import DatasetTabularResource
from src.resources.ai4b_dataset import AI4BharatDatasetAggregateResource

CORPUS_BLUEPRINT = Blueprint("corpus", __name__)

#end point for chart data
Api(CORPUS_BLUEPRINT).add_resource(
    DatasetAggregateResource, "/v0/store/search"
)

#end point for tabular report data
Api(CORPUS_BLUEPRINT).add_resource(
    DatasetTabularResource, "/v0/store/reportdata"
)

#end point for AI4Bharat/Samanantar datasets

Api(CORPUS_BLUEPRINT).add_resource(
    AI4BharatDatasetAggregateResource,"/v0/store/ai4b"
)
