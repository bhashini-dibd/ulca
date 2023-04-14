from flask_restful import Resource
from repositories import UserManagementRepositories
from models import CustomResponse, Status, post_error
from utilities import UserUtils
from flask import request, jsonify
import config
import logging
from config import MAX_API_KEY, SECRET_KEY

log         =   logging.getLogger('file')
userRepo    =   UserManagementRepositories()

class CreateUsers(Resource):

    def post(self):
        body = request.get_json()
        if 'users' not in body or not body['users']:
            return post_error("Data Missing", "users not found", None), 400

        users = body['users']
        log.info("Creation request received for {} user/s".format(len(users)))  
        log.info("User/s validation started")
        for i,user in enumerate(users):
            validity = UserUtils.validate_user_input_creation(user)
            if validity is not None:
                log.info("User validation failed for user{}".format(i+1))
                return validity, 400
        log.info("Users are validated")

        try:
            result = userRepo.create_users(users)
            if result is not None:
                log.info("User creation failed | {}".format(str(result)))
                return result, 400   
            else:
                res = CustomResponse(Status.SUCCESS_USR_CREATION.value, None)
                log.info("User creation successful")
                return res.getresjson(), 200
        except Exception as e:
            log.exception("Exception while creating user records: {}".format(str(e)))
            return post_error("Exception occurred", "Exception while performing user creation:{}".format(str(e)), None), 400


class UpdateUsers(Resource):

    def post(self):
        body = request.get_json()
        if 'users' not in body or not body['users']:
            return post_error("Data Missing", "users not found", None), 400

        users = body['users']
        user_id = None
        user_id=request.headers["x-user-id"]
        log.info("Updation request received for {} user/s".format(len(users)))
        log.info("User/s validation started")
        for i,user in enumerate(users):
            validity = UserUtils.validate_user_input_updation(user)
            if validity is not None:
                log.info("User validation failed for user{}".format(i+1))
                return validity, 400
        log.info("Users are validated")

        try:
            result = userRepo.update_users(users,user_id)
            if result== True:
                log.info("User/s updation successful")
                res = CustomResponse(Status.SUCCESS_USR_UPDATION.value, None)
                return res.getresjson(), 200
            else:
                log.info("User updation failed | {}".format(str(result)))
                return result, 400

        except Exception as e:
            log.exception("Exception while updating user records: " + str(e))
            return post_error("Exception occurred", "Exception while performing user updation:{}".format(str(e)), None), 400


class SearchUsers(Resource):

    def post(self):
        user_ids        =   []
        user_emails     =   []
        role_codes      =   []
        org_codes       =   []
        offset          =   None
        limit_value     =   None
        skip_pagination =   None

        body = request.get_json()
        if "userIDs" in body:
            user_ids    =   body['userIDs']
        if "emails" in body:
            user_emails =   body['emails']
        if "roleCodes" in body:
            role_codes  =   body['roleCodes']
        if "orgCodes" in body:
            org_codes   =   body['orgCodes']
        if "offset" in body:
            offset      =   body['offset']
        if "limit" in body:
            limit_value =   body['limit']      
        if "skip_pagination" in body:
            skip_pagination =   body['skip_pagination']
        
        log.info("User/s search request received | {}".format(str(body)))
        
        if not user_ids and not user_emails and not role_codes and not org_codes and not offset and not limit_value:
            offset      =   config.OFFSET_VALUE
            limit_value =   config.LIMIT_VALUE
        try:
            result = userRepo.search_users(user_ids, user_emails, role_codes,org_codes,offset,limit_value,skip_pagination)
            log.info("User/s search successful")
            if result == None:
                log.info("No users matching the search criterias")
                res = CustomResponse(Status.EMPTY_USR_SEARCH.value, None)
                return res.getresjson(), 200
            res = CustomResponse(Status.SUCCESS_USR_SEARCH.value, result[0],result[1])
            return res.getresjson(), 200
        except Exception as e:
            log.exception("Exception while searching user records: " +str(e))
            return post_error("Exception occurred", "Exception while performing user search:{}".format(str(e)), None), 400


