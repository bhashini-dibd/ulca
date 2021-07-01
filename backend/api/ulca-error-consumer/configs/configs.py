import os

DEBUG                                   =   False
ENABLE_CORS                             =   False

context_path                            =   os.environ.get('ERROR_CONSUMER_CONTEXT_PATH','/ulca/error-consumer')
app_host                                =   os.environ.get('ULCA_ERROR_CONSUMER_HOST', '0.0.0.0')
app_port                                =   os.environ.get('ULCA_ERROR_CONSUMER_PORT', 5001)
publish_consumer_grp                    =   os.environ.get('KAFKA_ULCA_DS_PUBLISH_CONSUMER_GRP', 'ulca-ds-publish-consumer-group-v0')
ulca_dataset_topic_partitions           =   os.environ.get('KAFKA_ULCA_DS_TOPIC_PARTITIONS', 3)
if isinstance(ulca_dataset_topic_partitions, str):
    ulca_dataset_topic_partitions  =  eval(ulca_dataset_topic_partitions)

kafka_bootstrap_server_host             =   os.environ.get('KAFKA_ULCA_BOOTSTRAP_SERVER_HOST', 'localhost:9092')
error_event_input_topic                 =   os.environ.get('KAFKA_ULCA_ERROR_CONSUMER_IP_TOPIC', 'ulca-ds-error-ip-v0')
ulca_db_cluster                         =   os.environ.get('ULCA_MONGO_CLUSTER', "mongodb://localhost:27017/")
error_db                                =   os.environ.get('ULCA_ERROR_DB', "ulca-error")
error_collection                        =   os.environ.get('ULCA_ERROR_COL', "errors")
shared_storage_path                     =   os.environ.get('ULCA_SHARED_STORAGE_PATH', "/opt/")
error_prefix                            =   os.environ.get('ULCA_ERROR_PREFIX', 'errors')
pt_publish_tool                         =   os.environ.get('PT_TOOL_PUBLISH', 'publish')
file_store_host                         =   os.environ.get('ULCA_FILE_STORE_SERVER_URL', 'http://file-store:5001')
file_store_upload_endpoint              =   os.environ.get('ULCA_FILE_STORE_UPLOAD', '/ulca/file-store/v0/file/upload')
file_store_delete_endpoint              =   os.environ.get('ULCA_FILE_STORE_REMOVE', '/ulca/file-store/v0/file/remove')

redis_server_host                       =   os.environ.get('REDIS_URL','localhost')
redis_server_port                       =   os.environ.get('REDIS_PORT',6379)
redis_server_db                         =   os.environ.get('ULCA_REDIS_DB',5)
redis_server_password                   =   os.environ.get('REDIS_PASS','password')
redis_key_expiry                        =   os.environ.get('ULCA_ERROR_RECORD_EXPRY_SECONDS',172800)
if isinstance(redis_key_expiry, str):
    redis_key_expiry  =  eval(redis_key_expiry)
error_batch_size                        =   os.environ.get('ULCA_EEROR_BATCH_SIZE',10000)
if isinstance(error_batch_size, str):
    error_batch_size  =  eval(error_batch_size)

error_cron_interval_sec                 =   os.environ.get('ULCA_ERROR_CRON_JOB_INTERVAL_SEC',30)
if isinstance(error_cron_interval_sec, str):
    error_cron_interval_sec  =  eval(error_cron_interval_sec)

error_job_failure_interval_sec          =   os.environ.get('ULCA_ERROR_CRON_JOB_FAILURE_INTERVAL_SEC',60) 
if isinstance(error_job_failure_interval_sec, str):
    error_job_failure_interval_sec  =  eval(error_job_failure_interval_sec)