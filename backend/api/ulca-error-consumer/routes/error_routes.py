from flask import Blueprint
from flask_restful import Api
from resources import FetchErrorReport,Health

ERROR_CONSUMER_BLUEPRINT = Blueprint("error-consumer", __name__)

#Error report end-point
Api(ERROR_CONSUMER_BLUEPRINT).add_resource(FetchErrorReport,"/v0/error/report")

Api(ERROR_CONSUMER_BLUEPRINT).add_resource(Health, "/health")