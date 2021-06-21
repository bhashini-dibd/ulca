import os

app_host                =   os.environ.get('ULCA_ERROR_CONSUMER_HOST', '0.0.0.0')
app_port                =   os.environ.get('ULCA_ERROR_CONSUMER_PORT', 5001)



publish_consumer_grp    =   os.environ.get('KAFKA_ULCA_DS_PUBLISH_CONSUMER_GRP', 'ulca-ds-publish-consumer-group-v0')
publish_error_code      =   "3000_XXX"
shared_storage_path     =   os.environ.get('ULCA_SEARCH_SHARED_STORAGE_PATHX', '/opt/')
aws_error_prefix        =   os.environ.get('ULCA_AWS_S3_ERROR_PREFIX', 'errors/')
pt_publish_tool         =   os.environ.get('PT_TOOL_PUBLISH', 'publish')

ulca_dataset_topic_partitions        =  os.environ.get('KAFKA_ULCA_DS_TOPIC_PARTITIONS', 3)
if isinstance(ulca_dataset_topic_partitions, str):
    ulca_dataset_topic_partitions    =  eval(ulca_dataset_topic_partitions)

kafka_bootstrap_server_host          =  os.environ.get('KAFKA_ULCA_BOOTSTRAP_SERVER_HOST', 'localhost:9092')
error_event_input_topic              =  os.environ.get('KAFKA_ULCA_ERROR_CONSUMER_IP_TOPIC', 'ulca-ds-error-ip-v1')



ulca_db_cluster         =   os.environ.get('ULCA_MONGO_CLUSTER', "mongodb://localhost:27017/")
error_db                =   os.environ.get('ULCA_ERROR_DB', "ulca-error")
error_collection        =   os.environ.get('ULCA_ERROR_COL', "errors")

azure_connection_string =   os.environ.get('ULCA_AZURE_CONNECTION_STRING',"XXXXXXXXXXXXX")
azure_container_name    =   os.environ.get('ULCA_AZURE_FILE_CONTAINER',"error-files")