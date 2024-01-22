import os

#service configs
DEBUG           =    False
CONTEXT_PATH    =    "/ulca/mdms"
HOST            =   '0.0.0.0'
PORT            =   5001
ENABLE_CORS     =   False

#git configs
git_folder_prefix           =   os.environ.get('ULCA_MASTER_DATA_GIT_FOLDER_PATH','https://raw.githubusercontent.com/bhashini-dibd/ulca/mdms/master-data/dev')
#git_folder_prefix = 'https://raw.githubusercontent.com/bhashini-dibd/ulca/develop/master-data/dev'
git_master_data_api         =   os.environ.get('ULCA_MASTER_DATA_GIT_API','https://api.github.com/repos/bhashini-dibd/ulca/contents/master-data/dev/')
#sared folder
shared_storage_folder       =   os.environ.get('ULCA_SHARED_STORAGE_PATH','/opt/') 
#redis configs 
redis_server_host           =   os.environ.get('REDIS_URL', 'localhost')
redis_server_db             =   os.environ.get('REDIS_MDMS_DB',8)
redis_server_port           =   os.environ.get('REDIS_PORT', 6379)
redis_server_password       =   os.environ.get('REDIS_PASS', None)
#pipeline
masPipe                     =   "pipelinefeedQns"