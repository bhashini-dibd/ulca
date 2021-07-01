import os

app_host = os.environ.get('ULCA_DS_PUBLISH_HOST', '0.0.0.0')
app_port = os.environ.get('ULCA_DS_PUBLISH_PORT', 5001)
redis_server_host = os.environ.get('REDIS_URL', 'localhost')
redis_server_port = os.environ.get('REDIS_PORT', 6379)
redis_server_pass = os.environ.get('REDIS_PASS', None)

db_cluster = os.environ.get('ULCA_DS_PUBLISH_MONGO_CLUSTER', "mongodb://localhost:27017/")
db = os.environ.get('ULCA_DS_PUBLISH_DB', "ulca")
asr_collection = os.environ.get('ULCA_DS_PUBLISH_ASR_COL', "asr-dataset")
ocr_collection = os.environ.get('ULCA_DS_PUBLISH_OCR_COL', "ocr-dataset")
parallel_collection = os.environ.get('ULCA_DS_PUBLISH_PARALLEL_COL', "parallel-dataset")
monolingual_collection = os.environ.get('ULCA_DS_PUBLISH_MONOLINGUAL_COL', "monolingual-dataset")
object_store = os.environ.get('ULCA_OBJECT_STORE', "AWS")

offset = os.environ.get('ULCA_DATASET_DEFAULT_OFFSET', None)
if isinstance(offset, str):
    offset = eval(offset)
limit = os.environ.get('ULCA_DATASET_DEFAULT_LIMIT', None)
if isinstance(limit, str):
    offset = eval(limit)
ds_batch_size = os.environ.get('ULCA_DS_BATCH_SIZE', 1000)
if isinstance(ds_batch_size, str):
    ds_batch_size = eval(ds_batch_size)
pt_update_batch = os.environ.get('ULCA_PT_UPDATE_BATCH', 100)
if isinstance(pt_update_batch, str):
    pt_update_batch = eval(pt_update_batch)
no_of_parallel_processes = os.environ.get('PUBLISH_PARALLEL_PRC', 1)
if isinstance(no_of_parallel_processes, str):
    no_of_parallel_processes = eval(no_of_parallel_processes)
sample_size = os.environ.get('ULCA_DATASET_SAMPLE_SIZE', 10)
if isinstance(sample_size, str):
    sample_size = eval(sample_size)
pt_redis_db = os.environ.get('ULCA_PT_REDIS_DB', 0)
if isinstance(pt_redis_db, str):
    pt_redis_db = eval(pt_redis_db)
record_expiry_in_sec = os.environ.get('ULCA_PUBLISH_RECORD_EXPIRY_IN_SEC', 86400)
if isinstance(record_expiry_in_sec, str):
    record_expiry_in_sec = eval(record_expiry_in_sec)
shared_storage_path = os.environ.get('ULCA_SEARCH_SHARED_STORAGE_PATH', '/opt/')

asr_immutable_keys = ["id", "audioFilename", "text", "audioHash", "textHash", "datasetType",
                      "sourceLanguage", "fileLocation"]
asr_non_tag_keys = ["id", "startTime", "endTime", "samplingRate", "audioFilename", "text", "submitter", "fileLocation"]
asr_search_ignore_keys = ["_id", "tags", "submitter", "collectionSource", "license", "domain", "collectionMethod",
                          "datasetType", "audioHash", "textHash", "fileLocation"]
asr_updatable_keys = []

parallel_immutable_keys = ["id", "sourceText", "targetText", "sourceTextHash", "targetTextHash", "sourceLanguage",
                           "targetLanguage", "datasetType"]
parallel_updatable_keys = ["alignmentScore"]

parallel_non_tag_keys = ["id", "score", "sourceText", "targetText", "submitter"]
parallel_search_ignore_keys = ["_id", "tags", "submitter", "collectionSource", "license", "domain",
                               "collectionMethod", "datasetType", "hashedKey", "sk", "derived", "sourceTextHash",
                               "targetTextHash"]

ocr_immutable_keys = ["id", "imageFilename", "groundTruth", "imageHash", "groundTruthHash",
                      "datasetType", "sourceLanguage", "fileLocation"]
ocr_non_tag_keys = ["id", "boundingBox", "imageFilename", "groundTruth", "imageFilePath", "submitter", "fileLocation"]
ocr_search_ignore_keys = ["_id", "tags", "submitter", "collectionSource", "license", "domain", "collectionMethod",
                          "datasetType", "imageHash", "groundTruthHash", "fileLocation"]
ocr_updatable_keys = []

mono_immutable_keys = ["id", "text", "textHash", "datasetType", "sourceLanguage"]
mono_non_tag_keys = ["id", "text", "submitter"]
mono_search_ignore_keys = ["_id", "tags", "submitter", "collectionSource", "license", "domain", "collectionMethod",
                          "datasetType", "textHash"]
