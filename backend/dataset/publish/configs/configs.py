import os

app_host = os.environ.get('ULCA_DS_PUBLISH_HOST', '0.0.0.0')
app_port = os.environ.get('ULCA_DS_PUBLISH_PORT', 5001)
redis_server_host = os.environ.get('REDIS_URL', 'localhost')
redis_server_port = os.environ.get('REDIS_PORT', 6379)
redis_server_pass = os.environ.get('REDIS_PASS', None)

db_cluster = os.environ.get('ULCA_DS_PUBLISH_MONGO_CLUSTER', "mongodb://10.30.11.136:27017/")
db = os.environ.get('ULCA_DS_PUBLISH_DB', "ulca")
asr_collection = os.environ.get('ULCA_DS_PUBLISH_ASR_COL', "asr-dataset")
tts_collection = os.environ.get('ULCA_DS_PUBLISH_TTS_COL', "tts-dataset")
asr_unlabeled_collection = os.environ.get('ULCA_DS_PUBLISH_ASR_UNLABELED_COL', "asr-unlabeled-dataset")
ocr_collection = os.environ.get('ULCA_DS_PUBLISH_OCR_COL', "ocr-dataset")
parallel_collection = os.environ.get('ULCA_DS_PUBLISH_PARALLEL_COL', "parallel-dataset")
monolingual_collection = os.environ.get('ULCA_DS_PUBLISH_MONOLINGUAL_COL', "monolingual-dataset")
transliteration_collection = os.environ.get('ULCA_DS_PUBLISH_TRANSLITERATIONL_COL', "transliteration-dataset")
glossary_collection = os.environ.get('ULCA_DS_PUBLISH_GLOSSARY_COL', "glossary-dataset")
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
pub_dedup_redis_db = os.environ.get('ULCA_PUBLISH_DEDUP_REDIS_DB', 3)
if isinstance(pub_dedup_redis_db, str):
    pub_dedup_redis_db = eval(pub_dedup_redis_db)
record_expiry_in_sec = os.environ.get('ULCA_PUBLISH_RECORD_EXPIRY_IN_SEC', 86400)
if isinstance(record_expiry_in_sec, str):
    record_expiry_in_sec = eval(record_expiry_in_sec)
zip_chunk_size = os.environ.get('ULCA_PUBLISH_ZIP_CHUNK_SIZE', 100000)
if isinstance(zip_chunk_size, str):
    zip_chunk_size = eval(zip_chunk_size)
shared_storage_path = os.environ.get('ULCA_SEARCH_SHARED_STORAGE_PATH', '/opt/')

asr_immutable_keys = ["_id", "id", "audioFilename", "text", "audioHash", "textHash", "datasetType", "sourceLanguage",
                      "fileLocation", "lastModifiedOn", "createdOn","imageHash","imageFileName", "imageFileLocation"]
asr_non_tag_keys = ["_id", "id", "startTime", "endTime", "samplingRate", "audioFilename", "text", "submitter",
                    "fileLocation", "durationInSeconds", "duration", "lastModifiedOn", "createdOn", "age", "languagesSpoken","exactAge"]
asr_search_ignore_keys = ["_id", "id", "tags", "datasetType", "audioHash", "textHash",
                          "fileLocation", "imageFileLocation","lastModifiedOn", "createdOn", "version", "datasetId","imageHash","imageFilename"]
asr_updatable_keys = ["durationInSeconds", "duration", "version"]

tts_immutable_keys = ["_id", "id", "audioFilename", "text", "audioHash", "textHash", "datasetType", "sourceLanguage",
                      "fileLocation", "lastModifiedOn", "createdOn"]
tts_non_tag_keys = ["_id", "id", "startTime", "endTime", "samplingRate", "audioFilename", "text", "submitter",
                    "fileLocation", "durationInSeconds", "duration", "lastModifiedOn", "createdOn", "age"]
tts_search_ignore_keys = ["_id", "id", "tags", "datasetType", "audioHash", "textHash",
                          "fileLocation", "lastModifiedOn", "createdOn", "version", "datasetId"]
tts_updatable_keys = ["durationInSeconds", "duration", "version"]

asr_unlabeled_immutable_keys = ["_id", "id", "audioFilename", "audioHash", "datasetType", "sourceLanguage",
                                "fileLocation", "lastModifiedOn", "createdOn","imageHash","imageFileName", "imageFileLocation"]
