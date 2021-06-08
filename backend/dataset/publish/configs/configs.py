import os

app_host = os.environ.get('ULCA_DS_PUBLISH_HOST', '0.0.0.0')
app_port = os.environ.get('ULCA_DS_PUBLISH_PORT', 5010)

db_cluster = os.environ.get('ULCA_DS_PUBLISH_MONGO_CLUSTER', "mongodb://localhost:27017/")
db = os.environ.get('ULCA_DS_PUBLISH_DB', "ulca")
asr_collection = os.environ.get('ULCA_DS_PUBLISH_ASR_COL', "asr-dataset")
ocr_collection = os.environ.get('ULCA_DS_PUBLISH_OCR_COL', "ocr-dataset")
parallel_collection = os.environ.get('ULCA_DS_PUBLISH_PARALLEL_COL', "parallel-dataset")
monolingual_collection = os.environ.get('ULCA_DS_PUBLISH_MONOLINGUAL_COL', "monolingual-dataset")

offset = os.environ.get('ULCA_DATASET_DEFAULT_OFFSET', 0)
if isinstance(offset, str):
    offset = eval(offset)
limit = os.environ.get('ULCA_DATASET_DEFAULT_LIMIT', 100000000)
if isinstance(limit, str):
    offset = eval(limit)
parallel_ds_batch_size = os.environ.get('ULCA_PARALLEL_DS_BATCH_SIZE', 100000)
if isinstance(parallel_ds_batch_size, str):
    parallel_ds_batch_size = eval(parallel_ds_batch_size)
asr_ds_batch_size = os.environ.get('ULCA_ASR_DS_BATCH_SIZE', 1000)
if isinstance(asr_ds_batch_size, str):
    asr_ds_batch_size = eval(asr_ds_batch_size)
ocr_ds_batch_size = os.environ.get('ULCA_OCR_DS_BATCH_SIZE', 1000)
if isinstance(ocr_ds_batch_size, str):
    ocr_ds_batch_size = eval(ocr_ds_batch_size)
no_of_parallel_processes = os.environ.get('PUBLISH_PARALLEL_PRC', 1)
if isinstance(no_of_parallel_processes, str):
    no_of_parallel_processes = eval(no_of_parallel_processes)
sample_size = os.environ.get('ULCA_DATASET_SAMPLE_SIZE', 10)
if isinstance(sample_size, str):
    sample_size = eval(sample_size)
shared_storage_path = os.environ.get('ULCA_SEARCH_SHARED_STORAGE_PATH', '/app/publish/')

asr_immutable_keys = ["id", "audioFilename", "text", "audioFilePath", "audioHash", "textHash", "datasetType", "sourceLanguage"]
asr_non_tag_keys = ["id", "startTime", "endTime", "samplingRate", "audioFilename", "text", "submitter"]
parallel_immutable_keys = ["id", "sourceText", "targetText", "sourceTextHash", "targetTextHash", "sourceLanguage", "targetLanguage", "datasetType"]
parallel_non_tag_keys = ["id", "score", "sourceText", "targetText", "submitter"]
ocr_immutable_keys = ["id", "imageFilename", "groundTruth", "imageFilePath", "imageHash", "groundTruthHash", "datasetType", "sourceLanguage"]
ocr_non_tag_keys = ["id", "boundingBox", "imageFilename", "groundTruth", "imageFilePath", "submitter", "sourceLanguage"]
mono_immutable_keys = ["id", "text", "textHash", "datasetType"]
mono_non_tag_keys = ["id", "text", "submitter"]
publish_error_code = "3000_XXX"

pt_publish_tool = "publish"
pt_inprogress_status = "inprogress"
pt_success_status = "successful"
pt_failed_status = "failed"
pt_db = os.environ.get('ULCA_PROC_TRACKER_DB', "ulca-process-tracker")
pt_task_collection = os.environ.get('ULCA_PROC_TRACKER_TASK_COL', "ulca-pt-tasks")



kafka_bootstrap_server_host = os.environ.get('KAFKA_ULCA_BOOTSTRAP_SERVER_HOST', 'localhost:9092')
publish_input_topic = os.environ.get('KAFKA_ULCA_DS_PUBLISH_IP_TOPIC', 'ulca-ds-publish-ip-v0')
publish_output_topic = os.environ.get('KAFKA_ULCA_DS_PUBLISH_OP_TOPIC', 'ulca-ds-publish-op-v0')
search_input_topic = os.environ.get('KAFKA_ULCA_DS_SEARCH_IP_TOPIC', 'ulca-ds-search-ip-v0')
search_output_topic = os.environ.get('KAFKA_ULCA_DS_SEARCH_OP_TOPIC', 'ulca-ds-search-op-v0')
delete_input_topic = os.environ.get('KAFKA_ULCA_DS_DELETE_IP_TOPIC', 'ulca-ds-delete-ip-v0')
delete_output_topic = os.environ.get('KAFKA_ULCA_DS_DELETE_OP_TOPIC', 'ulca-ds-delete-op-v0')
publish_consumer_grp = os.environ.get('KAFKA_ULCA_DS_PUBLISH_CONSUMER_GRP', 'ulca-ds-publish-consumer-group-v0')
error_event_input_topic = os.environ.get('KAFKA_ULCA_DS_ERROR_IP_TOPIC', 'ulca-ds-error-op-v0')
metric_event_input_topic = os.environ.get('KAFKA_ULCA_DS_METRIC_IP_TOPIC', 'ulca-ds-metric-op-v0')
ulca_dataset_topic_partitions = os.environ.get('KAFKA_ULCA_DS_TOPIC_PARTITIONS', 3)
if isinstance(ulca_dataset_topic_partitions, str):
    ulca_dataset_topic_partitions = eval(ulca_dataset_topic_partitions)

aws_access_key = os.environ.get('ULCA_AWS_S3_ACCESS_KEY', 'access-key')
aws_secret_key = os.environ.get('ULCA_AWS_S3_SECRET_KEY', 'secret-key')
aws_bucket_name = os.environ.get('ULCA_AWS_BUCKET_NAME', 'ulca-datasets')
aws_ocr_prefix = os.environ.get('ULCA_AWS_S3_OCR_PREFIX', '/ocr/')
aws_asr_prefix = os.environ.get('ULCA_AWS_S3_ASR_PREFIX', '/asr/')
aws_dataset_prefix = os.environ.get('ULCA_AWS_S3_DATASET_PREFIX', '/datasets/')

dataset_type_parallel = os.environ.get('DATASET_TYPE_PARALLEL_DS', 'parallel-corpus')
dataset_type_asr = os.environ.get('DATASET_TYPE_ASR_DS', 'asr-corpus')
dataset_type_ocr = os.environ.get('DATASET_TYPE_OCR_DS', 'ocr-corpus')
dataset_type_monolingual = os.environ.get('DATASET_TYPE_MONOLINGUAL_DS', 'monolingual-corpus')


