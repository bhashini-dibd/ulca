import os

#service configs
DEBUG           =    False
CONTEXT_PATH    =    "/ulca/mdms"
HOST            =   '0.0.0.0'
PORT            =   5001
ENABLE_CORS     =   False

#git configs
git_folder_prefix           =   os.environ.get('ULCA_MASTER_DATA_GIT_FOLDER',"https://raw.githubusercontent.com/ULCA-IN/ulca/master/master-data/dev/")
shared_storage_folder       =   os.environ.get('ULCA_SHARED_STORAGE_PATH','/home/jainy/Documents/upload/')  