asr_unlabeled_non_tag_keys = ["_id", "id", "startTime", "endTime", "samplingRate", "audioFilename", "text", "submitter",
                              "fileLocation", "durationInSeconds", "duration", "lastModifiedOn", "createdOn", "age", "languagesSpoken","exactAge"]
asr_unlabeled_search_ignore_keys = ["_id", "id", "tags", "datasetType", "audioHash",
                                    "fileLocation", "lastModifiedOn", "createdOn", "datasetId","imageHash", "imageFileLocation","imageFilename"]
asr_unlabeled_updatable_keys = ["durationInSeconds", "duration", "version"]

parallel_immutable_keys = ["_id", "id", "sourceText", "targetText", "sourceTextHash", "targetTextHash",
                           "sourceLanguage", "targetLanguage", "datasetType", "lastModifiedOn", "createdOn"]
parallel_non_tag_keys = ["_id", "id", "alignmentScore", "sourceText", "targetText", "submitter", "lastModifiedOn",
                         "createdOn"]
parallel_search_ignore_keys = ["_id", "id", "tags",  "datasetType", "hashedKey", "sk",
                               "derived", "sourceTextHash", "targetTextHash", "lastModifiedOn", "createdOn", "version",
                               "datasetId", "sourceLanguage", "targetLanguage"]
parallel_updatable_keys = ["alignmentScore", "version"]
parallel_derived_keys = ["submitter", "collectionMethod"]
parallel_dataset_submitter = [{"submitter": {"name": "Derived", "aboutMe": "Record derived from associative pairs"}}]
parallel_dataset_collection_method = [{"collectionMethod": {"collectionDescription": ["Derived"]}}]

ocr_immutable_keys = ["_id", "id", "imageFilename", "groundTruth", "imageHash", "groundTruthHash", "datasetType",
                      "sourceLanguage", "fileLocation", "lastModifiedOn", "createdOn"]
ocr_non_tag_keys = ["_id", "id", "boundingBox", "imageFilename", "groundTruth", "imageFilePath", "submitter",
                    "fileLocation", "lastModifiedOn", "createdOn"]
ocr_search_ignore_keys = ["_id", "id", "tags", "datasetType", "imageHash",
                          "groundTruthHash", "fileLocation", "lastModifiedOn", "createdOn", "version", "datasetId"]
ocr_updatable_keys = ["version"]

mono_immutable_keys = ["_id", "id", "text", "textHash", "datasetType", "sourceLanguage", "lastModifiedOn", "createdOn"]
mono_non_tag_keys = ["_id", "id", "text", "submitter", "lastModifiedOn", "createdOn"]
mono_search_ignore_keys = ["_id", "id", "tags", "datasetType", "textHash",
                           "lastModifiedOn", "createdOn", "version", "datasetId"]
mono_updatable_keys = ["version"]

transliteration_immutable_keys = ["_id", "id", "sourceText", "targetText", "sourceTextHash", "targetTextHash",
                           "sourceLanguage", "targetLanguage", "datasetType", "lastModifiedOn", "createdOn"]
transliteration_non_tag_keys = ["_id", "id", "alignmentScore", "sourceText", "targetText", "submitter", "lastModifiedOn",
                         "createdOn"]
transliteration_search_ignore_keys = ["_id", "id", "tags", "datasetType", "hashedKey", "sk",
                               "sourceTextHash", "targetTextHash", "lastModifiedOn", "createdOn", "version",
                               "datasetId", "sourceLanguage", "targetLanguage"]
transliteration_updatable_keys = ["alignmentScore", "version"]

glossary_immutable_keys = ["_id", "id", "sourceText", "targetText", "sourceTextHash", "targetTextHash",
                           "sourceLanguage", "targetLanguage", "datasetType", "lastModifiedOn", "createdOn"]
glossary_non_tag_keys = ["_id", "id", "alignmentScore", "sourceText", "targetText", "submitter", "lastModifiedOn",
                         "createdOn"]
glossary_search_ignore_keys = ["_id", "id", "tags", "datasetType", "hashedKey", "sk",
                               "derived", "sourceTextHash", "targetTextHash", "lastModifiedOn", "createdOn", "version",
                               "datasetId", "sourceLanguage", "targetLanguage"]
