import config
import json
from models import UserAuthenticationModel

authModel = UserAuthenticationModel()

class UserAuthenticationRepositories:

    def user_login(self,user_name, password):
        result = authModel.user_login(user_name, password)
        return result

    def user_logout(self,user_name):
        result = authModel.user_logout(user_name)
        return result

    def key_search(self,key):
        result = authModel.key_search(key)
        return result

    def forgot_password(self,user_name):
        result = authModel.forgot_password(user_name)
        return result
  
    def reset_password(self,user_id,user_name,password):
        result = authModel.reset_password(user_id,user_name,password)
        return result

    def verify_user(self,user_email,user_id):
        result = authModel.verify_user(user_email,user_id)
        return result

    def activate_deactivate_user(self,user_email,status,from_id):
        result = authModel.activate_deactivate_user(user_email,status,from_id)
        return result
    
    def token_search(self,token):
        result = authModel.token_search(token)
        return result