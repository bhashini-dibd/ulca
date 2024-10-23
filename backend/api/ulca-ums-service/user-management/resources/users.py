from flask_restful import Resource
from repositories import UserManagementRepositories
from models import CustomResponse,SearchCustomResponse, CustomResponseDhruva,Status, post_error
from utilities import UserUtils, normalize_bson_to_json
from flask import request, jsonify
import config
import logging
import requests
from config import MAX_API_KEY, SECRET_KEY, PATCH_URL, ONBOARDING_AUTH_HEADER

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
            res = SearchCustomResponse(Status.SUCCESS_USR_SEARCH.value, result[0],result[1])
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

class GetApiKeysForProfile(Resource):
    def post(self):
        body = request.get_json()
        if "userID" not in body.keys():
            return post_error("Data Missing", "users not found", None), 400
        user = body['userID']
        appName = None
        userAPIKeys = UserUtils.get_user_api_keys(user,appName)
        userServiceProvider = UserUtils.listOfServiceProviders()
        if not userServiceProvider:
            return post_error("400", "User Service Provider is None")
        for i in range(0,len(userAPIKeys)):
            if "serviceProviderKeys" in userAPIKeys[i].keys():
                existing_names = []                    
                for existing_keys in userAPIKeys[i]["serviceProviderKeys"]: 
                    existing_names.append(existing_keys["serviceProviderName"])
                if not existing_names:
                    userAPIKeys[i]["serviceProviderKeys"].append({"serviceProviderName":userServiceProvider})
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


class GenerateServiceProviderKeyWithoutLogin(Resource):
    def post(self):
        body = request.get_json()
        if "userID" not in body.keys():
            return post_error("400", "Please provide userID", None), 400
        if "udyatApiKey" not in body.keys():
            return post_error("400", "Please provide udyatApiKey", None), 400
        if "appName" not in body.keys():
            return post_error("400", "Please provide appName", None), 400
        
        # Below 2 lines are for key generation within Authenticator Application
        if "udyatApiKey" in body.keys() and "userID" in body.keys() and "appName" in body.keys():
            return UserUtils.generateServiceProviderKey(body["userID"],body["appName"],body["udyatApiKey"])


