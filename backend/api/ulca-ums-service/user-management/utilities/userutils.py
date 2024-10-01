import uuid
from uuid import uuid4
import smtplib
from email.mime.text import MIMEText
from email.mime.multipart import MIMEMultipart
from email.mime.base import MIMEBase
from email import encoders
import time
import re
import bcrypt
import db
from models.response import post_error
import jwt
import secrets
from .orgUtils import OrgUtils
from utilities import MODULE_CONTEXT
from .app_enums import EnumVals
import config
import json
from datetime import datetime,timedelta
import requests
from flask_mail import Mail, Message
from app import mail
from flask import render_template
from bson import json_util
from bson.objectid import ObjectId
from config import (
    USR_MONGO_COLLECTION, 
    USR_TEMP_TOKEN_MONGO_COLLECTION, 
    USR_KEY_MONGO_COLLECTION, 
    MAX_API_KEY, 
    USR_MONGO_PROCESS_COLLECTION, 
    SPECIAL_CHARS, 
    BHAHSINI_GLOSSARY_CREATE_URL, 
    BHAHSINI_GLOSSARY_DELETE_URL, 
    BHAHSINI_GLOSSARY_FETCH_URL, 
    BHAHSINI_SPEAKER_ENROLL_CREATE_URL, 
    BHAHSINI_SPEAKER_VERIFICATION_URL, 
    BHAHSINI_SPEAKER_FETCH_URL, 
    BHAHSINI_SPEAKER_DELETE_URL
    )
from config import SENDER_EMAIL, SENDER_PASSWORD, SENDER_USERNAME
from Crypto.Cipher import AES
import base64
import sys
import os
import logging


log = logging.getLogger('file')

SECRET_KEY          =   secrets.token_bytes()

role_codes_filepath =   config.ROLE_CODES_URL
json_file_dir       =   config.ROLE_CODES_DIR_PATH
json_file_name      =   config.ROLE_CODES_FILE_NAME

mail_server         =   config.MAIL_SENDER
mail_ui_link        =   config.BASE_URL
reset_pwd_link      =   config.RESET_PWD_ENDPOINT
token_life          =   config.AUTH_TOKEN_EXPIRY_HRS
verify_mail_expiry  =   config.USER_VERIFY_LINK_EXPIRY
apikey_expiry       =   config.USER_API_KEY_EXPIRY  
role_codes          =   []
role_details        =   []

class OutboxEmptyException(Exception):
    "Unable to send verification mail. Please try again later"
    pass

class UserUtils:

    @staticmethod
    def generate_user_id():
        """UUID generation."""

        return(uuid.uuid4().hex)


    @staticmethod
    def generate_user_api_key():
        eventId = datetime.now().strftime('%S') + str(uuid4())
        return eventId