mono_updatable_keys = []

publish_error_code = "3000_XXX"
threads_threshold = 100

pt_publish_tool = os.environ.get('PT_TOOL_PUBLISH', 'publish')
pt_search_tool = os.environ.get('PT_TOOL_SEARCH', 'search')
pt_delete_tool = os.environ.get('PT_TOOL_DELETE', 'delete')
pt_inprogress_status = os.environ.get('STATUS_INPROGRESS', 'In-Progress')
pt_success_status = os.environ.get('STATUS_COMPLETED', 'Completed')
pt_failed_status = os.environ.get('STATUS_FAILED', 'Failed')

ulca_db_cluster = os.environ.get('ULCA_MONGO_CLUSTER', "mongodb://localhost:27017/")
pt_db = os.environ.get('ULCA_PROC_TRACKER_DB', "ulca-process-tracker")
pt_task_collection = os.environ.get('ULCA_PROC_TRACKER_TASK_COL', "ulca-pt-tasks")
error_db = os.environ.get('ULCA_ERROR_DB', "ulca-error")
error_collection = os.environ.get('ULCA_ERROR_COL', "errors")

kafka_bootstrap_server_host = os.environ.get('KAFKA_ULCA_BOOTSTRAP_SERVER_HOST', 'localhost:9092')
publish_input_topic = os.environ.get('KAFKA_ULCA_DS_PUBLISH_IP_TOPIC', 'ulca-ds-publish-ip-v0')
search_input_topic = os.environ.get('KAFKA_ULCA_DS_SEARCH_IP_TOPIC', 'ulca-ds-search-ip-v0')
delete_input_topic = os.environ.get('KAFKA_ULCA_DS_DELETE_IP_TOPIC', 'ulca-ds-delete-ip-v0')
publish_consumer_grp = os.environ.get('KAFKA_ULCA_DS_PUBLISH_CONSUMER_GRP', 'ulca-ds-publish-consumer-group-v0')
error_event_input_topic = os.environ.get('KAFKA_ULCA_DS_ERROR_IP_TOPIC', 'ulca-ds-error-ip-v0')
metric_event_input_topic = os.environ.get('KAFKA_ULCA_DS_METRIC_IP_TOPIC', 'org-ulca-bievent-dataset-v3')
ulca_dataset_topic_partitions = os.environ.get('KAFKA_ULCA_DS_TOPIC_PARTITIONS', 3)
if isinstance(ulca_dataset_topic_partitions, str):
    ulca_dataset_topic_partitions = eval(ulca_dataset_topic_partitions)

aws_access_key = os.environ.get('ULCA_AWS_S3_ACCESS_KEY', 'access-key')
aws_secret_key = os.environ.get('ULCA_AWS_S3_SECRET_KEY', 'secret-key')
aws_bucket_name = os.environ.get('ULCA_AWS_BUCKET_NAME', 'ulca-datasets')
aws_link_prefix = f'http://{aws_bucket_name}.s3.amazonaws.com/'

azure_connection_string = os.environ.get('ULCA_AZURE_CONNECTION_STRING', "azure_conn_string")
azure_container_name = os.environ.get('ULCA_AZURE_FILE_CONTAINER', "ulca-prod-container")
azure_account_name = os.environ.get('ULCA_AZURE_ACCOUNT_NAME', "azure_account")
azure_link_prefix = f'https://{azure_account_name}.blob.core.windows.net/{azure_container_name}/'

ocr_prefix = os.environ.get('ULCA_AWS_S3_OCR_PREFIX', 'ocr')
asr_prefix = os.environ.get('ULCA_AWS_S3_ASR_PREFIX', 'asr')
dataset_prefix = os.environ.get('ULCA_AWS_S3_DATASET_PREFIX', 'datasets')
error_prefix = os.environ.get('ULCA_AWS_S3_ERROR_PREFIX', 'errors')

dataset_type_parallel = os.environ.get('DS_TYPE_PARALLEL', 'parallel-corpus')
dataset_type_asr = os.environ.get('DS_TYPE_ASR', 'asr-corpus')
dataset_type_ocr = os.environ.get('DS_TYPE_OCR', 'ocr-corpus')
dataset_type_monolingual = os.environ.get('DS_TYPE_MONOLINGUAL', 'monolingual-corpus')

user_mode_pseudo = os.environ.get('USER_MODE_PSEUDO', 'pseudo')
user_mode_real = os.environ.get('USER_MODE_REAL', 'real')

file_store_host = os.environ.get('ULCA_FILE_STORE_SERVER_URL', 'http://file-store:5001')
file_store_upload_endpoint = os.environ.get('ULCA_FILE_STORE_UPLOAD', '/ulca/file-store/v0/file/upload')
file_store_delete_endpoint = os.environ.get('ULCA_FILE_STORE_REMOVE', '/ulca/file-store/v0/file/remove')
