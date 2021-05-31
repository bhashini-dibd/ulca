import os

db_cluster = os.environ.get('ULCA_DS_PUBLISH_MONGO_CLUSTER', "mongodb://15.207.103.202:27017/")
db = os.environ.get('ULCA_DS_PUBLISH_DB', "ulca")
asr_collection = os.environ.get('ULCA_DS_PUBLISH_ASR_COL', "asr-dataset")
ocr_collection = os.environ.get('ULCA_DS_PUBLISH_OCR_COL', "ocr-dataset")
parallel_collection = os.environ.get('ULCA_DS_PUBLISH_PARALLEL_COL', "parallel-datatset")
monolingual_collection = os.environ.get('ULCA_DS_PUBLISH_MONOLINGUAL_COL', "monolingual-dataset")

offset = os.environ.get('ULCA_DATASET_DEFAULT_OFFSET', 0)
if isinstance(offset, str):
    offset = eval(offset)
limit = os.environ.get('ULCA_DATASET_DEFAULT_LIMIT', 1000)
if isinstance(limit, str):
    offset = eval(limit)
parallel_ds_batch_size = os.environ.get('ULCA_PARALLEL_DS_BATCH_SIZE', 100000)
if isinstance(limit, str):
    parallel_ds_batch_size = eval(limit)

kafka_bootstrap_server_host = os.environ.get('KAFKA_ULCA_BOOTSTRAP_SERVER_HOST', 'localhost:9092')
parallel_input_topic = os.environ.get('KAFKA_ULCA_PARALLEL_DS_PUBLISH_IP_TOPIC', 'ulca-parallel-ds-ip-v0')
asr_input_topic = os.environ.get('KAFKA_ULCA_ASR_DS_PUBLISH_IP_TOPIC', 'ulca-asr-ds-ip-v0')
ocr_input_topic = os.environ.get('KAFKA_ULCA_OCR_DS_PUBLISH_IP_TOPIC', 'ulca-ocr-ds-ip-v0')
monolingual_input_topic = os.environ.get('KAFKA_ULCA_MONOLINGUAL_PUBLISH_DS_IP_TOPIC', 'ulca-monolingual-ds-ip-v0')
ulca_dataset_publish_consumer_grp = os.environ.get('KAFKA_ULCA_DS_PUBLISH_CONSUMER_GRP', 'ulca-ds-publish-consumer-group-v0')
ulca_dataset_topic_partitions = os.environ.get('KAFKA_ULCA_DS_PUBLISH_TOPIC_PARTITIONS', 3)
if isinstance(ulca_dataset_topic_partitions, str):
    ulca_dataset_topic_partitions = eval(ulca_dataset_topic_partitions)
