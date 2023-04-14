from flask import Blueprint
from flask_restful import Api
from resources import CreateUsers, UpdateUsers, SearchUsers, OnboardUsers, ActivateDeactivateUser, VerifyUser, Health, GetApiKey, RevokeApiKey, GenerateApiKey, ToggleDataTracking, GenerateServiceProviderKey, RemoveServiceProviderKey


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
    GetApiKey, "/v1/users/getApiKeys"
)

Api(USER_MANAGEMENT_BLUEPRINT).add_resource(
    RevokeApiKey, "/v1/users/revokeApiKey"
)

Api(USER_MANAGEMENT_BLUEPRINT).add_resource(
    GenerateApiKey, "/v1/users/generateApiKey"
)

Api(USER_MANAGEMENT_BLUEPRINT).add_resource(
    ToggleDataTracking, "/v1/users/dataTrackingToggle"
)

Api(USER_MANAGEMENT_BLUEPRINT).add_resource(
    GenerateServiceProviderKey, "/v1/users/generateServiceProviderKey"
)

Api(USER_MANAGEMENT_BLUEPRINT).add_resource(
    RemoveServiceProviderKey, "/v1/users/removeServiceProviderKey"
)