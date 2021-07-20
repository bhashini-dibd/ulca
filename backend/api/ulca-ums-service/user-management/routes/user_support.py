from flask import Blueprint
from flask_restful import Api
from resources import UserLogin, UserLogout, ApiKeySearch, ForgotPassword, ResetPassword , SearchRoles, VerifyToken


USER_SUPPORT_BLUEPRINT = Blueprint("user-management-support", __name__)

Api(USER_SUPPORT_BLUEPRINT).add_resource(
    UserLogin, "/v1/users/login"
)

Api(USER_SUPPORT_BLUEPRINT).add_resource(
    UserLogout, "/v1/users/logout"
)

Api(USER_SUPPORT_BLUEPRINT).add_resource(
    ApiKeySearch, "/v1/users/api-key-search"
)

Api(USER_SUPPORT_BLUEPRINT).add_resource(
    ForgotPassword, "/v1/users/forgot-password"
)

Api(USER_SUPPORT_BLUEPRINT).add_resource(
    ResetPassword, "/v1/users/reset-password"
)

Api(USER_SUPPORT_BLUEPRINT).add_resource(
    SearchRoles,"/v1/users/get-roles"
)

Api(USER_SUPPORT_BLUEPRINT).add_resource(
    VerifyToken,"/v1/users/get/token/status"
)