class GenerateServiceProviderKey(Resource):
    def post(self):
        body = request.get_json()
        
        # Below 2 lines are for key generation within Authenticator Application
        if "udyatApiKey" in body.keys() and "userID" in body.keys() and "appName" in body.keys():
            return UserUtils.generateServiceProviderKey(body["userID"],body["appName"],body["udyatApiKey"])
            
        if "pipelineId" not in body.keys() and "serviceProviderName" not in body.keys():
            return post_error("400", "Please provide pipelineId or serviceProviderName", None), 400
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


        if "serviceProviderName" in body.keys():
            pipelineID = UserUtils.get_pipelineIdbyServiceProviderName(body["serviceProviderName"]) #ULCA-PROCESS-TRACKER
        else:
            pipelineID = UserUtils.get_pipelineId(body["pipelineId"]) #ULCA-PROCESS-TRACKER
        #log.info(f"user_document details {user_document}")
        if not pipelineID:
            return post_error("400", "pipelineID does not exists.   Please provide a valid pipelineId", None), 400
        if isinstance(pipelineID,dict) and pipelineID:
            masterList = []
            if "serviceProvider" not in pipelineID.keys() and "apiEndPoints" not in pipelineID.keys() and "inferenceEndPoint" not in pipelineID.keys():
                return post_error("400", "serviceProvider or apiEndPoints or inferenceEndPoint does not exists.   Please provide a valid details", None), 400

            serviceProviderName = pipelineID["serviceProvider"]["name"]
            serviceProviderKeyUrl = pipelineID["apiEndPoints"]["apiKeyUrl"]
            masterkeyname = pipelineID["inferenceEndPoint"]["masterApiKey"]["name"]
            masterkeyvalue = pipelineID["inferenceEndPoint"]["masterApiKey"]["value"]
            masterList.append(masterkeyname)
            masterList.append(masterkeyvalue)
 
        user_document,email  = UserUtils.get_userDoc(body["userID"]) #UMS
        if not user_document and not email:
            return post_error("400", "Error in fetching ulcaApiKey. Please check if it exists.", None), 400
        if isinstance(user_document, list) and user_document:
            if not any(usr['ulcaApiKey'] == body['ulcaApiKey'] for usr in user_document):
                return post_error("400", "ulcaApiKey does not exist. Please provide a valid one.", None), 400
            for usr in user_document:
                if body["ulcaApiKey"] in usr.values():
                    serviceProviderNameExists = False
                    #Check if ServiceProviderName Exists?
                    if "serviceProviderKeys" in usr.keys() and len(usr['serviceProviderKeys'])!=0: 
                        for each_provider in usr['serviceProviderKeys']:
                            if "serviceProviderName" not in each_provider.keys():
                                return post_error("400", "serviceProviderName is empty for the user.", None), 400
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
                        log.info(f"decrepted keys for generate service provider keys {decryptedKeys}")
                        generatedSecretKeys = UserUtils.get_service_provider_keys(email, usr["appName"],serviceProviderKeyUrl,decryptedKeys, dataTracking)
                        addServiceKeys, servProvAdded = UserUtils.pushServiceProvider(generatedSecretKeys, body["ulcaApiKey"],serviceProviderName, dataTracking)
                        returnServiceProviderKey = {"serviceProviderKeys":servProvAdded["serviceProviderKeys"][0]}
                        if addServiceKeys["nModified"] == 1 and addServiceKeys["updatedExisting"] == True:
                            returnServiceProviderKey["message"] = "Service Provider Key created"
                        elif not addServiceKeys["nModified"] == 1 :
                            return post_error("400", "Service Provider Key not created", None), 400
                        log.info(addServiceKeys)
            if "ulcaApiKey" in body.keys():
                returnServiceProviderKey['ulcaApiKey'] = body["ulcaApiKey"]
            return returnServiceProviderKey

        
            

class RemoveServiceProviderKeyWithoutLogin(Resource):
    def post(self):
        body = request.get_json()
        if "userID" not in body.keys():
            return post_error("400", "Please provide userID", None), 400
        if "udyatApiKey" not in body.keys():
            return post_error("400", "Please provide udyatApiKey", None), 400
        if "appName" not in body.keys():
            return post_error("400", "Please provide appName", None), 400
        

        # Below 2 lines are for key generation within Authenticator Application
        if "udyatApiKey" in body.keys() and "userID" in body.keys() and "appName" in body.keys():
            return UserUtils.removeServiceProviderKey(body["userID"],body["appName"],body["udyatApiKey"])

        
