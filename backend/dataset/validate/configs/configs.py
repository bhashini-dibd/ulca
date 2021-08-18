import os

app_host = os.environ.get('ULCA_DS_VALIDATE_HOST', '0.0.0.0')
app_port = os.environ.get('ULCA_DS_VALIDATE_PORT', 5001)

redis_server_host = os.environ.get('REDIS_URL', 'localhost')
redis_server_port = os.environ.get('REDIS_PORT', 6379)
redis_server_pass = os.environ.get('REDIS_PASS', None)

parallel_corpus_config_path = os.environ.get('ULCA_VALIDATE_PARALLEL_CONFIG', 'configs/parallel_corpus_config.json')
asr_config_path = os.environ.get('ULCA_VALIDATE_ASR_CONFIG', 'configs/asr_config.json')
ocr_config_path = os.environ.get('ULCA_VALIDATE_OCR_CONFIG', 'configs/ocr_config.json')
monolingual_config_path = os.environ.get('ULCA_VALIDATE_MONOLINGUAL_CONFIG', 'configs/monolingual_config.json')
asr_unlabeled_config_path = os.environ.get('ULCA_VALIDATE_ASR_UNLABELED_CONFIG', 'configs/asr_unlabeled_config.json')

pt_update_batch = os.environ.get('ULCA_PT_UPDATE_BATCH', 1000)
if isinstance(pt_update_batch, str):
    pt_update_batch = eval(pt_update_batch)

kafka_bootstrap_server_host = os.environ.get('KAFKA_ULCA_BOOTSTRAP_SERVER_HOST', 'localhost:9092')
validate_input_topic = os.environ.get('KAFKA_ULCA_DS_VALIDATE_IP_TOPIC', 'ulca-ds-validate-ip-v0')
validate_output_topic = os.environ.get('KAFKA_ULCA_DS_PUBLISH_IP_TOPIC', 'ulca-ds-publish-ip-v0')
error_event_input_topic = os.environ.get('KAFKA_ULCA_DS_ERROR_IP_TOPIC', 'ulca-ds-error-ip-v0')
validate_error_code = "2000_XXX"

validate_consumer_grp = os.environ.get('KAFKA_ULCA_DS_VALIDATE_CONSUMER_GRP', 'ulca-ds-validate-consumer-group-v0')
ulca_dataset_topic_partitions = os.environ.get('KAFKA_ULCA_DS_TOPIC_PARTITIONS', 16)
if isinstance(ulca_dataset_topic_partitions, str):
    ulca_dataset_topic_partitions = eval(ulca_dataset_topic_partitions)

pt_redis_db = os.environ.get('ULCA_PT_REDIS_DB', 0)
if isinstance(pt_redis_db, str):
    pt_redis_db = eval(pt_redis_db)

validate_dedup_redis_db = os.environ.get('ULCA_VALIDATE_DEDUP_REDIS_DB', 6)
if isinstance(validate_dedup_redis_db, str):
    validate_dedup_redis_db = eval(validate_dedup_redis_db)

record_expiry_in_sec = os.environ.get('ULCA_VALIDATE_RECORD_EXPIRY_IN_SEC', 172800)
shared_storage_path = os.environ.get('ULCA_SEARCH_SHARED_STORAGE_PATHX', '/opt/')

ulca_db_cluster = os.environ.get('ULCA_MONGO_CLUSTER', "mongodb://localhost:27017/")
pt_db = os.environ.get('ULCA_PROC_TRACKER_DB', "ulca-process-tracker")
pt_task_collection = os.environ.get('ULCA_PROC_TRACKER_TASK_COL', "ulca-pt-tasks")

pt_publish_tool = os.environ.get('PT_TOOL_VALIDATE', 'validate')
pt_inprogress_status = os.environ.get('PT_STATUS_INPROGRESS', 'inprogress')
pt_success_status = os.environ.get('PT_STATUS_SUCCESS', 'successful')
pt_failed_status = os.environ.get('PT_STATUS_FAILED', 'failed')

dataset_type_parallel = os.environ.get('DS_TYPE_PARALLEL', 'parallel-corpus')
dataset_type_asr = os.environ.get('DS_TYPE_ASR', 'asr-corpus')
dataset_type_ocr = os.environ.get('DS_TYPE_OCR', 'ocr-corpus')
dataset_type_monolingual = os.environ.get('DS_TYPE_MONOLINGUAL', 'monolingual-corpus')
dataset_type_asr_unlabeled = os.environ.get('DS_TYPE_ASR_UNLABELED', 'asr-unlabeled-corpus')

validate_text_length_threshold = os.environ.get('DS_VALIDATE_TEXT_LENGTH_THRESHOLD', 2)
asr_minimum_words_per_min = os.environ.get('DS_VALIDATE_MINIMUM_WORDS_PER_MIN', 10)
validate_parallel_labse_threshold = os.environ.get('VALIDATE_PARALLEL_LABSE_THRESHOLD', 0.75)


