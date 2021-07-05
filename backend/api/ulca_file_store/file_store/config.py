import os

#service configs
DEBUG           =    False
CONTEXT_PATH    =    "/ulca/file-store"
HOST            =   '0.0.0.0'
PORT            =   5001
ENABLE_CORS     =   False
download_folder         =   'downloads/'
object_store            =   os.environ.get('ULCA_OBJECT_STORE', "AZURE")      
shared_storage_path     =   os.environ.get('ULCA_SHARED_STORAGE_PATH', "opt")

#aws configs
aws_file_prefix         =   os.environ.get('ULCA_AWS_FILE_PREFIX',"errors/")
aws_access_key          =   os.environ.get('ULCA_AWS_S3_ACCESS_KEY', 'access-key')
aws_secret_key          =   os.environ.get('ULCA_AWS_S3_SECRET_KEY', 'secret-key')
aws_bucket_name         =   os.environ.get('ULCA_AWS_BUCKET_NAME', 'ulca-datasets')
aws_link_prefix         =   f'https://{aws_bucket_name}.s3.amazonaws.com/'

#azure configs
azure_connection_string =   os.environ.get('ULCA_AZURE_CONNECTION_STRING',"connection_string")
azure_container_name    =   os.environ.get('ULCA_AZURE_FILE_CONTAINER',"container_name")
azure_account_name      =   os.environ.get('ULCA_AZURE_ACCOUNT_NAME', "account_name")
azure_link_prefix       =   f'https://{azure_account_name}.blob.core.windows.net/{azure_container_name}/'




