from models import UserManagementModel
from models import post_error
from utilities import UserUtils
import time
import datetime

userModel   =   UserManagementModel()

class UserManagementRepositories:
    
    def create_users(self,users):

        records                         =   []
        for user in users:
            users_data                  =   {}
            hashed                      =   UserUtils.hash_password(user["password"])
            user_id                     =   UserUtils.generate_user_id()
            user_api_key                =   UserUtils.generate_user_api_key()
            users_data["userID"]        =   user_id
            users_data["userApiKey"]    =   user_api_key#should be a list
            users_data["email"]         =   user["email"]
            users_data["firstName"]     =   user["firstName"]
            users_data["password"]      =   hashed.decode("utf-8")
            users_data["roles"]         =   user["roles"]
            
            if "lastName" in user:
                users_data["lastName"]  =   user["lastName"]
            if "phoneNo" in user:
                users_data["phoneNo"]   =   user["phoneNo"]

            users_data["isVerified"]   =   False
            users_data["isActive"]     =   False
            users_data["registeredTime"]   =   datetime.datetime.utcnow()
            users_data["activatedTime"]    =   0
            records.append(users_data)
        if not records:
            return post_error("Data Null", "Data recieved for user creation is empty", None)

        result = userModel.create_users(records)
        if result is not None:
            return result

    def update_users(self,users,user_id):
        records                         =   []
        for user in users:
            users_data                  =   {}
            users_data["email"]         =   user["email"]
            if user.get("firstName")    !=  None:
                users_data["firstName"] =   user["firstName"]
            if user.get("phoneNo")      !=  None:
                users_data["phoneNo"]   =   user["phoneNo"]
            if user.get("roles")        !=  None:
                users_data["roles"]     =  user["roles"]
            if user.get("password")     !=  None:
                hashed                   =   UserUtils.hash_password(user["password"])
                users_data["password"]   =   hashed.decode("utf-8")

            records.append(users_data)
        result = userModel.update_users_by_uid(records,user_id)
        if result is not None:
            return result
        else:
            return True

    def search_users(self,user_ids, user_emails, role_codes,org_codes,offset,limit_value,skip_pagination):
        result = userModel.get_user_by_keys(
            user_ids, user_emails, role_codes,org_codes,offset,limit_value,skip_pagination)
        if result is not None:
            return result

    def onboard_users(self,users):
        result = userModel.onboard_users(users)
        if result is not None:
            return result

    def get_roles(self):
        result = userModel.get_roles_from_role_sheet()
        if result is not None:
            return result

    # def get_user_apiKey(self):
    #     result = UserUtils.