glossary_updatable_keys = ["alignmentScore", "version"]

govt_data_whitelist_enabled = os.environ.get('ULCA_PUBLISH_GOVT_DATA_WHITELIST_ENABLED', True)
if isinstance(govt_data_whitelist_enabled, str):
    if govt_data_whitelist_enabled == "TRUE":
        govt_data_whitelist_enabled = True
    else:
        govt_data_whitelist_enabled = False
govt_cs = ["ac.in", "gov.in", "nic.in", "org", "edu.in", "org.in"]

#whitelist data based on submitername 
submiter_name_whitelist_enabled = os.environ.get('ULCA_PUBLISH_SUBMITTER_NAME_WHITELIST_ENABLED', True)
if isinstance(submiter_name_whitelist_enabled, str):
    if submiter_name_whitelist_enabled == "TRUE":
        submiter_name_whitelist_enabled = True
    else:
        submiter_name_whitelist_enabled = False
submitter_names_to_whitelist = ["IISc"]

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
publish_search_consumer_grp = os.environ.get('KAFKA_ULCA_DS_PUBLISH_SEARCH_CONSUMER_GRP',
                                             'ulca-ds-publish-search-consumer-group-v0')
error_event_input_topic = os.environ.get('KAFKA_ULCA_DS_ERROR_IP_TOPIC', 'ulca-ds-error-ip-v0')
metric_event_input_topic = os.environ.get('KAFKA_ULCA_DS_BIEVENT_TOPIC', 'org-ulca-bievent-dataset-v3')
notifier_input_topic = os.environ.get('KAFKA_ULCA_NOTIFIER_CONSUMER_IP_TOPIC', 'ulca-notifier-ip-v0')
ulca_dataset_topic_partitions = os.environ.get('KAFKA_ULCA_DS_TOPIC_PARTITIONS', 12)
if isinstance(ulca_dataset_topic_partitions, str):
    ulca_dataset_topic_partitions = eval(ulca_dataset_topic_partitions)

ocr_prefix = os.environ.get('ULCA_OS_OCR_PREFIX', 'ocr')
asr_prefix = os.environ.get('ULCA_OS_ASR_PREFIX', 'asr')
tts_prefix = os.environ.get('ULCA_OS_TTS_PREFIX', 'tts')
asr_unlabeled_prefix = os.environ.get('ULCA_OS_ASR_UNLABELED_PREFIX', 'asr-unlabeled')
dataset_prefix = os.environ.get('ULCA_OS_DATASET_PREFIX', 'datasets')
error_prefix = os.environ.get('ULCA_OS_ERROR_PREFIX', 'errors')

dataset_type_parallel = os.environ.get('DS_TYPE_PARALLEL', 'parallel-corpus')
dataset_type_asr = os.environ.get('DS_TYPE_ASR', 'asr-corpus')
dataset_type_tts = os.environ.get('DS_TYPE_TTS', 'tts-corpus')
dataset_type_asr_unlabeled = os.environ.get('DS_TYPE_ASR_UNLABELED', 'asr-unlabeled-corpus')
dataset_type_ocr = os.environ.get('DS_TYPE_OCR', 'ocr-corpus')
dataset_type_monolingual = os.environ.get('DS_TYPE_MONOLINGUAL', 'monolingual-corpus')
dataset_type_transliteration = os.environ.get('DS_TYPE_TRANSLITERATION', 'transliteration-corpus')
dataset_type_glossary = os.environ.get('DS_TYPE_GLOSSARY', 'glossary-corpus')
dataset_type_ner = os.environ.get('DS_TYPE_NER', 'ner-corpus')

user_mode_pseudo = os.environ.get('USER_MODE_PSEUDO', 'precheck')
user_mode_real = os.environ.get('USER_MODE_REAL', 'real')

file_store_host = os.environ.get('ULCA_FILE_STORE_SERVER_URL', 'http://file-store:5001')
file_store_upload_endpoint = os.environ.get('ULCA_FILE_STORE_UPLOAD', '/ulca/file-store/v0/file/upload')
file_store_delete_endpoint = os.environ.get('ULCA_FILE_STORE_REMOVE', '/ulca/file-store/v0/file/remove')

notifier_search_complete_status = os.environ.get('ULCA_NOTIFIER_DS_SEARCH_COMPLETED_STATUS', 'search-records-completed')
