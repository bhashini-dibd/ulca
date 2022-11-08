from db import get_db
from utilities import UserUtils,EnumVals, normalize_bson_to_json
from .response import post_error
from config import USR_MONGO_COLLECTION,ADMIN_ROLE_KEY, USR_KEY_MONGO_COLLECTION
import time
import logging

log = logging.getLogger('file')
admin_role_key          =   ADMIN_ROLE_KEY

class UserManagementModel(object):

    def create_users(self,records):
        """Inserting user records to the databse"""

        try:
            #connecting to mongo instance/collection
            collections = get_db()[USR_MONGO_COLLECTION]
            #inserting user records on db
            results = collections.insert(records)
            log.info("Count of users created : {}".format(len(results)))
            if len(records) != len(results):
                return post_error("Database exception", "Some of the users were not created due to databse error", None)
            #email notification for registered users
            user_notified   =  UserUtils.generate_email_notification(records,EnumVals.VerificationTaskId.value) 
            if user_notified is not None:
                return user_notified
        except Exception as e:
            log.exception("Database connection exception {}".format(str(e)))
            return post_error("Database  exception", "An error occurred while processing on the db :{}".format(str(e)), None)


    def update_users_by_uid(self,users,user_id):
        """Updating user records in the database"""

        try:
            for i,user in enumerate(users):
                #connecting to mongo instance/collection
                collections = get_db()[USR_MONGO_COLLECTION]
                record = collections.find({"userID": user_id})
                if record.count() != 0:
                    log.info("Record found matching the userID {}".format(user_id))
                    for rec in record:
                        #fetching the user roles
                        roles=rec["roles"] 
                        #fetching user name
                        email=rec["email"]

                    #verifying the requested person, both admin and user can reset password   
                    if (admin_role_key in roles) or (email == user["email"]):
                        log.info("Reset password request is checked against role permission and username")
                        #updating user record
                        results = collections.update({"email": user["email"]}, {'$set': user})

                        key_collection = get_db()[USR_KEY_MONGO_COLLECTION]
                        # removing API keys from user record
                        key_collection.remove({"email":user["email"]})

                    if 'writeError' in list(results.keys()):
                        log.info("User{} updation failed due to {}".format((i+1),str(results)))
                        return post_error("Database error", "some of the records where not updated", None)
                    log.info("User{} updated".format(i+1))
                
                else:
                    log.info("No record found matching the userID {}".format(user_id))
                    return post_error("Data Not valid","You are not authorized to update these details",None)
        except Exception as e:
            log.exception(f"Database connection exception : {e} ")
            return post_error("Database connection exception", "An error occurred while connecting to the database:{}".format(str(e)), None)


    def get_user_by_keys(self,user_ids, user_names, role_codes,org_codes,offset,limit_value,skip_pagination):
        """Searching for user records in the databse"""

        #keys to exclude from search result
        exclude = {"password": False,"_id":False}
        try:
            #connecting to mongo instance/collection
            collections = get_db()[USR_MONGO_COLLECTION]
            #fetching all verified users from db when skip_pgination = True
            if skip_pagination==True:
                log.info("Fetching all verified users from database")
                out = collections.find({"isVerified":True},exclude)
                record_count=out.count()
            #fetching users with pagination(skip & limit) when skip_pagination != True
            elif not user_ids and not user_names and not role_codes and not org_codes :
                log.info("Fetching verified users from database with pagination property")
                out = collections.find({"isVerified":True},exclude).sort([("_id",-1)]).skip(offset).limit(limit_value)
                record_count=collections.find({"isVerified":True}).count()
            else:
                log.info("Fetching verified users from database matching user properties")
                out = collections.find(
                {'$or': [
                    {'userID':{'$in': user_ids},'isVerified': True},
                    {'email': {'$in': user_names},'isVerified': True},
                    {'roles': {'$in': role_codes},'isVerified': True},
                    {'orgID': {'$in': org_codes},'isVerified': True}
                ]}, exclude)
                record_count=out.count()          
            result = []
            for record in out:
                result.append(normalize_bson_to_json(record))
            if not result:
                return None
            return result,record_count

        except Exception as e:
            log.exception(f"Exception on user search {str(e)}")
            return post_error("Database connection exception", "An error occurred while connecting to the database:{}".format(str(e)), None)

    def onboard_users(self,users):
        records = []
        for user in users:
            users_data = {}
            hashed = UserUtils.hash_password(user["password"])
            user_id = UserUtils.generate_user_id()
            user_roles = []
            for role in user["roles"]:
                role_info = {}
                role_info["roleCode"] = role["roleCode"].upper()
                if "roleDesc" in role:
                    role_info["roleDesc"] = role["roleDesc"]
                user_roles.append(role_info)
            users_data['userID'] = user_id
            users_data['name'] = user["name"]
            users_data['userName'] = user["userName"]
            users_data['password'] = hashed.decode("utf-8")
            users_data['email'] = user["email"]
            if "phoneNo" in user:
                users_data['phoneNo'] = user["phoneNo"]
            users_data['roles'] = user_roles
            users_data['is_verified'] =True
            users_data['is_active'] =True
            users_data['registered_time'] =eval(str(time.time()))
            users_data['activated_time'] =eval(str(time.time()))
            if "orgID" in user.keys():
                users_data['orgID'] = str(user["orgID"]).upper()          
            if "models" in user:
                users_data['models']= user["models"]
            records.append(users_data)

        if not records:
            return post_error("Data Null", "Data recieved for insert operation is null", None)
        try:
            #connecting to mongo instance/collection
            collections = get_db()[USR_MONGO_COLLECTION]
            #inserting records on database
            results = collections.insert(records)
            if len(records) != len(results):
                return post_error("Database error", "some of the records were not inserted into db", None)
            log.info("Count of users onboared : {}".format(len(results)), MODULE_CONTEXT)
        except Exception as e:
            log.exception("db connection exception " +
                          str(e),  MODULE_CONTEXT, e)
            return post_error("Database  exception", "An error occurred while processing on the db :{}".format(str(e)), None)


    def get_roles_from_role_sheet(self):
        role_description=UserUtils.read_role_codes()[1]
        if role_description:
            return role_description
