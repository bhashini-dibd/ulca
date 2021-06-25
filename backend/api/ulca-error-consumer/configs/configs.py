import os


app_host                =   os.environ.get('ULCA_ERROR_CONSUMER_HOST', '0.0.0.0')
app_port                =   os.environ.get('ULCA_ERROR_CONSUMER_PORT', 5001)



publish_consumer_grp    =   os.environ.get('KAFKA_ULCA_DS_PUBLISH_CONSUMER_GRP', 'ulca-ds-publish-consumer-group-v0')
publish_error_code      =   "3000_XXX"
shared_storage_path     =   os.environ.get('ULCA_SEARCH_SHARED_STORAGE_PATHX', '/home/jainy/Desktop')#/opt/ 
aws_error_prefix        =   os.environ.get('ULCA_AWS_S3_ERROR_PREFIX', 'errors/')
pt_publish_tool         =   os.environ.get('PT_TOOL_PUBLISH', 'publish')

ulca_dataset_topic_partitions        =  os.environ.get('KAFKA_ULCA_DS_TOPIC_PARTITIONS', 3)
if isinstance(ulca_dataset_topic_partitions, str):
    ulca_dataset_topic_partitions    =  eval(ulca_dataset_topic_partitions)

kafka_bootstrap_server_host          =  os.environ.get('KAFKA_ULCA_BOOTSTRAP_SERVER_HOST', 'localhost:9092')
error_event_input_topic              =  os.environ.get('KAFKA_ULCA_ERROR_CONSUMER_IP_TOPIC', 'ulca-ds-error-ip-v0')



ulca_db_cluster         =   os.environ.get('ULCA_MONGO_CLUSTER', "mongodb://localhost:27017/")
error_db                =   os.environ.get('ULCA_ERROR_DB', "ulca-error")
error_collection        =   os.environ.get('ULCA_ERROR_COL', "errors")

aws_access_key          =   os.environ.get('ULCA_AWS_S3_ACCESS_KEY', 'access-key')
aws_secret_key          =   os.environ.get('ULCA_AWS_S3_SECRET_KEY', 'secret-key')
aws_bucket_name         =   os.environ.get('ULCA_AWS_BUCKET_NAME', 'ulca-datasets')
aws_ocr_prefix          =   os.environ.get('ULCA_AWS_S3_OCR_PREFIX', 'ocr/')
aws_asr_prefix          =   os.environ.get('ULCA_AWS_S3_ASR_PREFIX', 'asr/')
aws_dataset_prefix      =   os.environ.get('ULCA_AWS_S3_DATASET_PREFIX', 'datasets/')
error_prefix            =   os.environ.get('ULCA_AWS_S3_ERROR_PREFIX', 'errors')
aws_link_prefix         =   f'http://{aws_bucket_name}.s3.amazonaws.com/'


# https://myaccount.blob.core.windows.net/mycontainer/myblob.ext


file_store_host               =   os.environ.get('ULCA_FILE_STORE_SERVER_URL', 'http://file-store:5001/')
file_store_upload_endpoint    =   os.environ.get('ULCA_FILE_STORE_UPLOAD', '/ulca/file-store/v0/file/upload')
file_store_delete_endpoint    =   os.environ.get('ULCA_FILE_STORE_UPLOAD', '/ulca/file-store/v0/file/remove')

azure_connection_string =   os.environ.get('ULCA_AZURE_CONNECTION_STRING',"azure_connection_string")
azure_container_name    =   os.environ.get('ULCA_AZURE_FILE_CONTAINER',"azure-container")
azure_account_name      =   os.environ.get('ULCA_AZURE_ACCOUNT_NAME', "azure-account")
azure_link_prefix       =   f'https://{azure_account_name}.blob.core.windows.net/{azure_container_name}/'

