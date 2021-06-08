from utilities import MODULE_CONTEXT, userutils
from db import get_db
from utilities import UserUtils
from .response import post_error
from datetime import datetime, timedelta
from utilities import UserUtils, normalize_bson_to_json
import time
import config
from config import USR_MONGO_COLLECTION, USR_TEMP_TOKEN_MONGO_COLLECTION, USR_KEY_MONGO_COLLECTION
import logging

log = logging.getLogger('file')
admin_role_key          =   config.ADMIN_ROLE_KEY
verify_mail_expiry      =   config.USER_VERIFY_LINK_EXPIRY
apikey_expiry           =   config.USER_API_KEY_EXPIRY  

class UserAuthenticationModel(object):

    def user_login(self,user_email, password=None):
        """User Login."""

        try:
            user_keys = UserUtils.get_data_from_keybase(user_email,keys=True)
            print(user_keys,"kkkkkkkkkkkkkkkkkkkk")
            if "errorID" in user_keys:
                return user_keys

            collections = get_db()[USR_MONGO_COLLECTION]
            #fetching user data
            user_details = collections.find({"email": user_email, "isActive": True},{"_id":0,"password":0})
            if user_details.count() == 0:
                return post_error("Data not valid","Error on fetching user details")
            for user in user_details:
                return {"userKeys":user_keys,"userDetails": normalize_bson_to_json(user)}

            # if (datetime.utcnow() - user_keys["createdOn"]) > timedelta(days=apikey_expiry):
            #     log.info("api-keys expired for {}".format(user_email), MODULE_CONTEXT)
            #     new_keys = userutils.renew_api_keys(user_email)
                
        except Exception as e:
            log.exception("Database connection exception ",  MODULE_CONTEXT, e)
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


    def token_search(self,key):
        """Token search for user details"""

        try:
            log.info("searching for the user, using api-key")
            result = UserUtils.get_data_from_keybase(key,email=True)
            if "errorID" in result:
                return result
            email = result["email"]
            collections = get_db()[USR_MONGO_COLLECTION] 
            user = collections.find({"email":email},{"password":0,"_id":0})
            for record in user:
                record["privateKey"] = result["privateKey"]
                return normalize_bson_to_json(record)

        except Exception as e:
            log.exception("Database connection exception ",  MODULE_CONTEXT, e)
            return post_error("Database connection exception", "An error occurred while connecting to the database:{}".format(str(e)), None)

  
    def forgot_password(self,user_name):
        """Generaing forgot password notification"""

        #generating random id
        rand_id=UserUtils.generate_user_id()
        #connecting to mongo instance/collection
        collections = get_db()[USR_TEMP_TOKEN_MONGO_COLLECTION]
        #inserting new id generated onto temporary token collection
        collections.insert({"user": user_name, "token": rand_id, "start_time": datetime.utcnow()})
        #generating email notification
        result = UserUtils.generate_email_reset_password(user_name,rand_id)
        if result is not None:
            return result
        return True
    

    def reset_password(self,user_id,user_name,password):
        """Resetting password
        
        an active user can reset their own password,
        admin can reset password of any active users.
        """
        
        #generating password hash
        hashed = UserUtils.hash_password(password).decode("utf-8")
        try:
            #connecting to mongo instance/collection
            collections = get_db()[USR_MONGO_COLLECTION]
            #searching for valid record matching given user_id
            record = collections.find({"userID": user_id})
            if record.count() != 0:
                log.info("Record found matching the userID {}".format(user_id), MODULE_CONTEXT)
                for user in record:
                    #fetching the user roles
                    roles=[ rol['roleCode'] for rol in user["roles"] ] 
                    #converting roles to upper keys
                    role_keys=[x.upper() for x in roles]
                    #fetching user name
                    username=user["userName"]
                #verifying the requested person, both admin and user can reset password   
                if (admin_role_key in role_keys) or (username == user_name):
                    log.info("Reset password request is checked against role permission and username")
                    results = collections.update({"userName":user_name,"is_active":True}, {"$set": {"password": hashed}})
                    if 'writeError' in list(results.keys()):
                        return post_error("Database error", "writeError while updating record", None)
                    return True
            else:
                log.info("No record found matching the userID {}".format(user_id), MODULE_CONTEXT)
                return post_error("Data Not valid","Invalid Credential",None)              
        except Exception as e:
            log.exception("Database  exception ",  MODULE_CONTEXT, e)
            return post_error("Database exception", "Exception:{}".format(str(e)), None)         

  
    def verify_user(self,user_email,user_id):
        """User verification and activation."""

        try:
            #connecting to mongo instance/collection
            collections = get_db()[USR_MONGO_COLLECTION]
            #checking for pre-verified records on the same username 
            primary_record= collections.find({"email": user_email,"isVerified": True})
            if primary_record.count()!=0:
                log.info("{} is already a verified user".format(user_email), MODULE_CONTEXT) 
                return post_error("Not allowed","This user already have a verified account",None)
            #fetching user record matching userName and userID
            record = collections.find({"email": user_email,"userID":user_id})
            if record.count() ==1:
                for user in record:
                    register_time = user["registeredTime"]
                    #checking whether verfication link had expired or not
                    if (datetime.utcnow() - register_time) > timedelta(hours=verify_mail_expiry):
                        log.info("Verification link expired for {}".format(user_email), MODULE_CONTEXT)
                        return post_error("Data Not valid","Verification link expired",None)

                    results = collections.update(user, {"$set": {"isVerified": True,"isActive": True,"activatedTime": datetime.utcnow()}})
                    if 'writeError' in list(results.keys()):
                            return post_error("Database error", "writeError whie updating record", None)
                    log.info("Record updated for {}, activation & verification statuses are set to True".format(user_email), MODULE_CONTEXT)
            else:
                log.info("No proper database records found for activation of {}".format(user_email), MODULE_CONTEXT)
                return post_error("Data Not valid","No records matching the given parameters ",None) 

            #Generating API Keys
            new_keys   =   UserUtils.generate_api_keys(user_email)
           
            #email notification for registered users
            user_notified=UserUtils.generate_email_confirmation(user_email)
            if user_notified is not None:
                return user_notified
            return new_keys
        except Exception as e:
            log.exception("Database  exception ",  MODULE_CONTEXT, e)
            return post_error("Database exception", "Exception:{}".format(str(e)), None)

        

    def activate_deactivate_user(self,user_email,status):
        """"Resetting activation status of verified users"""

        try:
            #connecting to mongo instance/collection
            collections = get_db()[USR_MONGO_COLLECTION]
            #searching for a verified account for given username
            record = collections.find({"userName": user_email,"is_verified":True})
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
                    #updating active status on database
                    results = collections.update(user, {"$set": {"is_active": status}})
                    if 'writeError' in list(results.keys()):
                        log.info("Status updation on database failed due to writeError")
                        return post_error("db error", "writeError whie updating record", None)
                    log.info("Status updation on database successful")
            else:
                return post_error("Data Not valid","Somehow there exist more than one record matching the given parameters ",None)               
        except Exception as e:
            log.exception("Database  exception ",  MODULE_CONTEXT, e)
            return post_error("Database exception", "Exception:{}".format(str(e)), None)
           
           
