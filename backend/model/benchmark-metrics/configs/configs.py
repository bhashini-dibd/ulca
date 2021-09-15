import os

# redis_server_host = os.environ.get('REDIS_URL', 'localhost')
# redis_server_port = os.environ.get('REDIS_PORT', 6379)
# redis_server_pass = os.environ.get('REDIS_PASS', None)

metric_config_path = os.environ.get('ULCA_MODEL_METRIC_CONFIG_PATH', 'configs/metric_config.json')

kafka_bootstrap_server_host = os.environ.get('KAFKA_ULCA_BOOTSTRAP_SERVER_HOST', 'localhost:9092')
metric_eval_input_topic = os.environ.get('KAFKA_ULCA_METRIC_EVAL_IP_TOPIC', 'ulca-bm-metric-ip-v0')
metric_eval_consumer_grp = os.environ.get('KAFKA_ULCA_METRIC_EVAL_CONSUMER_GRP', 'ulca-metric-eval-consumer-group-v0')

# pt_redis_db = os.environ.get('ULCA_PT_REDIS_DB', 0)
# if isinstance(pt_redis_db, str):
#     pt_redis_db = eval(pt_redis_db)



ulca_db_cluster = os.environ.get('ULCA_MONGO_CLUSTER', "mongodb://10.30.11.136:27017/")
mongo_db_name = os.environ.get('ULCA_PROC_TRACKER_DB', "ulca-process-tracker")
mongo_collection_name = os.environ.get('ULCA_BENCHMARK_PROCESS_COLLECTION', "benchmarkprocess")


# pt_db = os.environ.get('ULCA_PROC_TRACKER_DB', "ulca-process-tracker")
# pt_task_collection = os.environ.get('ULCA_PROC_TRACKER_TASK_COL', "ulca-pt-tasks")

# pt_publish_tool = os.environ.get('PT_TOOL_VALIDATE', 'validate')
# pt_inprogress_status = os.environ.get('PT_STATUS_INPROGRESS', 'inprogress')
# pt_success_status = os.environ.get('PT_STATUS_SUCCESS', 'successful')
# pt_failed_status = os.environ.get('PT_STATUS_FAILED', 'failed')

model_task_type_translation = os.environ.get('MODEL_TASK_TYPE_TRANSLATION', 'translation')
model_task_type_asr = os.environ.get('MODEL_TASK_TYPE_ASR', 'asr')