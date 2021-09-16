from flask import Blueprint
from flask_restful import Api
from resources import NotifierResource, MismatchNotifierResource

# end-point for independent service
NOTIFIER_BLUEPRINT = Blueprint("notifier-service", __name__)
api = Api(NOTIFIER_BLUEPRINT)

api.add_resource(NotifierResource, "/v1/send/mail")

api.add_resource(MismatchNotifierResource, "/v1/notify/mismatch")