class RemoveServiceProviderKey(Resource):
    def post(self):
        body = request.get_json()
        
        # Below 2 lines are for key generation within Authenticator Application
        if "udyatApiKey" in body.keys() and "userID" in body.keys() and "appName" in body.keys():
            return UserUtils.removeServiceProviderKey(body["userID"],body["appName"],body["udyatApiKey"])

        if "serviceProviderName" not in body.keys():
            return post_error("400", "Please provide serviceProviderName", None), 400
        if "userID" not in body.keys():
            return post_error("400", "Please provide userID", None), 400
        if "ulcaApiKey" not in body.keys():
            return post_error("400", "Please provide ulcaApiKey", None), 400

        # Get service pipeline
        pipelineID = UserUtils.get_pipelineIdbyServiceProviderName(body["serviceProviderName"])
        if isinstance(pipelineID,dict) and pipelineID:
            masterList = []
            if "serviceProvider" not in pipelineID.keys() and "apiEndPoints" not in pipelineID.keys() and "inferenceEndPoint" not in pipelineID.keys():
                return post_error("400", "serviceProvider or apiEndPoints or inferenceEndPoint does not exists.   Please provide a valid details", None), 400

            serviceProviderName = pipelineID["serviceProvider"]["name"]
            serviceProviderKeyUrl = pipelineID["apiEndPoints"]["apiKeyUrl"]
            masterkeyname = pipelineID["inferenceEndPoint"]["masterApiKey"]["name"]
            masterkeyvalue = pipelineID["inferenceEndPoint"]["masterApiKey"]["value"]
            masterList.append(masterkeyname)
            masterList.append(masterkeyvalue)
        elif pipelineID == None:
            return post_error("400", "pipelineID does not exists.   Please provide a valid pipelineId", None), 400

        # Retrieve user details 
        user_document,email  = UserUtils.get_userDoc(body["userID"]) #UMS
        if not user_document and not email:
            return post_error("400", "userID does not exists.   Please provide a valid userID", None), 400
        if isinstance(user_document, list) and user_document:
            if not any(usr['ulcaApiKey'] == body['ulcaApiKey'] for usr in user_document):
                return post_error("400", "ulcaApiKey does not exist. Please provide a valid one.", None), 400
            for usr in user_document:
                if body["ulcaApiKey"] in usr.values():
                    if "appName" in usr.keys():
                        decryptedKeys = UserUtils.decryptAes(SECRET_KEY,masterList)
                        generatedSecretKeys = UserUtils.revoke_service_provider_keys(email, usr["appName"],serviceProviderKeyUrl,decryptedKeys)
        pullRecord = UserUtils.removeServiceProviders(body['userID'],body['ulcaApiKey'],body["serviceProviderName"])
        if pullRecord['nModified'] == 1:
            res = CustomResponse(Status.REMOVE_SERVICE_PROVIDER.value, "SUCCESS")
            return res.getresjson(), 200
        else:
            return post_error("400", "Unable to revoke service provider details, please check userID and/or ulcaApiKey and/or service provider Name ", None), 400
                    
        
class ToggleDataTracking(Resource):
    def post(self):
        body = request.get_json()
        if "serviceProviderName" not in body.keys():
            return post_error("400", "Please provide serviceProviderName", None), 400
        if "userID" not in body.keys():
            return post_error("400", "Please provide userID", None), 400
        if "ulcaApiKey" not in body.keys():
            return post_error("400", "Please provide ulcaApiKey", None), 400
        if "dataTracking" not in body.keys():
            return post_error("400", "Please provide value for dataTracking", None), 400  
        if isinstance(body['dataTracking'], bool):
            boole = body['dataTracking']
        else:
            return post_error("400", "Please provide Boolean", None), 400

        #get email from userID, appName from unique ulcaApiKey, masterKeyDetails for headers auth fropm pipeLine, apiKeyUrl from pipeline.
        #ONly success result from patch request needs to be sent to frontEnd.
        #getEmail from userID
        userEmail, appName_ = UserUtils.getUserEmail(body['userID'],body['ulcaApiKey'])
        if not userEmail or not appName_:
           return post_error("400", "Error in fetching Details, please check the userID and ulcaApiKey", None), 400
        pipeline_doc = UserUtils.getPipelinefromSrvcPN(body['serviceProviderName'])
        if not pipeline_doc and not isinstance(pipeline_doc,dict):
            return post_error("400", "Please check the Service Provider Name", None), 400
        pipeline_masterkeys = []#dict for headers
        pipeline_masterkeys.append(pipeline_doc['inferenceEndPoint']['masterApiKey']['name'])
        pipeline_masterkeys.append(pipeline_doc['inferenceEndPoint']['masterApiKey']['value'])
        patch_url = pipeline_doc["apiEndPoints"]["apiKeyUrl"]
        decrypt_headers = UserUtils.decryptAes(SECRET_KEY,pipeline_masterkeys)
        req_body = {"emailId" : userEmail, "appName" :  appName_,'dataTracking' : boole}
        patch_req = requests.patch(url = patch_url, headers=decrypt_headers, json=req_body)
        log.info(f"Patch Request Response :: {patch_req}...............{patch_req.json()} .............{patch_req.status_code}")
        if (patch_req.json()['status']) == 'success':
            toggled_matched, toggle_modified = UserUtils.updateDataTrackingValuePull(body['userID'], body['ulcaApiKey'], body['serviceProviderName'], boole)
            if toggle_modified == 1:
                res = CustomResponse(Status.TOGGLED_DATA_SUCCESS.value, "SUCCESS")
                return res.getresjson(), 200
            elif toggle_modified == 0 and toggled_matched == 1:
                res = CustomResponse(Status.TOGGLED_DATA_SUCCESS.value, "SUCCESS")
                return res.getresjson(), 200

        elif 'success' not in patch_req.json().keys():
            return post_error("400", "Unable to toggle Data Tracking at the moment, please try again", None), 400


