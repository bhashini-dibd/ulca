from flask import Blueprint
from flask_restful import Api
from resources import NotifierResource,Health

NOTIFIER_BLUEPRINT = Blueprint("notifier", __name__)

#Error report end-point
Api(NOTIFIER_BLUEPRINT).add_resource(NotifierResource,"/v0/notify/user")

Api(NOTIFIER_BLUEPRINT).add_resource(Health, "/health")