class OnboardUsers(Resource):

    def post(self):
        body = request.get_json()
        if 'users' not in body or not body['users']:
            return post_error("Data Missing", "users not found", None), 400
        users = body['users']
        log.info("Request received for onboarding {} user/s".format(len(users)), MODULE_CONTEXT)
        log.info("User/s validation started")
        for i,user in enumerate(users):
            validity = UserUtils.validate_user_input_creation(user)
            if validity is not None:
                log.info("User validation failed for user{}".format(i+1), MODULE_CONTEXT)
                return validity, 400
            log.info("Users are validated")
        try:
            result = userRepo.onboard_users(users)
            if result is not None:
                log.info("User/s onboarding failed | {}".format(str(result)), MODULE_CONTEXT)
                return result, 400              
            else:
                log.info("User/s onboarding successful")
                res = CustomResponse(Status.SUCCESS_USR_ONBOARD.value, None)
                return res.getresjson(), 200
        except Exception as e:
            log.exception("Exception while creating user records for users on-boarding: " + str(e), MODULE_CONTEXT, e)
            return post_error("Exception occurred", "Exception while performing users on-boarding::{}".format(str(e)), None), 400


class SearchRoles(Resource):

    def get(self):
        try:
            log.info("Request for role search received")
            result = userRepo.get_roles()
            if "errorID" in result:
                log.info("Role search failed")
                return result, 400
            else:
                log.info("Role search successful")
                res = CustomResponse(Status.SUCCESS_ROLE_SEARCH.value, result)
                return res.getresjson(), 200
        except Exception as e:
            log.exception("Exception while searching user records: " +
                          str(e), MODULE_CONTEXT, e)
            return post_error("Exception occurred", "Exception while performing user search::{}".format(str(e)), None), 400


class Health(Resource):
    def get(self):
        response = {"code": "200", "status": "ACTIVE"}
        return jsonify(response)


class GetApiKey(Resource):
    def post(self):
        body = request.get_json()
        if "userID" not in body.keys():
            return post_error("Data Missing", "users not found", None), 400
        user = body['userID']
        appName = None
        userAPIKeys = UserUtils.get_user_api_keys(user,appName)
        #userAPIKeys.append({"userId": user})
        if isinstance(userAPIKeys, list):
            res = CustomResponse(Status.SUCCESS_GET_APIKEY.value, userAPIKeys)
            return res.getresjson(), 200
        else:
            return post_error("400", "userID cannot be empty, please provide one.")

class RevokeApiKey(Resource): #perform deletion of the userAPIKey from UserID
    def post(self): #userID and userApiKey mandatory.
        body = request.get_json()
        if "userID" not in body.keys(): 
            return post_error("400", "userID not found", None), 400       
        if "ulcaApiKey" not in body.keys():
            return post_error("400", "ulcaApiKey not found", None), 400
        userid = body["userID"]
        userapikey = body["ulcaApiKey"]
        revokekey = UserUtils.revoke_userApiKey(userid, userapikey)
        if revokekey["nModified"] == 1:
            res = CustomResponse(Status.SUCCESS_REVOKE_APIKEY.value, "SUCCESS")
            return res.getresjson(), 200
        else:
            return post_error("400", "Unable to revoke ulcaApiKey. Please check the userID and/or ulcaApiKey.")

class GenerateApiKey(Resource):
    def post(self):
        body = request.get_json()
        if "userID" not in body.keys(): 
            return post_error("400", "Please provide userID", None), 400
        if "appName" not in body.keys():
            return post_error("400", "Please provide appName", None), 400
       
        serviceProviderKey = []
        user = body["userID"]
        checkAppName = UserUtils.check_appName(body["appName"])
        if checkAppName:
            return post_error("400", "appName cannot contain special chars and/or uppercase", None), 400
        elif not checkAppName:
            appName = body["appName"] 
        user_api_keys, status = UserUtils.get_user_api_keys(user,appName)
        if status == False:
            if isinstance(user_api_keys,list) and len(user_api_keys) < MAX_API_KEY:
                generatedapikey = UserUtils.generate_user_api_key()
                UserUtils.insert_generated_user_api_key(user,appName,generatedapikey,serviceProviderKey)
                res = CustomResponse(Status.SUCCESS_GENERATE_APIKEY.value, generatedapikey)
                return res.getresjson(), 200
            else:
                return post_error("400", "Maximum Key Limit Reached", None), 400
        
        if status == True and "errorID" in user_api_keys.keys():
            return post_error("400", user_api_keys['message'], None), 400


class ToggleDataTracking(Resource):
    def post(self):
        body = request.get_json()
        #validation
        if "userID" not in body.keys(): 
            return post_error("400", "Please provide userID", None), 400
        if "ulcaApiKey" not in body.keys():
            return post_error("400", "Please provide ulcaApiKey", None), 400
        if "dataTracking" not in body.keys():
            return post_error("400", "Please provide dataTracking", None), 400
        if "ServiceProviderKey" not in body.keys():
            return post_error("400", "Please provide ServiceProviderKey", None), 400