#take timestamp epoch and add/generate uid based on this time. 38 character
#by default apiKey will be 1. Maximum apiKey should be 5. if list has only 1 value, we should be able to call generateApiKey. If user has 5 apiKey already existed=>reached max apiKey

    @staticmethod
    def check_appName(string):
        FLAG = None
        FLAG_ = None
        for strr in string:
            if strr.isdigit() or strr in SPECIAL_CHARS or strr.isupper():
                FLAG = True
            else:
                FLAG_ = False
        if FLAG:
            return True
        elif not FLAG_:
            return False

    @staticmethod
    def validate_email_format(email):
        """Email validation

        Max length check
        Regex validation for emails.
        """
        if len(email) > config.EMAIL_MAX_LENGTH:
            return False
        regex = '([a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+)'
        if (re.search(regex, email)):
            return True
        else:
            return False


    @staticmethod
    def hash_password(password):
        """Password hashing using bcrypt."""

        # converting str to byte before hashing
        password_in_byte    = bytes(password, 'utf-8')  
        salt                = bcrypt.gensalt()
        return(bcrypt.hashpw(password_in_byte, salt))


    @staticmethod
    def validate_password(password):
        """Password rule check

        Minimum x chars long,as defined by 'MIN_LENGTH' on config,
        Must contain upper,lower,number and a special character.
        """

        if len(password) < config.PWD_MIN_LENGTH:
            return post_error("Invalid password", "password should be minimum {} characteristics long".format(config.PWD_MIN_LENGTH), None)
        if len(password) > config.PWD_MAX_LENGTH:
            return post_error("Invalid password", "password cannot exceed {} characteristics long".format(config.PWD_MAX_LENGTH), None)
        regex   = ("^(?=.*[a-z])(?=." + "*[A-Z])(?=.*\\d)" +
                 "(?=.*[-+_!@#$%^&*., ?]).+$")
        pattern = re.compile(regex)
        if re.search(pattern, password) is None:
            return post_error("Invalid password", "password must contain atleast one uppercase,one lowercase, one numeric and one special character", None)


    @staticmethod
    def validate_phone(phone):
        """Phone number validation
        
        10 digits, starts with 6,7,8 or 9.
        """
        pattern = re.compile("(0/91)?[6-9][0-9]{9}")
        if (pattern.match(phone)) and len(phone) == 10:
            return True
        else:
            return False

    @staticmethod
    def email_availability(email):
        """Email validation
        
        Verifying whether the email is already taken or not
        """
        try:
            #connecting to mongo instance/collection
            collections = db.get_db()[USR_MONGO_COLLECTION]  
            #searching username with verification status = True 
            #Ignore case sensitivity.
            regex_case_ignored = re.compile('^'+email+'$', re.IGNORECASE)
            user_record = collections.find({"email": {"$regex":regex_case_ignored}}) 
            for usr in user_record:
                log.info(f"user record : {user_record}")
            if user_record.count() != 0:
                for usr in user_record:
                    if usr["isVerified"] == True:
                        log.info("Email is already taken")
                        return post_error("Data not valid", "This email address is already registered with ULCA. Please use another email address.", None)
                    register_time = usr["registeredTime"]
                    #checking whether verfication link had expired or not
                    if (datetime.utcnow() - register_time) < timedelta(hours=verify_mail_expiry):
                        log.exception("{} already did a signup, asking them to revisit their mail for verification".format(email))
                        return post_error("Data Not valid","This email address is already registered with ULCA. Please click on the verification link sent on your email address to complete the verification process.",None)
                    elif (datetime.utcnow() - register_time) > timedelta(hours=verify_mail_expiry):
                        #Removing old records if any
                        collections.delete_many({"email": email})
                    
            log.info("Email is not already taken, validated")
        except Exception as e:
            log.exception("Database connection exception |{}".format(str(e)))
            return post_error("Database exception", "An error occurred while working on the database:{}".format(str(e)), None)

    @staticmethod
    def validate_rolecodes(roles):
        """Role Validation

        roles should match roles defined on ULCA system,
        pre-defined roles are read from zuul (git) configs.
        """

        global role_codes
        global role_details
        if not role_codes:
            log.info("Reading roles from remote location")
            role_codes = UserUtils.read_role_codes()
        log.info(role_codes)
        for role in roles:
            try:
                if role not in role_codes:
                    log.info(f"Role --{role} is not a valid role ")
                    return False
            except Exception:
                return post_error("Roles missing","No roles are read from json,empty json or invalid path",None)


    @staticmethod
    def generate_api_keys(email):
        """Issuing new API key

        Generating public and private keys,
        storing them on redis,
        returning it back to users after successful storage.
        """
        
        try: 
            #creating payload for API Key storage
            key_payload = {"email": email, "publicKey":str(uuid.uuid4()), "privateKey": uuid.uuid4().hex, "createdOn": datetime.utcnow()}
            log.info("New API key issued for {}".format(email), MODULE_CONTEXT) 
            #connecting to mongo instance/collection
            collections = db.get_db()[USR_KEY_MONGO_COLLECTION]
            #inserting api-key records on db
            collections.insert(key_payload)
            del key_payload["_id"]
            del key_payload["createdOn"]
            return key_payload

        except Exception as e:
            log.exception("Database exception | {}".format(str(e)))
            return post_error("Database connection exception", "An error occurred while connecting to database :{}".format(str(e)), None)


    @staticmethod
    def token_validation(token):
        """Auth-token Validation for auth-token-search
        
        matching with existing active tokens on database,
        decoding the token,
        updating user token status on database in case of token expiry.
        """
        try:
            #connecting to mongo instance/collection
            collections = db.get_db()[USR_TOKEN_MONGO_COLLECTION]  
            #searching for token from database    
            result = collections.find({"token": token},{"_id": 0, "user": 1, "active": 1, "secret_key": 1})
            if result.count() == 0:
                log.info("No token found matching the auth-token-search request")
                return post_error("Invalid token", "Token received is not matching", None)
            for value in result:
                #checking for token status = False 
                if value["active"] == False:
                    log.info("Token on auth-token-search request has expired")
                    return post_error("Invalid token", "Token has expired", None)
                #checking for token status = True 
                if value["active"] == True:
                    secret_key = value["secret_key"]
                    #token decoding
                    try:
                        jwt.decode(token, secret_key, algorithm='HS256')
                    except jwt.exceptions.ExpiredSignatureError as e:
                        log.exception("Auth-token expired, time limit exceeded",  MODULE_CONTEXT, e)
                        #updating user token status on collection
                        collections.update({"token": token}, {"$set": {"active": False}})
                        return post_error("Invalid token", "Token has expired", None)
                    except Exception as e:
                        log.exception("Auth-token expired, jwt decoding failed",  MODULE_CONTEXT, e)
                        return post_error("Invalid token", "Not a valid token", None)
        except Exception as e:
            log.exception("Database connection exception ",  MODULE_CONTEXT, e)
            return post_error("Database connection exception", "An error occurred while connecting to the database", None)


    @staticmethod
    def get_user_from_token(token,temp):
        """Fetching user details from token"""

        try: 
            #for password resetting
            if temp:
                document = USR_TEMP_TOKEN_MONGO_COLLECTION 
                #connecting to mongo instance/collection
                collections = db.get_db()[document] 
                #searching for database record matching token, getting user_name
                result = collections.find({"token": token}, {"_id": 0, "user": 1})
                if result.count() == 0:
                    return post_error("Invalid token", "Token received is not matching", None)
                for record in result:
                    username = record["user"]  
            else:
                #connecting to redis instance
                redis_client = get_redis()
                key_data     = redis_client.get(token)
                usr_payload  = json.loads(key_data.decode("utf-8"))

        except Exception as e:
            log.exception("db connection exception ",  MODULE_CONTEXT, e)
            return post_error("Database connection exception", "An error occurred while connecting to the database", None)
        try:
            #connecting to mongo instance/collection
            collections_usr = db.get_db()[USR_MONGO_COLLECTION]
            #searching for database record matching username
            result_usr = collections_usr.find({"email": usr_payload["email"],"is_verified":True}, {"_id": 0, "password": 0})
            for record in result_usr:
                #checking active status of user
                if record["is_active"] == False:
                    log.info("{} is not an active user".format(username),MODULE_CONTEXT)
                    return post_error("Not active", "This operation is not allowed for an inactive user", None)
                return record
        except Exception as e:
            log.exception("db connection exception ",  MODULE_CONTEXT, e)
            return post_error("Database connection exception", "An error occurred while connecting to the database", None)


    @staticmethod
    def get_data_from_keybase(value,keys=None,email=None):
        try: 
            
            #connecting to mongo instance/collection
            collections = db.get_db()[USR_KEY_MONGO_COLLECTION] 
            if keys:
                result = collections.find({"email":value},{"email":1,"publicKey":1,"privateKey":1,"_id":0})
                if result.count() ==0:
                    return None
                for key in result:
                    return key  
            if email:
                result = collections.find({"publicKey":value},{"email":1,"privateKey":1,"createdOn":1,"_id":0})
                if result.count() ==0:
                    log.info("No data found matching the request")
                    return post_error("Invalid key", "key received is invalid", None)
                for key in result:
                    if ((datetime.utcnow() - key["createdOn"]) > timedelta(days=apikey_expiry)):
                        log.info("Keys expired for user : {}".format(key["email"]))
                        #removing keys since they had expired
                        collections.remove({"publicKey":value})
                        return post_error("Invalid key", "key has expired", None)
                    else:
                        return key

        except Exception as e:
            log.exception("db connection exception | {}".format(str(e)))
            return post_error("Database connection exception", "An error occurred while connecting to the database", None)
        

    @staticmethod
    def get_token(user_name):
        """Token Retrieval for login 
        
        fetching token for the desired user_name,
        validating the token(user_name matching, expiry check),
        updating the token status on db if the token had expired.
        """
        try:
            #connecting to mongo instance/collection
            collections = db.get_db()[USR_TOKEN_MONGO_COLLECTION] 
            #searching for token against the user_name
            record = collections.find({"user": user_name, "active": True}, {"_id": 0, "token": 1, "secret_key": 1})
            if record.count() == 0:
                log_info("No active token found for:{}".format(user_name), MODULE_CONTEXT)
                return {"status": False, "data": None}
            else:
                for value in record:
                    secret_key = value["secret_key"]
                    token = value["token"]
                    try:
                        #decoding the jwt token using secret-key
                        jwt.decode(token, secret_key, algorithm='HS256')
                        log_info("Token validated for:{}".format(user_name), MODULE_CONTEXT)
                        return({"status": True, "data": token})
                    #expired-signature error
                    except jwt.exceptions.ExpiredSignatureError as e:
                        log_exception("Token for {} had expired, exceeded the timeLimit".format(user_name),  MODULE_CONTEXT, e)
                        #updating token status on token-collection
                        collections.update({"token": token}, { "$set": {"active": False}})
                        return({"status": False, "data": None})
                    #falsy token error
                    except Exception as e:
                        log_exception("Token not valid for {}".format(user_name),  MODULE_CONTEXT, e)
                        return({"status": False, "data": None})
        except Exception as e:
            log.exception("Database connection exception ",  MODULE_CONTEXT, e)
            return({"status": "Database connection exception", "data": None})


    @staticmethod
    def validate_user_input_creation(user):
        """Validating user creation inputs.

        -Mandatory key checks
        -Email Validation
        -Password Validation 
        """
        obj_keys = {'firstName','email','password','roles'}
        for key in obj_keys:
            if (user.get(key) == None) :
                    log.info("Mandatory key checks failed")
                    return post_error("Data Missing","firstName,email,roles and password are mandatory for user creation",None)
        log.info("Mandatory key checks successful")

        if len(user["firstName"]) > config.NAME_MAX_LENGTH:
            return post_error("Invalid data", "firstName given is too long", None)
        email_availability_status = UserUtils.email_availability(user["email"])
        if email_availability_status is not None:
            log.info("Email validation failed, already taken")
            return email_availability_status
        if (UserUtils.validate_email_format(user["email"]) == False):
            log.info("Email validation failed")
            return post_error("Data not valid", "Email given is not valid", None)
        log.info("Email  validated")

        password_validity = UserUtils.validate_password(user["password"])
        if password_validity is not None:
            log.info("Password validation failed")
            return password_validity
        log.info("Password validated")

        if user.get("roles") != None:
            rolecodes = user["roles"]
            if UserUtils.validate_rolecodes(rolecodes) == False:
                log.info("Role validation failed")
                return post_error("Invalid data", "Rolecode given is not valid", None) 
            log.info("Role/s validated")

        if user.get("phoneNo") != None:
            phone_validity = UserUtils.validate_phone(user["phoneNo"])          
            if phone_validity is False:
                return post_error("Data not valid", "Phone number given is not valid", None)
            log.info("Phone number  validated")

        # if user.get("orgID") != None:
        #     org_validity =OrgUtils.validate_org(str(user["orgID"]).upper())
        #     if org_validity is not None:
        #         log.info("Org validation failed")
        #         return org_validity
        #     log.info("Org validated")




    @staticmethod
    def validate_user_input_updation(user):
        """Validating user updation inputs.

        -Mandatory key checks
        -User Id Validation
        -Email Validation
        -Org Validation
        -Role Validation
        -Model Validation 
        """
        global role_details
        if user.get("email") == None:
            return post_error("Data Missing", "email not found", None)
        email = user["email"]
        try:
            #connecting to mongo instance/collection
            collections = db.get_db()[USR_MONGO_COLLECTION]
            #searching User Id with verification status = True 
            record = collections.find({'email': email,"isVerified":True})
            if record.count() == 0:
                log.info("User Id validation failed, no such verified user")
                return post_error("Data not valid", "No such verified user with the given email", None)
            for value in record:
                if value["isActive"]== False:
                    log.info("User Id validation failed,inactive user")
                    return post_error("Not active", "User account is inactive", None)
            log.info("User Id validation successful")          
        except Exception as e:
            log.exception(f"Database connection exception {e}")
            return post_error("Database connection exception", "An error occurred while connecting to the database:{}".format(str(e)), None)

        if user.get("roles") != None:
            rolecodes = user["roles"]
            if not isinstance(rolecodes,list):
                return post_error("Invalid data", "roles should be a list of values", None)
            role_validity = UserUtils.validate_rolecodes(rolecodes) 
            if role_validity == False:
                log.info("Role validation failed")
                return post_error("Invalid data", "Rolecode given is not valid", None)

        # if user.get("orgID") != None:
            #validate org
        if user.get("firstName") != None:
            if len(user["firstName"]) > config.NAME_MAX_LENGTH:
                return post_error("Invalid data", "firstName given is too long", None)

        if user.get("phoneNo") != None:
            phone_validity = UserUtils.validate_phone(user["phoneNo"])          
            if phone_validity is False:
                return post_error("Data not valid", "Phone number given is not valid", None)
            log.info("Phone number  validated")
        
        if user.get("password")!=None:
            password_validity = UserUtils.validate_password(user["password"])
            if password_validity is not None:
                log.info("Password validation failed")
                return password_validity
            log.info("Password validated")
        
        # if user.get("orgID") != None:
        #     org_validity =OrgUtils.validate_org(str(user["orgID"]).upper())
        #     if org_validity is not None:
        #         log.info("Org validation failed")
        #         return org_validity
        #     log.info("Org validated")
        


    @staticmethod
    def validate_user_login_input(user_email, password):
        """User credentials validation
        
        checking whether the user is verified and active,
        password matching.
        """

        try:
            #connecting to mongo instance/collection
            collections = db.get_db()[USR_MONGO_COLLECTION]
            #fetching the user details from db
            result = collections.find({'email': user_email}, {
                'password': 1, '_id': 0,'isActive':1,'isVerified':1})
            if result.count() == 0:
                log.info("{} is not a verified user".format(user_email))
                return post_error("Not verified", "This email address is not registered with ULCA. Please sign up.", None)
            for value in result:
                log.info(f"Result from user search :: {value}")
                if value["isVerified"]== False:
                    log.info("{} is not a verified user".format(user_email))
                    return post_error("Not active", "User account is not verified. Please click on the verification link sent on your email address to complete the verification process.", None)
                if value["isActive"]== False:
                    log.info("{} is not an active user".format(user_email))
                    return post_error("Not active", "User account is inactive", None)
                password_in_db = value["password"].encode("utf-8")
                try:
                    if bcrypt.checkpw(password.encode("utf-8"), password_in_db)== False:
                        log.info("Password validation failed for {}".format(user_email))
                        return post_error("Invalid Credentials", "Incorrect username or password", None)
                except Exception as e:
                    log.exception(f"exception while decoding password : {e}")
                    return post_error("exception while decoding password", "exception:{}".format(str(e)), None)                   
        except Exception as e:
            log.exception("Exception while validating email and password for login"+str(e))
            return post_error("Database exception","Exception occurred:{}".format(str(e)),None)


    @staticmethod
    def read_role_codes():
        """Reading roles from git config."""
        
        try:
            file = requests.get(role_codes_filepath, allow_redirects=True)
            file_path = json_file_dir + json_file_name
            open(file_path, 'wb').write(file.content)
            log.info("Roles data read from git and pushed to local" )
            with open(file_path, 'r') as stream:
                parsed = json.load(stream)
                roles = parsed['roles']
                rolecodes = []
                for role in roles:
                    if role["active"]:
                        rolecodes.append(role["code"])
            return rolecodes
        except Exception as exc:
            log.exception("Exception while reading roles: " +str(exc))
            return post_error("CONFIG_READ_ERROR",
                       "Exception while reading roles: " + str(exc))


    @staticmethod
    def generate_email_notification(user_records,task_id):

        for user_record in user_records:
            email       =   user_record["email"]
            timestamp   =   eval(str(time.time()).replace('.', '')[0:13])
            name        =   None
            user_id     =   None
            pubKey      =   None
            pvtKey      =   None
            link        =   None

            if task_id == EnumVals.VerificationTaskId.value:
                email_subject   =   EnumVals.VerificationSubject.value
                template        =   'usr_verification.html' 
                user_id         =   user_record["userID"]
                link            =   mail_ui_link+"activate/{}/{}/{}".format(email,user_id,timestamp)

            if task_id == EnumVals.ConfirmationTaskId.value:
                email_subject   =   EnumVals.ConfirmationSubject.value
                template        =   'usr_confirm_registration.html'
                name            =   user_record["name"]

            if task_id == EnumVals.ForgotPwdTaskId.value:
                email_subject   =   EnumVals.ForgotPwdSubject.value
                template        =   'reset_pwd_mail_template.html'
                pubKey          =   user_record["pubKey"]
                pvtKey          =   user_record["pvtKey"]
                link            =   mail_ui_link+reset_pwd_link+"{}/{}/{}/{}".format(email,pubKey,pvtKey,timestamp)
                name            =   user_record["name"]
            try:
                
                msg = MIMEMultipart()
                msg['From'] = SENDER_EMAIL
                msg['To'] = email
                msg['Subject'] = email_subject
                    
                message_html = render_template(template,ui_link=mail_ui_link,activity_link=link,user_name=name)

                # Attach the message to the email
                msg.attach(MIMEText(message_html, 'html'))

                # # Attach the file if provided
                # attachment_filename = "feedback.xlsx"
                # attachment = open(attachment_filename, "rb")
                # part = MIMEBase('application', 'octet-stream')
                # part.set_payload(attachment.read())
                # encoders.encode_base64(part)
                # part.add_header('Content-Disposition', f"attachment; filename= {attachment_filename}")
                # msg.attach(part)

                # Connect to the SMTP server
                with smtplib.SMTP('smtp.azurecomm.net', 587) as server:
                    server.starttls()
                    print(f"SENDER USERNAME :: {SENDER_USERNAME}")
                    print(f"SENDER PASSWORD :: {SENDER_PASSWORD}")
                    server.login(SENDER_USERNAME, SENDER_PASSWORD)
                    response = server.sendmail(SENDER_EMAIL, email, msg.as_string())
                    print("Email sent")
                            
                
                
                # Older email code                
                # msg = Message(subject=email_subject,sender=mail_server,recipients=[email])
                # msg.html = render_template(template,ui_link=mail_ui_link,activity_link=link,user_name=name)
                # with mail.record_messages() as outbox:
                #     mail.send(msg)
                #     log.info("Email outbox size {outboxLength} and subject {emailSubject}".format(outboxLength=len(outbox),emailSubject=outbox[0].subject))
                #     if len(outbox) < 1:
                #         raise OutboxEmptyException
                #     log.info("Generated email notification for {} ".format(email), MODULE_CONTEXT)

            except Exception as e:
                log.exception("Exception while generating email notification | {}".format(str(e)))
                return post_error("Exception while generating email notification","Exception occurred:{}".format(str(e)),None)
            
    @staticmethod
    def validate_username(user_email):
        """Validating userName/Email"""

        try:
            #connecting to mongo instance/collection
            collections = db.get_db()[USR_MONGO_COLLECTION]
            #searching for record matching user_name
            valid = collections.find({"email":user_email,"isVerified":True})
            if valid.count() == 0:
                log.info("Not a valid email/username")
                return post_error("Not Valid","This email address is not registered with ULCA",None)#Given email is not associated with any of the active ULCA accounts
            for value in valid:
                if value["isActive"]== False:
                    log.info("Given email/username is inactive")
                    return post_error("Not active", "User account is inactive", None)
        except Exception as e:
            log.exception("exception while validating username/email"+str(e),  MODULE_CONTEXT, e)
            return post_error("Database exception","Exception occurred:{}".format(str(e)),None)


    @staticmethod
    def get_user_api_keys(userId,appName):
        try:
            coll = db.get_db()[USR_MONGO_COLLECTION]
            response = coll.find_one({"userID": userId})
            dupStatus = True
            dupAppName = []
            if appName == None:
                if isinstance(response,dict):
                    if 'apiKeyDetails' in response.keys() and isinstance(response['apiKeyDetails'],list):
                        return response['apiKeyDetails'] #Return the list of user api keys
                    else:
                        return [] #If user doesn't have any api keys
                else:
                    log.info("Not a valid userId")
                    return post_error("Not Valid","This userId address is not registered with ULCA",None)
            if appName != None:
                if isinstance(response,dict):
                    if not 'apiKeyDetails' in response.keys():
                        return [],False
                    if 'apiKeyDetails' in response.keys() and isinstance(response['apiKeyDetails'],list):
                        for appN in response["apiKeyDetails"]:
                            dupAppName.append(appN["appName"])
                        if appName in dupAppName:
                            return post_error("Not Valid","This appName is already in use, please try by changing appName",None) , dupStatus
                        elif appName not in dupAppName:
                            dupStatus = False
                            return response['apiKeyDetails'], dupStatus#,dupStatus #Return the list of user api keys
                    else:
                        return [], dupStatus #If user doesn't have any api keys
                else:
                    log.info("Not a valid userId")
                    return post_error("Not Valid","This userId address is not registered with ULCA",None), dupStatus
        except Exception as e:
            log.exception("Not a valid userId")
            return post_error("error processing ULCA userId:{}".format(str(e)),None)

    @staticmethod
    def get_email_api_keys(email,appName):
        try:
            coll = db.get_db()[USR_MONGO_COLLECTION]
            response = coll.find_one({"email": email})
            dupStatus = True
            dupAppName = []
            if appName == None:
                if isinstance(response,dict):
                    if 'apiKeyDetails' in response.keys() and isinstance(response['apiKeyDetails'],list):
                        return response #Return the list of user api keys
                    else:
                        return [] #If user doesn't have any api keys
                else:
                    log.info("Not a valid userId")
                    return post_error("Not Valid","This userId address is not registered with ULCA",None)
            if appName != None:
                if isinstance(response,dict):
                    if not 'apiKeyDetails' in response.keys():
                        return [],False
                    if 'apiKeyDetails' in response.keys() and isinstance(response['apiKeyDetails'],list):
                        for appN in response["apiKeyDetails"]:
                            dupAppName.append(appN["appName"])
                        if appName in dupAppName:
                            return post_error("Not Valid","This appName is already in use, please try by changing appName",None) , dupStatus
                        elif appName not in dupAppName:
                            dupStatus = False
                            return response, dupStatus#,dupStatus #Return the list of user api keys
                    else:
                        return [], dupStatus #If user doesn't have any api keys
                else:
                    log.info("Not a valid userId")
                    return post_error("Not Valid","This userId address is not registered with ULCA",None), dupStatus
        except Exception as e:
            log.exception("Not a valid userId")
            return post_error("error processing ULCA userId:{}".format(str(e)),None)

    @staticmethod
    def insert_generated_user_api_key(user,appName,apikey,serviceProviderKey):
        collection = db.get_db()[USR_MONGO_COLLECTION]
        # if len(serviceProviderKey) == 0:
        #     spk = []
        # elif len(serviceProviderKey) >1:
        #     spk = serviceProviderKey
        details = collection.update({"userID":user}, {"$push": {"apiKeyDetails":{"appName":appName,"ulcaApiKey":apikey,"createdTimestamp":str(int(time.time())),"serviceProviderKeys":serviceProviderKey}}})
        return details


    @staticmethod
    def revoke_userApiKey(userid, userapikey):
        collection = db.get_db()[USR_MONGO_COLLECTION]
        log.info(f"userapikey {userapikey}")
        revoke = collection.update({"userID":userid}, {"$pull":{"apiKeyDetails": {"ulcaApiKey" : userapikey.replace(" ","")}}})
        # log.info(revoke)
        return json.loads(json_util.dumps(revoke))


    @staticmethod
    def get_userDoc(userID):
        collection = db.get_db()[USR_MONGO_COLLECTION]
        userdoc = collection.find_one({"userID" : userID})#, "apiKeyDetails.serviceProviderKeys.serviceProviderName" : serviceName })
        #log.info(f"userdoc  231231 {userdoc}")
        if userdoc:
            if "apiKeyDetails" in userdoc.keys():
                if len(userdoc["apiKeyDetails"]) >= 1:

                    apiKeyDeets = userdoc["apiKeyDetails"]
                    return apiKeyDeets , userdoc["email"]
                return None, None
            return None, None
        elif not userdoc:
            return None, None

    @staticmethod
    def get_pipelineId(pipelineID):
        collections = db.get_process_db()[USR_MONGO_PROCESS_COLLECTION]
        pipeL = collections.find_one({"_id" : ObjectId(pipelineID)})
        if pipeL:
            return pipeL 
        elif not pipeL:
            return None
    #Get pipelineId based on serviceProviderName
    @staticmethod
    def get_pipelineIdbyServiceProviderName(serviceProviderName):
        collections = db.get_process_db()[USR_MONGO_PROCESS_COLLECTION]
        pipeL = collections.find_one({"serviceProvider.name" : serviceProviderName})
        if pipeL:
            return pipeL 
        elif not pipeL:
            return None

    @staticmethod
    def decryptAes(secreKey,source):
        """
        Input encrypted bytes, return decrypted bytes, using iv and key
        """
        decryptedMasterKeysList = []
        decryptedMasterKeysDict = {}
        #log.info(source)
        if source and isinstance(source,list):
            for decrp in range(2):
                
                byte_array = base64.b64decode(source[decrp])
                iv = byte_array[0:16] # extract the 16-byte initialization vector
                messagebytes = byte_array[16:] # encrypted message is the bit after the iv
                cipher = AES.new(secreKey.encode("UTF-8"), AES.MODE_CBC, iv )
                decrypted_padded = cipher.decrypt(messagebytes)
                last_byte = decrypted_padded[-1]
                decrypted = decrypted_padded[0:-last_byte]
                decryptedMasterKeysList.append(decrypted.decode("UTF-8"))

            decryptedMasterKeysDict[decryptedMasterKeysList[0]] = decryptedMasterKeysList[1]
        return decryptedMasterKeysDict


    @staticmethod
    def get_service_provider_keys(email, appName,EndPointurl,decryptedValues, dataTracking):
        body = {"emailId" : email.lower(), "appName" : appName, "dataTracking": dataTracking}
        log.info("Get Service Provider Key Api Call URL"+str(EndPointurl))
        log.info("Get Service Provider Key Api Call Request"+str(body))
        result = requests.post(url=EndPointurl, json=body, headers=decryptedValues)
        log.info("Get Service Provider Key Api Call Response"+str(result.json()))
        #log.info(result.json())
        return result.json()

    @staticmethod
    def revoke_service_provider_keys(email, appName,EndPointurl,decryptedValues):
        body = {"emailId" : email.lower(), "appName" : appName}
        log.info("Revoke Service Provider Key Api Call URL"+str(EndPointurl))
        log.info("Revoke Service Provider Key Api Call Request"+str(body))
        result = requests.delete(url=EndPointurl, json=body, headers=decryptedValues)
        log.info(f"Revoked Response :: {result.status_code} :: {result.json()}")   
        log.info("Revoke Service Provider Key Api Call Response"+str(result.json()))
        #log.info(result.json())
        return result.json()

    @staticmethod
    def pushServiceProvider(generatedApiKeys,ulcaApiKey,userServiceProviderName, dataTracking):
        collections = db.get_db()[USR_MONGO_COLLECTION]
        updateDoc = collections.update({"apiKeyDetails.ulcaApiKey":ulcaApiKey},{"$push":{"apiKeyDetails.$.serviceProviderKeys":{"serviceProviderName":userServiceProviderName,"dataTracking":dataTracking,"inferenceApiKey":generatedApiKeys}}})
        servProvKe = {"serviceProviderKeys":[{"serviceProviderName":userServiceProviderName,"dataTracking": dataTracking,"inferenceApiKey":generatedApiKeys}]}
        return json.loads(json_util.dumps(updateDoc)), servProvKe

    @staticmethod
    def removeServiceProviders(userID,ulcaApiKey,serviceProviderName):
        collections = db.get_db()[USR_MONGO_COLLECTION]
        deleteDoc = collections.update({"userID":userID,"apiKeyDetails.ulcaApiKey":ulcaApiKey},{"$pull":{"apiKeyDetails.$.serviceProviderKeys":{"serviceProviderName":serviceProviderName}}})
        return json.loads(json_util.dumps(deleteDoc))

    @staticmethod
    def updateDataTrackingValuePull(userID, ulcaApiKey, serviceProviderName, dataTrackingVal):
        collections = db.get_db()[USR_MONGO_COLLECTION]
        query_to_update = {"userID":userID,"apiKeyDetails.ulcaApiKey":ulcaApiKey,"apiKeyDetails.serviceProviderKeys.serviceProviderName":serviceProviderName}
        update = {"$set":{"apiKeyDetails.$[].serviceProviderKeys.$[elem].dataTracking": dataTrackingVal}}
        array_filters = [{"elem.serviceProviderName": serviceProviderName}]
        collectUpdate = collections.update_one(query_to_update, update, array_filters= array_filters)
        return collectUpdate.matched_count, collectUpdate.modified_count


    @staticmethod
    def getUserEmail(userID, ulcaAK):
        collections = db.get_db()[USR_MONGO_COLLECTION]
        email = collections.find_one({"userID":userID})
        if email:
            if len(email['apiKeyDetails']) >= 1:
                for appName in email['apiKeyDetails']:
                    if appName['ulcaApiKey'] == ulcaAK:
                        ulcaAppN = appName['appName']
                        return email['email'], ulcaAppN
        elif not email:
            return None, None

    @staticmethod
    def getPipelinefromSrvcPN(spn):
        collection = db.get_process_db()[USR_MONGO_PROCESS_COLLECTION]
        pipeline = collection.find_one({"serviceProvider.name":spn})
        return pipeline


    @staticmethod
    def getDataTrackingKey(userID, ulcaApiKey):
        collections = db.get_db()[USR_MONGO_COLLECTION]
        record = collections.find({"userID":userID, "apiKeyDetails.ulcaApiKey": ulcaApiKey})
        for rec in record:
            log.info(f"record output of dataTracking {rec}")
        return rec
    
    @staticmethod
    def listOfServiceProviders():
        collection = db.get_process_db()[USR_MONGO_PROCESS_COLLECTION]
        pipelinie_Docs = list(collection.find({"status":"published"}))        
        if not pipelinie_Docs:
            return None
        if isinstance(pipelinie_Docs, list):
            return pipelinie_Docs[0]['serviceProvider']['name']

    @staticmethod
    def getUserInfKey(appName, userID, serviceProvider):
        collections = db.get_db()[USR_MONGO_COLLECTION]
        userinfkey = collections.find_one({"userID":userID})
        if not userinfkey:
            return None
        
        if "apiKeyDetails" in userinfkey.keys():
            for apiK in userinfkey['apiKeyDetails']:
                if apiK['appName'] == appName and 'serviceProviderKeys' in apiK.keys():
                    for service in apiK['serviceProviderKeys']:
                        if service['serviceProviderName'] == serviceProvider:
                            return service['inferenceApiKey']['value']
        else:
            return None
        
    @staticmethod
    def send_create_req_for_dhruva(auth_headers,glossary):
        body = {"glossary":glossary}
        result = requests.post(url=BHAHSINI_GLOSSARY_CREATE_URL, json=body, headers=auth_headers)
        log.info(f"result from dhruva {result.json}")
        print(f"RESPONSE :: {result.json()} and STATUS CODE :: {result.status_code}")
        return result.json(), result.status_code
    
    @staticmethod
    def send_delete_req_for_dhruva(auth_headers,glossary):
        body = {"glossary":glossary}
        result = requests.post(url=BHAHSINI_GLOSSARY_DELETE_URL, json=body, headers=auth_headers)
        # if result.status_code == 200:
        #     return result.json()
        print(f"RESPONSE :: {result.json()} and STATUS CODE :: {result.status_code}")
        return result.json(), result.status_code
    
    @staticmethod
    def send_fetch_req_for_dhruva(auth_headers):
        #body = {"apiKey":infKey}      
        result = requests.get(url=BHAHSINI_GLOSSARY_FETCH_URL,  headers=auth_headers)
        print(f"RESPONSE :: {result.json()} and STATUS CODE :: {result.status_code}")
        return result.json(), result.status_code
    
    @staticmethod
    def send_speaker_enroll_for_dhruva(userinferenceApiKey, request_body):
        headers = {
        'Authorization': userinferenceApiKey,
        'Content-Type': 'application/json'
        }
        result = requests.post(url=BHAHSINI_SPEAKER_ENROLL_CREATE_URL, json=request_body, headers=headers)
        return result.json(), 200
        

    @staticmethod
    def send_speaker_verify_for_dhruva(userinferenceApiKey, request_body):
        headers = {
        'Authorization': userinferenceApiKey,
        'Content-Type': 'application/json'
        }
        result = requests.post(url=BHAHSINI_SPEAKER_VERIFICATION_URL, json=request_body, headers=headers)
        return result.json(), result.status_code

    @staticmethod
    def send_speaker_fetchall_for_dhruva(userinferenceApiKey):

        headers = {
        'Authorization': userinferenceApiKey,
        'Content-Type': 'application/json'
        }
        result = requests.get(url=BHAHSINI_SPEAKER_FETCH_URL, headers=headers)
        return result.json(), result.status_code
    
    @staticmethod
    def send_speaker_delete_for_dhruva(userinferenceApiKey, request_body):

        headers = {
        'Authorization': userinferenceApiKey,
        'Content-Type': 'application/json'
        }
        result = requests.delete(url=BHAHSINI_SPEAKER_DELETE_URL, json=request_body, headers=headers)
        return result.json(), result.status_code
        