class EnrollSpeaker(Resource):
    def post(self):
        body = request.get_json()
        print("Request Body: ", body)
        appName = request.args.get("appName")
        serviceProviderName = request.args.get("serviceProviderName")

        if appName is None:
            return post_error("400", "Please provide appName", None), 400
        if serviceProviderName is None:
            return post_error("400", "Please provide serviceProviderName", None), 400
        
        user_id=request.headers["x-user-id"]
        
        userinferenceApiKey = UserUtils.getUserInfKey(appName,user_id, serviceProviderName)
        print("useringerenceKey: ", userinferenceApiKey)
        if not userinferenceApiKey:
            return post_error("400", "Couldn't find the user inference api Key", None), 400
        api_dict = {"api-key":userinferenceApiKey}
        pipelineID = UserUtils.get_pipelineIdbyServiceProviderName(serviceProviderName)
        if not pipelineID:
            return post_error("400", "Couldn't find the user inference api Key", None), 400
        log.info(f"pipelineID {pipelineID}")
        log.info(f"userinferenceApiKey {userinferenceApiKey}")
    
        dhruva_result_json, dhruva_result_status_code = UserUtils.send_speaker_enroll_for_dhruva(userinferenceApiKey, body)
        res = CustomResponseDhruva(dhruva_result_json, dhruva_result_status_code)
        return res.getdhruvaresults(), dhruva_result_status_code
class VerifySpeaker(Resource):
    def post(self):
        body = request.get_json()
        appName = request.args.get("appName")
        serviceProviderName = request.args.get("serviceProviderName")

        if appName is None:
            return post_error("400", "Please provide appName", None), 400
        if serviceProviderName is None:
            return post_error("400", "Please provide serviceProviderName", None), 400
        
        user_id=request.headers["x-user-id"]
        
        userinferenceApiKey = UserUtils.getUserInfKey(appName,user_id, serviceProviderName)
        print("useringerenceKey: ", userinferenceApiKey)
        if not userinferenceApiKey:
            return post_error("400", "Couldn't find the user inference api Key", None), 400
        api_dict = {"api-key":userinferenceApiKey}
        pipelineID = UserUtils.get_pipelineIdbyServiceProviderName(serviceProviderName)
        if not pipelineID:
            return post_error("400", "Couldn't find the user inference api Key", None), 400
        log.info(f"pipelineID {pipelineID}")
        log.info(f"userinferenceApiKey {userinferenceApiKey}")

        dhruva_result_json, dhruva_result_status_code = UserUtils.send_speaker_verify_for_dhruva(userinferenceApiKey, body)
        res = CustomResponseDhruva(dhruva_result_json, dhruva_result_status_code)
        return res.getdhruvaresults(), dhruva_result_status_code

