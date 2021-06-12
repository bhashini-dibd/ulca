import os

app_host = os.environ.get('ULCA_DS_VALIDATE_HOST', '0.0.0.0')
app_port = os.environ.get('ULCA_DS_VALIDATE_PORT', 5001)

parallel_corpus_config_path = os.environ.get('ULCA_VALIDATE_PARALLEL_CONFIG', 'configs/parallel_corpus_config.json')
asr_config_path = os.environ.get('ULCA_VALIDATE_ASR_CONFIG', 'configs/asr_config.json')
ocr_config_path = os.environ.get('ULCA_VALIDATE_OCR_CONFIG', 'configs/ocr_config.json')
monolingual_config_path = os.environ.get('ULCA_VALIDATE_MONOLINGUAL_CONFIG', 'configs/monolingual_config.json')


kafka_bootstrap_server_host = os.environ.get('KAFKA_ULCA_BOOTSTRAP_SERVER_HOST', 'localhost:9092')
validate_input_topic = os.environ.get('KAFKA_ULCA_DS_VALIDATE_IP_TOPIC', 'ulca-ds-validate-ip-v0')
validate_output_topic = os.environ.get('KAFKA_ULCA_DS_PUBLISH_IP_TOPIC', 'ulca-ds-publish-ip-v0')
error_event_input_topic = os.environ.get('KAFKA_ULCA_DS_ERROR_IP_TOPIC', 'ulca-ds-error-ip-v0')
validate_error_code = "2000_XXX"

validate_consumer_grp = os.environ.get('KAFKA_ULCA_DS_VALIDATE_CONSUMER_GRP', 'ulca-ds-validate-consumer-group-v0')
ulca_dataset_topic_partitions = os.environ.get('KAFKA_ULCA_DS_TOPIC_PARTITIONS', 1)
if isinstance(ulca_dataset_topic_partitions, str):
    ulca_dataset_topic_partitions = eval(ulca_dataset_topic_partitions)


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

validate_parallel_labse_threshold = os.environ.get('VALIDATE_PARALLEL_LABSE_THRESHOLD', 0.75)


