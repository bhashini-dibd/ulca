import os

DEBUG           =    False
CONTEXT_PATH    =    "/ulca/file-store"
HOST            =   '0.0.0.0'
PORT            =   5001
ENABLE_CORS     =   False


  
object_store            = os.environ.get('ULCA_OBJECT_STORE', "AWS")      
shared_storage_path     =   os.environ.get('ULCA_SEARCH_SHARED_STORAGE_PATHX', "opt")

aws_file_prefix         =   os.environ.get('ULCA_AWS_FILE_PREFIX',"errors/")
aws_access_key          =   os.environ.get('ULCA_AWS_S3_ACCESS_KEY', 'access-key')
aws_secret_key          =   os.environ.get('ULCA_AWS_S3_SECRET_KEY', 'secret-key')
aws_bucket_name         =   os.environ.get('ULCA_AWS_BUCKET_NAME', 'ulca-datasets')

azure_connection_string =   os.environ.get('ULCA_AZURE_CONNECTION_STRING',"connection-string")
azure_container_name    =   os.environ.get('ULCA_AZURE_FILE_CONTAINER',"ulca-datasets")

aws_link_prefix         =   f'http://{aws_bucket_name}.s3.amazonaws.com/'
azure_link_prefix       =   f'http://{azure_container_name}.azure.com/'


