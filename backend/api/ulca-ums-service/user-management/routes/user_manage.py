from flask import Blueprint
from flask_restful import Api
from resources import CreateUsers, UpdateUsers, SearchUsers, OnboardUsers, ActivateDeactivateUser, VerifyUser, Health, getApiKey


USER_MANAGEMENT_BLUEPRINT = Blueprint("user-management-crud", __name__)

Api(USER_MANAGEMENT_BLUEPRINT).add_resource(
    CreateUsers, "/v1/users/signup"
)

Api(USER_MANAGEMENT_BLUEPRINT).add_resource(
    VerifyUser,"/v1/users/verify-user"
)

Api(USER_MANAGEMENT_BLUEPRINT).add_resource(
    UpdateUsers, "/v1/users/update"
)

Api(USER_MANAGEMENT_BLUEPRINT).add_resource(
    SearchUsers, "/v1/users/search"
)

Api(USER_MANAGEMENT_BLUEPRINT).add_resource(
    OnboardUsers,"/v1/users/onboard-users"
)

Api(USER_MANAGEMENT_BLUEPRINT).add_resource(
    ActivateDeactivateUser,"/v1/users/update/active/status"
)

Api(USER_MANAGEMENT_BLUEPRINT).add_resource(
    Health, "/health"
)

Api(USER_MANAGEMENT_BLUEPRINT).add_resource(
    getApiKey, "/v1/users/getApiKey"
)