class DeleteSpeaker(Resource):
    def post(self):
        body = request.get_json()
        appName = request.args.get("appName")
        serviceProviderName = request.args.get("serviceProviderName")

        if appName is None:
            return post_error("400", "Please provide appName", None), 400
        if serviceProviderName is None:
            return post_error("400", "Please provide serviceProviderName", None), 400
        
        user_id=request.headers["x-user-id"]
        
        userinferenceApiKey = UserUtils.getUserInfKey(appName,user_id, serviceProviderName)
        print("useringerenceKey: ", userinferenceApiKey)
        if not userinferenceApiKey:
            return post_error("400", "Couldn't find the user inference api Key", None), 400
        api_dict = {"api-key":userinferenceApiKey}
        pipelineID = UserUtils.get_pipelineIdbyServiceProviderName(serviceProviderName)
        if not pipelineID:
            return post_error("400", "Couldn't find the user inference api Key", None), 400
        log.info(f"pipelineID {pipelineID}")
        log.info(f"userinferenceApiKey {userinferenceApiKey}")

        dhruva_result_json, dhruva_result_status_code = UserUtils.send_speaker_delete_for_dhruva(userinferenceApiKey, body)
        res = CustomResponseDhruva(dhruva_result_json, dhruva_result_status_code)
        return res.getdhruvaresults(), dhruva_result_status_code

class FetchSpeaker(Resource):
    def get(self): 
        appName = request.args.get("appName")
        serviceProviderName = request.args.get("serviceProviderName")

        if appName is None:
            return post_error("400", "Please provide appName", None), 400
        if serviceProviderName is None:
            return post_error("400", "Please provide serviceProviderName", None), 400
        
        user_id=request.headers["x-user-id"]

        userinferenceApiKey = UserUtils.getUserInfKey(appName,user_id, serviceProviderName)
        print("useringerenceKey: ", userinferenceApiKey)
        if not userinferenceApiKey:
            return post_error("400", "Couldn't find the user inference api Key", None), 400
        api_dict = {"api-key":userinferenceApiKey}
        pipelineID = UserUtils.get_pipelineIdbyServiceProviderName(serviceProviderName)
        if not pipelineID:
            return post_error("400", "Couldn't find the user inference api Key", None), 400
        log.info(f"pipelineID {pipelineID}")
        log.info(f"userinferenceApiKey {userinferenceApiKey}") 

        dhruva_result_json, dhruva_result_status_code = UserUtils.send_speaker_fetchall_for_dhruva(userinferenceApiKey)
        res = CustomResponseDhruva(dhruva_result_json, dhruva_result_status_code)
        return res.getdhruvaresults(), dhruva_result_status_code

