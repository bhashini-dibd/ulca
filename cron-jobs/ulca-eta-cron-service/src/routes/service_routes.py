from flask import Blueprint
from flask_restful import Api
from resources import ETACalculatorResource

# end-point for independent service
CRON_BLUEPRINT = Blueprint("cron-manager-service", __name__)
api = Api(CRON_BLUEPRINT)
api.add_resource(ETACalculatorResource, "/v1/get/estimate")
