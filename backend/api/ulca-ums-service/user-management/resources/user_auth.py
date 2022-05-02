from flask_restful import Resource
from repositories import UserAuthenticationRepositories
from models import CustomResponse, Status, post_error
from utilities import UserUtils
from utilities import MODULE_CONTEXT
from flask import request
import logging

log = logging.getLogger('file')
authRepo = UserAuthenticationRepositories()

class UserLogin(Resource):

    def post(self):
        body = request.get_json()
        if "authenticator" not in body or not body["authenticator"]:
            return post_error("Data Missing","authenticator is not defined in request",None), 400
        credentials = body["data"]
        if "email" not in credentials or not credentials["email"]:
            return post_error("Data Missing","email not found",None), 400
        if "password" not in credentials or not credentials["password"]:
            return post_error("Data Missing","password not found",None), 400
        
        email = credentials["email"]
        password = credentials["password"]
        log.info("Request for login from {}".format(email))

        validity=UserUtils.validate_user_login_input(email, password)
        if validity is not None:
            log.exception("Login credentials check failed for {}".format(email))
            return validity, 400
        log.info("Login credentials check passed for {}".format(email))
        try:
            result = authRepo.user_login(email, password)
            if "errorID" in result:
                log.exception("Login failed for {}".format(email))
                return result, 400
            log.info("Login successful for {}".format(email))
            res = CustomResponse(Status.SUCCESS_USR_LOGIN.value, result)
            return res.getresjson(), 200
        except Exception as e:
            log.exception("Exception while  user login | {}".format(str(e)))
            return post_error("Exception occurred", "Exception while performing user login", None), 400            


class UserLogout(Resource):

    def post(self):
        body = request.get_json()
        if "userName" not in body or not body["userName"]:
            return post_error("Data Missing","userName not found",None), 400
        user_name = body["userName"]

        log.info("Request for logout from {}".format(user_name),MODULE_CONTEXT)
        try:
            result = authRepo.user_logout(user_name)
            if result == False:
                log.info("Logout failed for {}".format(user_name),MODULE_CONTEXT)
                res = CustomResponse(
                    Status.FAILURE_USR_LOGOUT.value, None)
                return res.getresjson(), 400
            else:
                log.info("{} logged out successfully".format(user_name),MODULE_CONTEXT)
                res = CustomResponse(Status.SUCCESS_USR_LOGOUT.value, None)
            return res.getres()
        except Exception as e:
            log.exception("Exception while logout: " + str(e), MODULE_CONTEXT, e)
            return post_error("Exception occurred", "Exception while performing user logout", None), 400
            


class ApiKeySearch(Resource):

    def post(self):
        body = request.get_json()    
        if body.get("key") == None:
            return post_error("Data Missing","key not found",None), 400
        key = body["key"]
        try:
            result = authRepo.key_search(key)
            if "errorID" in result:
                log.exception("api-key search request failed")
                return result, 400
            else:
                log.info("api-key search request successsful")
                res = CustomResponse(Status.SUCCESS_USR_TOKEN.value, result)
            return res.getresjson(), 200
        except Exception as e:
            log.exception("Exception while api-key search | {}".format(str(e)))
            return post_error("Exception occurred", "Exception while api-key search", None), 400
            

class ForgotPassword(Resource):
        
    def post(self):
        body = request.get_json()
        if body.get("email") == None:
            return post_error("Data Missing","email not found",None), 400
        email=body["email"]

        log.info("Request received for reset password link from {}".format(email),MODULE_CONTEXT)
        validity = UserUtils.validate_username(email)
        if validity is not None:
            log.info("Username/email validation failed  for generating reset password notification for {}".format(email), MODULE_CONTEXT)
            return validity, 400
        log.info("Username/email is validated for generating reset password notification for {}".format(email), MODULE_CONTEXT)
        try:
            result = authRepo.forgot_password(email)
            if result == True:
                log.info("Successfully generated reset-password link for {}".format(email),MODULE_CONTEXT)
                res = CustomResponse(
                        Status.SUCCESS_FORGOT_PWD.value, None)
                return res.getresjson(), 200
            else:
                log.info("Failed to generate reset-password link for {}".format(email),MODULE_CONTEXT)
                return result, 400
        except Exception as e:
            log.exception("Exception while forgot password api call: " + str(e), MODULE_CONTEXT, e)
            return post_error("Exception occurred", "Exception while forgot password api call:{}".format(str(e)), None), 400
            