class GenerateServiceProviderKey(Resource):
    def post(self):
        body = request.get_json()
       
        if "pipelineId" not in body.keys():
            return post_error("400", "Please provide pipelineId", None), 400
        if "userID" not in body.keys():
            return post_error("400", "Please provide userID", None), 400
        if "ulcaApiKey" not in body.keys():
            return post_error("400", "Please provide ulcaApiKey", None), 400
        if "dataTracking" not in body.keys():
            dataTracking = True
        if "dataTracking" in body.keys():
            if body['dataTracking'] == True:
                dataTracking = True
            elif body['dataTracking'] == False:
                dataTracking = False


        
        pipelineID = UserUtils.get_pipelineId(body["pipelineId"]) #ULCA-PROCESS-TRACKER
        #log.info(f"user_document details {user_document}")
        if isinstance(pipelineID,dict) and pipelineID:
            masterList = []
            if "serviceProvider" in pipelineID.keys():
                serviceProviderName = pipelineID["serviceProvider"]["name"]
            if "apiEndPoints" in pipelineID.keys() and "inferenceEndPoint" in pipelineID.keys():
                serviceProviderKeyUrl = pipelineID["apiEndPoints"]["apiKeyUrl"]
                masterkeyname = pipelineID["inferenceEndPoint"]["masterApiKey"]["name"]
                masterkeyvalue = pipelineID["inferenceEndPoint"]["masterApiKey"]["value"]
                masterList.append(masterkeyname)
                masterList.append(masterkeyvalue)
        elif pipelineID == None:
            return post_error("400", "pipelineID does not exists.   Please provide a valid pipelineId", None), 400
        user_document,email  = UserUtils.get_userDoc(body["userID"]) #UMS
        if isinstance(user_document, list) and user_document:
            if not any(usr['ulcaApiKey'] == body['ulcaApiKey'] for usr in user_document):
                return post_error("400", "ulcaApiKey does not exist. Please provide a valid one.", None), 400
            for usr in user_document:
                if body["ulcaApiKey"] in usr.values():
                    serviceProviderNameExists = False
                    #Check if ServiceProviderName Exists?
                    if "serviceProviderKeys" in usr.keys() and len(usr['serviceProviderKeys'])!=0: 
                        for each_provider in usr['serviceProviderKeys']:
                            if each_provider['serviceProviderName'] == serviceProviderName:
                                serviceProviderNameExists = True
                                break
                    if serviceProviderNameExists == True:
                        servProvKeyExists = {}
                        for users in usr["serviceProviderKeys"]:
                            if users["serviceProviderName"] == serviceProviderName:
                                servProvKeyExists["serviceProviderKeys"] = users
                        return servProvKeyExists
                    else:
                        decryptedKeys = UserUtils.decryptAes(SECRET_KEY,masterList)
                        generatedSecretKeys = UserUtils.get_service_provider_keys(email, usr["appName"],serviceProviderKeyUrl,decryptedKeys)
                        addServiceKeys, servProvAdded = UserUtils.pushServiceProvider(generatedSecretKeys, body["ulcaApiKey"],serviceProviderName, dataTracking)
                        if addServiceKeys["nModified"] == 1 and addServiceKeys["updatedExisting"] == True:
                            servProvAdded["message"] = "Service Provider Key created"
                        log.info(addServiceKeys)
            return servProvAdded
        elif user_document == None:
            return post_error("400", "userID does not exist, please provide a valid one.", None), 400
        
            


        
class RemoveServiceProviderKey(Resource):
    def post(self):
        body = request.get_json()
        if "serviceProviderName" not in body.keys():
            return post_error("400", "Please provide serviceProviderName", None), 400
        if "userID" not in body.keys():
            return post_error("400", "Please provide userID", None), 400
        if "ulcaApiKey" not in body.keys():
            return post_error("400", "Please provide ulcaApiKey", None), 400

        pullRecord = UserUtils.removeServiceProviders(body['userID'],body['ulcaApiKey'],body["serviceProviderName"])
        if pullRecord['nModified'] == 1:
            res = CustomResponse(Status.REMOVE_SERVICE_PROVIDER.value, "SUCCESS")
            return res.getresjson(), 200
        else:
            return post_error("400", "Unable to revoke service provider details, please check userID and/or ulcaApiKey and/or service provider Name ", None), 400
                    
        