class CreateGlossary(Resource):
    def post(self):
        user_id=request.headers["x-user-id"]
        body = request.get_json()
        if 'appName' not in body.keys():
            return post_error("400", "Please provide appName", None), 400
        if 'serviceProviderName' not in body.keys():
            return post_error("400", "Please provide serviceProviderName", None), 400
        if 'glossary' not in body.keys():
            return post_error("400", "Please provide Glossary", None), 400
        if not isinstance(body['glossary'],list):
            return post_error("400", "Glossary should be list of dictionaries", None), 400     
        if isinstance(body['glossary'], list):
            if "sourceLanguage" not in body['glossary'][0]:
                return post_error("400", "sourceLanguage is missing in glossary", None), 400 
            if "targetLanguage" not in body['glossary'][0]:
                return post_error("400", "sourceLanguage is missing in glossary", None), 400 
            if "sourceText" not in body['glossary'][0]:
                return post_error("400", "sourceText is missing in glossary", None), 400 
            if "targetText" not in body['glossary'][0]:
                return post_error("400", "targetText is missing in glossary", None), 400 
        
        userinferenceApiKey = UserUtils.getUserInfKey(body['appName'],user_id, body['serviceProviderName'])
        if not userinferenceApiKey:
            return post_error("400", "Couldn't find the user inference api Key", None), 400
        api_dict = {"api-key":userinferenceApiKey}
        pipelineID = UserUtils.get_pipelineIdbyServiceProviderName(body["serviceProviderName"])
        if not pipelineID:
            return post_error("400", "Couldn't find the user inference api Key", None), 400
        log.info(f"pipelineID {pipelineID}")
        log.info(f"userinferenceApiKey {userinferenceApiKey}")
        if pipelineID and isinstance(pipelineID,dict):
            masterList = []
        if "apiEndPoints" in pipelineID.keys() and "inferenceEndPoint" in pipelineID.keys() and "serviceProvider" in pipelineID.keys():
            masterkeyname = pipelineID["inferenceEndPoint"]["masterApiKey"]["name"]
            masterkeyvalue = pipelineID["inferenceEndPoint"]["masterApiKey"]["value"]
            #urlEndPoint    =  pipelineID["apiEndPoints"]["apiKeyUrl"]
            masterList.append(masterkeyname)
            masterList.append(masterkeyvalue)
        else:
            return post_error("400", "Couldn't find the endpoints, please check the service provider name", None), 400
        log.info(f"master api keys {masterList}")
        decrypt_headers = UserUtils.decryptAes(SECRET_KEY,masterList)
        if decrypt_headers:
            decrypt_headers.update(api_dict)
            log.info(f"decrypt_headers {decrypt_headers}")

        dhruva_result_json, dhruva_result_status_code = UserUtils.send_create_req_for_dhruva(decrypt_headers,body['glossary'])
        res = CustomResponseDhruva(dhruva_result_json, dhruva_result_status_code)
        return res.getdhruvaresults(), dhruva_result_status_code
        

class DeleteGlossary(Resource):
    def post(self):
        user_id=request.headers["x-user-id"]
        body = request.get_json()
        if 'appName' not in body.keys():
            return post_error("400", "Please provide appName", None), 400
        if 'serviceProviderName' not in body.keys():
            return post_error("400", "Please provide serviceProviderName", None), 400
        if 'glossary' not in body.keys():
            return post_error("400", "Please provide Glossary", None), 400
        if not isinstance(body['glossary'],list):
            return post_error("400", "Glossary should be list of dictionaries", None), 400     
        if isinstance(body['glossary'], list):
            if "sourceLanguage" not in body['glossary'][0]:
                return post_error("400", "sourceLanguage is missing in glossary", None), 400 
            if "targetLanguage" not in body['glossary'][0]:
                return post_error("400", "sourceLanguage is missing in glossary", None), 400 
            if "sourceText" not in body['glossary'][0]:
                return post_error("400", "sourceText is missing in glossary", None), 400 
            if "targetText" not in body['glossary'][0]:
                return post_error("400", "targetText is missing in glossary", None), 400 
        
        userinferenceApiKey = UserUtils.getUserInfKey(body['appName'],user_id, body['serviceProviderName'])
        if not userinferenceApiKey:
            return post_error("400", "Couldn't find the user inference api Key", None), 400
        api_dict = {"api-key":userinferenceApiKey}
        pipelineID = UserUtils.get_pipelineIdbyServiceProviderName(body["serviceProviderName"])
        if not pipelineID:
            return post_error("400", "Couldn't find the user inference api Key", None), 400
        log.info(f"pipelineID {pipelineID}")
        log.info(f"userinferenceApiKey {userinferenceApiKey}")
        if pipelineID and isinstance(pipelineID,dict):
            masterList = []
        if "apiEndPoints" in pipelineID.keys() and "inferenceEndPoint" in pipelineID.keys() and "serviceProvider" in pipelineID.keys():
            masterkeyname = pipelineID["inferenceEndPoint"]["masterApiKey"]["name"]
            masterkeyvalue = pipelineID["inferenceEndPoint"]["masterApiKey"]["value"]
            masterList.append(masterkeyname)
            masterList.append(masterkeyvalue)
        else:
            return post_error("404", "Couldn't find the endpoints, please check the service provider name", None), 404
        log.info(f"master api keys {masterList}")
        decrypt_headers = UserUtils.decryptAes(SECRET_KEY,masterList)
        if decrypt_headers:
            decrypt_headers.update(api_dict)
            log.info(f"decrypt_headers {decrypt_headers}")
        dhruva_result_json, dhruva_result_status_code = UserUtils.send_delete_req_for_dhruva(decrypt_headers,body['glossary'])
        res = CustomResponseDhruva(dhruva_result_json, dhruva_result_status_code)
        return res.getdhruvaresults(), dhruva_result_status_code        
        

