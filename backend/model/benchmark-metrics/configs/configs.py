import os

redis_server_host = os.environ.get('REDIS_URL', 'localhost')
redis_server_port = os.environ.get('REDIS_PORT', 6379)
redis_server_pass = os.environ.get('REDIS_PASS', None)

benchmark_metrics_dedup_redis_db = os.environ.get('ULCA_BENCHMARK_METRICS_DEDUP_REDIS_DB', 7)
if isinstance(benchmark_metrics_dedup_redis_db, str):
    benchmark_metrics_dedup_redis_db = eval(benchmark_metrics_dedup_redis_db)

record_expiry_in_sec = os.environ.get('ULCA_BENCHMARK_METRICS_RECORD_EXPIRY_IN_SEC', 172800)

metric_config_path = os.environ.get('ULCA_MODEL_METRIC_CONFIG_PATH', 'configs/metric_config.json')

kafka_bootstrap_server_host = os.environ.get('KAFKA_ULCA_BOOTSTRAP_SERVER_HOST', 'localhost:9092')
metric_eval_input_topic = os.environ.get('KAFKA_ULCA_METRIC_EVAL_IP_TOPIC', 'ulca-bm-metric-ip-v0')
metric_eval_consumer_grp = os.environ.get('KAFKA_ULCA_METRIC_EVAL_CONSUMER_GRP', 'ulca-metric-eval-consumer-group-v0')
ulca_notifier_input_topic = os.environ.get('KAFKA_ULCA_NOTIFIER_CONSUMER_IP_TOPIC', 'ulca-notifier-ip-v0')
ulca_notifier_benchmark_completed_event = os.environ.get('ULCA_NOTIFIER_BM_RUN_COMPLETED_STATUS', 'benchmark-run-completed')
ulca_notifier_benchmark_failed_event = os.environ.get('ULCA_NOTIFIER_BM_RUN_FAILED_STATUS', 'benchmark-run-failed')

ulca_db_cluster = os.environ.get('ULCA_MONGO_CLUSTER', "mongodb://10.30.11.136:27017/")
mongo_db_name = os.environ.get('ULCA_PROC_TRACKER_DB', "ulca-process-tracker")
mongo_collection_name = os.environ.get('ULCA_BENCHMARK_PROCESS_COLLECTION', "benchmarkprocess")
mongo_pt_collection_name = os.environ.get('ULCA_BENCHMARK_PROCESS_TRACKER_COLLECTION', "ulca-bm-tasks")


model_task_type_translation = os.environ.get('MODEL_TASK_TYPE_TRANSLATION', 'translation')
model_task_type_asr = os.environ.get('MODEL_TASK_TYPE_ASR', 'asr')
model_task_type_ocr = os.environ.get('MODEL_TASK_TYPE_OCR', 'ocr')
model_task_type_transliteration = os.environ.get('MODEL_TASK_TYPE_TRANSLITERATION', 'transliteration')