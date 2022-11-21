import os
import time

DEBUG           = False
API_URL_PREFIX  = "/ulca/data-metric"
HOST            = '0.0.0.0'
PORT            = 5001
ENABLE_CORS     = False

# mongodb configs
MONGO_DB_SCHEMA         =   os.environ.get('ULCA_MODEL_DB','ulca-process-tracker')
MONGO_MODEL_COLLECTION  =   os.environ.get('ULCA_MODEL_COL','model')
MONGO_BM_COLLECTION     =   os.environ.get('ULCA_BM_COL','benchmark')
MONGO_CONNECTION_URL    =   os.environ.get('ULCA_MONGO_CLUSTER', 'mongodb://localhost:27017')#
#druid store configs
DRUID_DB_SCHEMA         =   os.environ.get('MATRIC_DRUID_DB_SCHEMA', 'dataset-training-v5')
DRUID_CONNECTION_URL    =   os.environ.get('DRUID_CLUSTER_URL', 'druid://localhost:8082/druid/v2/sql/')

TIME_CONVERSION_VAL     =   os.environ.get('ASR_DATA_CONERSION_VAL',3600)
if isinstance(TIME_CONVERSION_VAL, str):
    TIME_CONVERSION_VAL  =  eval(TIME_CONVERSION_VAL)

metric_cron_interval_sec     =   os.environ.get('METRIC_CRON_INTERVAL_SEC',180)#
if isinstance(metric_cron_interval_sec, str):
    metric_cron_interval_sec  =  eval(metric_cron_interval_sec)

mismatch_cron_interval_sec     =   os.environ.get('MISMTACH_IDENTIFIER_CRON_INTERVAL_SEC',864000)#14400
if isinstance(mismatch_cron_interval_sec, str):
    mismatch_cron_interval_sec  =  eval(mismatch_cron_interval_sec)


data_connection_url         =   os.environ.get('ULCA_DS_PUBLISH_MONGO_CLUSTER', 'mongodb://localhost:27017')
data_db_schema              =   os.environ.get('DATA_PARALLEL', 'ulca')
data_parallel               =   os.environ.get('DATA_SCHEMA', 'parallel-dataset')
data_ocr                    =   os.environ.get('DATA_OCR', 'ocr-dataset')
data_mono                   =   os.environ.get('DATA_MONO', 'monolingual-dataset') 
data_asr                    =   os.environ.get('DATA_ASR', 'asr-dataset')
data_asr_unlabeled          =   os.environ.get('DATA_ASR_UNLABELED', 'asr-unlabeled-dataset')  


email_service_url           =   os.environ.get('UTILITY_SERVICE_NOTIFY_COUNT_URL','http://utility-service:5001//ulca/apis/v1/send/mail')
mismatch_email_service_url  =   os.environ.get('UTILITY_SERVICE_NOTIFY_MISMATCH_URL','http://utility-service:5001//ulca/apis/v1/notify/mismatch') 
mdms_bulk_fetch_url         =   os.environ.get('ULCA_MDMS_BULK_FETCH_URL','http://master-data-management:5001/ulca/mdms/v0/fetch-master/bulk')
