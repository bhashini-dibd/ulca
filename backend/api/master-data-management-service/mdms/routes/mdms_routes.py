from flask import Blueprint
from flask_restful import Api
from resources import MasterDataResource, BulkMasterDataResource, CacheBustResource

# end-point for independent service
MDMS_BLUEPRINT = Blueprint("master-data-mgmt", __name__)

#fetch master data end-point
Api(MDMS_BLUEPRINT).add_resource(MasterDataResource, "/v0/fetch-master")

Api(MDMS_BLUEPRINT).add_resource(BulkMasterDataResource, "/v0/fetch-master/bulk")

Api(MDMS_BLUEPRINT).add_resource(CacheBustResource, "/v0/bust-cache")