class FetchGlossary(Resource):
    def get(self):    
        appName = request.args.get("appName")
        serviceProviderName = request.args.get("serviceProviderName")
        user_id=request.headers["x-user-id"]
        userinferenceApiKey = UserUtils.getUserInfKey(appName,user_id, serviceProviderName)
        print("useringerenceKey: ", userinferenceApiKey)
        if not userinferenceApiKey:
            return post_error("400", "Couldn't find the user inference api Key", None), 400
        api_dict = {"api-key":userinferenceApiKey}
        pipelineID = UserUtils.get_pipelineIdbyServiceProviderName(serviceProviderName)
        if not pipelineID:
            return post_error("400", "Couldn't find the user inference api Key", None), 400
        log.info(f"pipelineID {pipelineID}")
        log.info(f"userinferenceApiKey {userinferenceApiKey}")
        if pipelineID and isinstance(pipelineID,dict):
            masterList = []
        if "apiEndPoints" not in pipelineID.keys() and "inferenceEndPoint" not in pipelineID.keys() and "serviceProvider" not in pipelineID.keys():
            return post_error("404", "Couldn't find the endpoints, please check the service provider name", None), 404
        masterkeyname = pipelineID["inferenceEndPoint"]["masterApiKey"]["name"]
        masterkeyvalue = pipelineID["inferenceEndPoint"]["masterApiKey"]["value"]
        masterList.append(masterkeyname)
        masterList.append(masterkeyvalue)
        log.info(f"master api keys {masterList}")
        decrypt_headers = UserUtils.decryptAes(SECRET_KEY,masterList)
        if decrypt_headers:
            decrypt_headers.update(api_dict)
            log.info(f"decrypt_headers {decrypt_headers}")

        dhruva_result_json, dhruva_result_status_code = UserUtils.send_fetch_req_for_dhruva(decrypt_headers)
        res = CustomResponseDhruva(dhruva_result_json, dhruva_result_status_code)
        return res.getdhruvaresults(), dhruva_result_status_code   


class OnboardingAppProfile(Resource):
    def get(self):
        email = request.args.get("email")
        authorization_header = request.headers.get("Authorization")
        print(f"ONBOARDING AUTH HEADER :: {ONBOARDING_AUTH_HEADER}")
        if authorization_header != ONBOARDING_AUTH_HEADER:
            return post_error("Data Missing", "Unauthorized to perform this operation", None), 401
        if "email" is None:
            return post_error("Data Missing", "Email ID is not entered", None), 400
        user = email
        appName = None
        userAPIKeys = UserUtils.get_email_api_keys(user,appName)
        if isinstance(userAPIKeys,dict) and userAPIKeys.get('message') == "This userId address is not registered with ULCA":
            return post_error("400", "This userId address is not registered with ULCA")            
        userServiceProvider = UserUtils.listOfServiceProviders()
        if not userServiceProvider:
            return post_error("400", "User Service Provider is None")
        print(f"userAPIKeys :: {userAPIKeys}")
        print(f"userServiceProvider :: {userServiceProvider}")
        
        userID = userAPIKeys['userID']
        print(f"userID :: {userID}")

        if isinstance(userAPIKeys,list) and len(userAPIKeys) == 0:
            return post_error("400", "User {userID}, does not have any API Keys registered within Udyat")
        
        #userID = userAPIKeys['userID']
        userAPIKeys = userAPIKeys['apiKeyDetails']
        for i in range(0,len(userAPIKeys)):
            if "serviceProviderKeys" in userAPIKeys[i].keys():
                existing_names = []                    
                for existing_keys in userAPIKeys[i]["serviceProviderKeys"]: 
                    existing_names.append(existing_keys["serviceProviderName"])
                if not existing_names:
                    userAPIKeys[i]["serviceProviderKeys"].append({"serviceProviderName":userServiceProvider})
        
        if isinstance(userAPIKeys, list):
            data = [{"userID":userID,"email":email,"apiKeys":userAPIKeys}]
            res = CustomResponse(Status.SUCCESS_GET_APIKEY.value, data)
            return res.getresjson(), 200
        else:
            return post_error("400", "userID cannot be empty, please provide one.")

