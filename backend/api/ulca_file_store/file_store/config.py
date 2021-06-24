import os

DEBUG           =    False
CONTEXT_PATH    =    "/ulca/file-store"
HOST            =   '0.0.0.0'
PORT            =   5001
ENABLE_CORS     =   False


ojectStore              =   os.environ.get("ULCA_FILE_STORE","AWS")        
shared_storage_path     =   os.environ.get('ULCA_SEARCH_SHARED_STORAGE_PATHX', 'opt')

aws_file_prefix         =   os.environ.get('ULCA_AWS_FILE_PREFIX',"errors/")
aws_access_key          =   os.environ.get('ULCA_AWS_S3_ACCESS_KEY', 'access-key')
aws_secret_key          =   os.environ.get('ULCA_AWS_S3_SECRET_KEY', 'secret-key')
aws_bucket_name         =   os.environ.get('ULCA_AWS_BUCKET_NAME', 'ulca-datasets')

aws_link_prefix         =   f'http://{aws_bucket_name}.s3.amazonaws.com/'