class ResetPassword(Resource):

    def post(self):
        
        body = request.get_json()
        if "email" not in body or not body["email"]:
            return post_error("Data Missing","email not found",None), 400
        if "password" not in body or not body["password"]:
            return post_error("Data Missing","password not found",None), 400
        user_id = None
        user_id=request.headers["x-user-id"]
        user_name = body["email"]
        password = body["password"]
        
        log.info("Request received for password resetting from {}; userID :{}".format(user_name,user_id),MODULE_CONTEXT)
        if not user_id:
            return post_error("userId missing","userId is mandatory",None), 400
        
        validity = UserUtils.validate_username(user_name)
        if validity is not None:
            log.info("Username/email validation failed on reset password for {}".format(user_name), MODULE_CONTEXT)
            return validity, 400
        log.info("Username/email is validated on reset password for {}".format(user_name), MODULE_CONTEXT)
        pwd_validity=UserUtils.validate_password(password)
        if pwd_validity is not None:
            log.info("Password check failed on reset password for {}".format(user_name), MODULE_CONTEXT)
            return pwd_validity, 400
        log.info("Password check passed on resetting password for {}".format(user_name), MODULE_CONTEXT)
            
        try:
            result = authRepo.reset_password(user_id,user_name,password)
            if result == True:
                log.info("Reset password successful for {}".format(user_name), MODULE_CONTEXT)
                res = CustomResponse(
                        Status.SUCCESS_RESET_PWD.value, None)
                return res.getresjson(), 200
            else:
                return result, 400
        except Exception as e:
            log.exception("Exception while forgot password api call: " + str(e), MODULE_CONTEXT, e)
            return post_error("Exception occurred", "Exception while reset password api call:{}".format(str(e)), None), 400


class VerifyUser(Resource):

    def post(self):
        body = request.get_json()
        if body.get("email") == None:
            return post_error("Data Missing","email not found",None), 400
        if body.get("userID") == None:
            return post_error("Data Missing","userID not found",None), 400
        user_email = body["email"]
        user_id = body["userID"]

        log.info("Request received for user verification of {}".format(user_email))
        try:
            result = authRepo.verify_user(user_email,user_id)
            if "errorID" in result:
                log.exception("User verification for {} failed".format(user_email))
                return result, 400
            else:
                log.info("User verification for {} successful".format(user_email))
                res = CustomResponse(
                        Status.SUCCESS_ACTIVATE_USR.value, result)
                return res.getresjson(), 200        
        except Exception as e:
            log.exception("Exception while user verification | {} ".format(str(e)))
            return post_error("Exception occurred", "Exception while verification user api call:{}".format(str(e)), None), 400


class ActivateDeactivateUser(Resource):

    def post(self):
        body = request.get_json()
        if "email" not in body or not body["email"]:
            return post_error("Data Missing","email not found",None), 400
        if "is_active" not in body:
            return post_error("Data Missing","is_active not found",None), 400
        user_email = body["email"]
        status= body["is_active"]
        from_id = None
        from_id=request.headers["x-user-id"]

        if not isinstance(status,bool):
            return post_error("Invalid format", "is_active status should be either true or false", None), 400
        log.info("Request received for updating activation status of {}".format(user_email))
        try:
            result = authRepo.activate_deactivate_user(user_email,status,from_id)
            if result is not None:
                log.info("Updation of activation status for {} failed".format(user_email))
                return result, 400
            else:
                log.info("Updation of activation status for {} successful".format(user_email))
                res = CustomResponse(Status.SUCCESS.value, None)
                return res.getresjson(), 200           
        except Exception as e:
            log.exception("Exception while activate/deactivate user api call: " + str(e))
            return post_error("Exception occurred", "Exception while deactivate user api call:{}".format(str(e)), None), 400


class VerifyToken(Resource):

    def post(self):
        body = request.get_json()    
        if body.get("token") == None:
            return post_error("Data Missing","token not found",None), 400
        key = body["token"]
        try:
            result = authRepo.token_search(key)
            if "errorID" in result:
                log.exception("token search for reset password failed")
                return result, 400
            if result["active"] == False:
                return post_error("Invalid Data","Link expired",None), 400
            else:
                log.info("token search for reset password  successsful")
                res = CustomResponse(Status.SUCCESS_USR_TOKEN.value, result)
            return res.getresjson(), 200
        except Exception as e:
            log.exception("Exception while token search for reset password | {}".format(str(e)))
            return post_error("Exception occurred", "Exception while token search for reset password : {}".format(str(e)), None), 400