# to retrieve user key details only

class OnboardingAppUserKeyDetails(Resource):
    def get(self):
        email = request.args.get("email")
        authorization_header = request.headers.get("Authorization")
        print(f"ONBOARDING AUTH HEADER :: {ONBOARDING_AUTH_HEADER}")
        if authorization_header != ONBOARDING_AUTH_HEADER:
            return post_error("Data Missing", "Unauthorized to perform this operation", None), 401
        if "email" is None:
            return post_error("Data Missing", "Email ID is not entered", None), 400
        user = email
        appName = None
        userAPIKeys = UserUtils.get_email_api_keys(user,appName)
        if isinstance(userAPIKeys,dict) and userAPIKeys.get('message') == "This userId address is not registered with ULCA":
            return post_error("400", "This userId address is not registered with ULCA")
        
        try:
            user_keys = UserUtils.get_data_from_keybase(email,keys=True)
            print(f"user_keys already :: {user_keys}")

            if not user_keys:
                user_keys   =   UserUtils.generate_api_keys(email)
                print(f"user_keys new :: {user_keys}")

            if "errorID" in user_keys:
                return user_keys
           
            user_details = {"userKeys":user_keys}
            data = [{"userDetails":user_details}]
            res = CustomResponse(Status.SUCCESS_GET_APIKEY.value, data)
            return res.getresjson(), 200            

        except Exception as e:
            log.exception("Database connection exception | {} ".format(str(e)))
            return post_error("Database  exception", "An error occurred while processing on the database:{}".format(str(e)), None)

# to retrieve user details only

class OnboardingAppUserDetails(Resource):
    def get(self):
        email = request.args.get("email")
        authorization_header = request.headers.get("Authorization")
        print(f"ONBOARDING AUTH HEADER :: {ONBOARDING_AUTH_HEADER}")
        if authorization_header != ONBOARDING_AUTH_HEADER:
            return post_error("Data Missing", "Unauthorized to perform this operation", None), 401
        if "email" is None:
            return post_error("Data Missing", "Email ID is not entered", None), 400
        user = email
        appName = None
        userAPIKeys = UserUtils.get_email_api_keys(user,appName)
        if isinstance(userAPIKeys,dict) and userAPIKeys.get('message') == "This userId address is not registered with ULCA":
            return post_error("400", "This userId address is not registered with ULCA")
        
        try:
            #fetching the user details from db
            user_details = UserUtils.retrieve_user_data_by_key(user_email=email)          
            print(f"user_details :: {user_details}")

            if user_details.count() == 0:
                return post_error("Data not valid","Error on fetching user details")
            for user in user_details:
                return {"userDetails": normalize_bson_to_json(user)}
            
            data = [{"userDetails":user_details,"email":email}]
            res = CustomResponse(Status.SUCCESS_GET_APIKEY.value, data)
            return res.getresjson(), 200            

        except Exception as e:
            log.exception("Database connection exception | {} ".format(str(e)))
            return post_error("Database  exception", "An error occurred while processing on the database:{}".format(str(e)), None)
