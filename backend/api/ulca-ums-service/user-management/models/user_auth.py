from os import name
from utilities import MODULE_CONTEXT, userutils
from db import get_db
from utilities import UserUtils
from .response import post_error
from datetime import datetime, timedelta
from utilities import UserUtils, normalize_bson_to_json
import time
import config
from config import USR_KEY_MONGO_COLLECTION, USR_MONGO_COLLECTION, USR_TEMP_TOKEN_MONGO_COLLECTION
import logging
from utilities import EnumVals

log = logging.getLogger('file')
admin_role_key          =   config.ADMIN_ROLE_KEY
verify_mail_expiry      =   config.USER_VERIFY_LINK_EXPIRY
apikey_expiry           =   config.USER_API_KEY_EXPIRY  



class UserAuthenticationModel(object):

    def user_login(self,user_email, password=None):
        """User Login."""

        try:
            user_keys = UserUtils.get_data_from_keybase(user_email,keys=True)
            collections = get_db()[USR_MONGO_COLLECTION]
            if not user_keys:
                user_keys   =   UserUtils.generate_api_keys(user_email)
            if "errorID" in user_keys:
                return user_keys

            
            #fetching user data
            user_details = collections.find({"email": user_email, "isActive": True,"isVerified":True},{"_id":0,"password":0})
            if user_details.count() == 0:
                return post_error("Data not valid","Error on fetching user details")
            for user in user_details:
                return {"userKeys":user_keys,"userDetails": normalize_bson_to_json(user)}
                
        except Exception as e:
            log.exception("Database connection exception | {} ".format(str(e)))
            return post_error("Database  exception", "An error occurred while processing on the database:{}".format(str(e)), None)


    def user_logout(self,user_name):
        """User Logout
        
        updating active status to False on user token collection.
        """

        try:
            #connecting to mongo instance/collection
            collections = get_db()[USR_MONGO_COLLECTION]
            #fetching user data
            record = collections.find({"user": user_name, "active": True})
            if record.count() == 0:
                return False
            if record.count() != 0:
                for user in record:
                    #updating status = False for user token collection
                    collections.update(user, {"$set": {"active": False, "end_time": eval(
                        str(time.time()).replace('.', '')[0:13])}})
                    log.info("Updated database record on user log out for {}".format(user_name), MODULE_CONTEXT)
                return True
        except Exception as e:
            log.exception("Database connection exception ",  MODULE_CONTEXT, e)
            return post_error("Database connection exception", "An error occurred while connecting to the database:{}".format(str(e)), None)


    def key_search(self,key):
        """Token search for user details"""

        try:
            log.info("searching for the user, using api-key")
            result = UserUtils.get_data_from_keybase(key,email=True)
            if "errorID" in result:
                return result
            email = result["email"]
            collections = get_db()[USR_MONGO_COLLECTION] 
            user = collections.find({"email":email,"isVerified":True,"isActive":True},{"password":0,"_id":0})
            if user.count() == 0:
                log.info("No user records found in db matching email: {}".format(email))
                return post_error("Invalid data", "Your key is not valid", None)
            for record in user:
                record["privateKey"] = result["privateKey"]
                return normalize_bson_to_json(record)

        except Exception as e:
            log.exception("Database connection exception | {}".format(str(e)))
            return post_error("Database connection exception", "An error occurred while connecting to the database:{}".format(str(e)), None)

  
    def forgot_password(self,user_email):
        """Generaing forgot password notification"""

        #connecting to mongo instance/collection
        user_collection         =   get_db()[USR_MONGO_COLLECTION]
        key_collection          =   get_db()[USR_KEY_MONGO_COLLECTION]
        token_collection        =   get_db()[USR_TEMP_TOKEN_MONGO_COLLECTION]
        user_record = user_collection.find({"email":user_email})
        name = user_record[0]["firstName"]
        record = token_collection.find({"email":user_email})
        #removing previous records if any
        if record.count() != 0:
            key_collection.remove({"email":user_email})
            token_collection.remove({"email":user_email})
            
        user_keys = UserUtils.get_data_from_keybase(user_email,keys=True)
        if not user_keys:
            user_keys   =   UserUtils.generate_api_keys(user_email)
        if "errorID" in user_keys:
            return user_keys
        user_keys["createdOn"] = datetime.utcnow()
        #inserting new id generated onto temporary token collection
        token_collection.insert(user_keys)
        #generating email notification
        result = UserUtils.generate_email_notification([{"email":user_email,"pubKey":user_keys["publicKey"],"pvtKey":user_keys["privateKey"],"name":name}],EnumVals.ForgotPwdTaskId.value)
        if result is not None:
            return result
        return True
    

    def reset_password(self,user_id,user_email,password):
        """Resetting password
        
        an active user can reset their own password,
        admin can reset password of any active users.
        """
        
        #generating password hash
        hashed = UserUtils.hash_password(password).decode("utf-8")
        try:
            #connecting to mongo instance/collection
            collections = get_db()[USR_MONGO_COLLECTION]
            key_collection = get_db()[USR_KEY_MONGO_COLLECTION]
            temp_collection = get_db()[USR_TEMP_TOKEN_MONGO_COLLECTION]
            #searching for valid record matching given user_id
            record = collections.find({"userID": user_id})
            if record.count() != 0:
                log.info("Record found matching the userID {}".format(user_id))
                for user in record:
                    #fetching the user roles
                    roles=user["roles"] 
                    #fetching user name
                    email=user["email"]

                #verifying the requested person, both admin and user can reset password   
                if (admin_role_key in roles) or (email == user_email):
                    log.info("Reset password request is checked against role permission and username")
                    results = collections.update({"email":user_email,"isActive":True}, {"$set": {"password": hashed}})
                    if 'writeError' in list(results.keys()):
                        return post_error("Database error", "writeError while updating record", None)
                    # removing temp API keys from user record
                    temp_collection.remove({"email":user_email})
                    # removing API keys from user record
                    key_collection.remove({"email":user_email})
                    return True
            else:
                log.info("No record found matching the userID {}".format(user_id))
                return post_error("Data Not valid","Invalid user details",None)              
        except Exception as e:
            log.exception("Database  exception ",  MODULE_CONTEXT, e)
            return post_error("Database exception", "Exception:{}".format(str(e)), None)         

  
    def verify_user(self,user_email,user_id):
        """User verification and activation."""

        try:
            name = None
            #connecting to mongo instance/collection
            collections = get_db()[USR_MONGO_COLLECTION]
            #checking for pre-verified records on the same username 
            primary_record= collections.find({"email": user_email,"isVerified": True})
            if primary_record.count()!=0:
                log.info("{} is already a verified user".format(user_email)) 
                return post_error("Not allowed","Your email has already been verified",None)
            #fetching user record matching userName and userID
            record = collections.find({"email": user_email,"userID":user_id})
            if record.count() ==1:
                for user in record:
                    register_time = user["registeredTime"]
                    name  = user["firstName"]
                    #checking whether verfication link had expired or not
                    if (datetime.utcnow() - register_time) > timedelta(hours=verify_mail_expiry):
                        log.exception("Verification link expired for {}".format(user_email))
                        #Removing previous record from db
                        collections.delete_many({"email": user_email,"userID":user_id})
                        return post_error("Data Not valid","Verification link expired. Please sign up again.",None)

                    results = collections.update(user, {"$set": {"isVerified": True,"isActive": True,"activatedTime": datetime.utcnow()}})
                    if 'writeError' in list(results.keys()):
                            return post_error("Database error", "writeError whie updating record", None)
                    log.info("Record updated for {}, activation & verification statuses are set to True".format(user_email))
            else:
                log.exception("No proper database records found for activation of {}".format(user_email))
                return post_error("Data Not valid","No records matching the given parameters ",None) 

            #Generating API Keys
            new_keys   =   UserUtils.generate_api_keys(user_email)
           
            #email notification for registered users
            user_notified=UserUtils.generate_email_notification([{"email":user_email,"name":name}],EnumVals.ConfirmationTaskId.value)
            if user_notified is not None:
                return user_notified
            return new_keys
        except Exception as e:
            log.exception("Database  exception ")
            return post_error("Database exception", "Exception:{}".format(str(e)), None)

        

    def activate_deactivate_user(self,user_email,status,from_id):
        """"Resetting activation status of verified users"""

        try:
            #connecting to mongo instance/collection
            collections = get_db()[USR_MONGO_COLLECTION]
            #searching for a verified account for given username
            record = collections.find({"email": user_email,"isVerified":True})
            if record.count()==0:
                log.info("{} is not a verified user".format(user_email), MODULE_CONTEXT)
                return post_error("Data Not valid","Not a verified user",None)
            if record.count() ==1:
                for user in record:
                    #validating the org where user belongs to
                    # validity=OrgUtils.validate_org(user["orgID"])
                    # if validity is not None:
                    #     log.info("{} belongs to an inactive org {}, hence operation failed".format(user_email,user["orgID"]), MODULE_CONTEXT)
                    #     return validity
                    if user["userID"] == from_id:
                        log.info("Self activation/deactivation not allowed")
                        return post_error("Invalid Request", "You are not allowed to change your status", None)
                    #updating active status on database
                    results = collections.update(user, {"$set": {"isActive": status}})
                    if 'writeError' in list(results.keys()):
                        log.info("Status updation on database failed due to writeError")
                        return post_error("DB error", "Something went wrong, please try again", None)
                    log.info("Status updation on database successful")
            else:
                return post_error("Data Not valid","Something went wrong, please try again",None)               
        except Exception as e:
            log.exception(f"Database  exception : {e}")
            return post_error("Database exception", "Something went wrong, please try again", None)
           
           
    def token_search(self,token):
        """Token search for user details"""

        try:
            log.info("searching for the keys")
            collections = get_db()[USR_TEMP_TOKEN_MONGO_COLLECTION] 
            user = collections.find({"publicKey":token})
            if user.count() == 0:
                log.info("Token has expired")
                return {"active": False}
            return {"active": True}

        except Exception as e:
            log.exception("Database connection exception | {}".format(str(e)))
            return post_error("Database connection exception", "An error occurred while connecting to the database:{}".format(str(e)), None)