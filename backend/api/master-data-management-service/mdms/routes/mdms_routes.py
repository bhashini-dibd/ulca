from flask import Blueprint
from flask_restful import Api
from resources import MasterDataResource

# end-point for independent service
MDMS_BLUEPRINT = Blueprint("master-data-mgmt", __name__)

#fetch master data end-point
Api(MDMS_BLUEPRINT).add_resource(MasterDataResource, "/v0/fetch/params")