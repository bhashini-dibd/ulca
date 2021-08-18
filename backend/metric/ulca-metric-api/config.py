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
MONGO_CONNECTION_URL    =   os.environ.get('ULCA_MONGO_CLUSTER', 'mongodb://localhost:27017')#
#druid store configs
DRUID_DB_SCHEMA         =   os.environ.get('MATRIC_DRUID_DB_SCHEMA', 'dataset-training-v5')
DRUID_CONNECTION_URL    =   os.environ.get('DRUID_CLUSTER_URL', 'druid://localhost:8082/druid/v2/sql/')#druid://localhost:8082/druid/v2/sql/

TIME_CONVERSION_VAL     =   os.environ.get('ASR_DATA_CONERSION_VAL',3600)
if isinstance(TIME_CONVERSION_VAL, str):
    TIME_CONVERSION_VAL  =  eval(TIME_CONVERSION_VAL)

DATA_FILTER_SET_FILE_PATH   =   os.environ.get('GIT_DATA_FILTER_PARAMS_FILE','https://raw.githubusercontent.com/project-anuvaad/ULCA/metric-api-feature/backend/metric/ulca-master-data-configs/datasetFilterParams.json')
FILTER_DIR_NAME             =   os.environ.get('FILTER_DIR_NAME','/app/utilities/')
FILTER_FILE_NAME            =   os.environ.get('FILTER_FILE_NAME','filters')

error_cron_interval_sec     =   os.environ.get('METRIC_CRON_INTERVAL_SEC',172800)#14400
if isinstance(error_cron_interval_sec, str):
    error_cron_interval_sec  =  eval(error_cron_interval_sec)

data_connection_url         =   os.environ.get('ULCA_DS_PUBLISH_MONGO_CLUSTER', 'mongodb://localhost:27017')
data_db_schema              =   os.environ.get('DATA_PARALLEL', 'ulca')
data_parallel               =   os.environ.get('DATA_SCHEMA', 'parallel-dataset')
data_ocr                    =   os.environ.get('DATA_OCR', 'ocr-dataset')
data_mono                   =   os.environ.get('DATA_MONO', 'monolingual-dataset') 
data_asr                    =   os.environ.get('DATA_ASR', 'asr-dataset')
data_asr_unlabeled          =   os.environ.get('DATA_ASR_UNLABELED', 'asr-unlabeled-dataset')  

MAIL_SETTINGS           =   {
                                "MAIL_SERVER"   : 'smtp.gmail.com',
                                "MAIL_PORT"     : 465,
                                "MAIL_USE_TLS"  : False,
                                "MAIL_USE_SSL"  : True,
                                "MAIL_USERNAME" : os.environ.get('ULCA_SUPPORT_EMAIL','xxx@gmail.com'),
                                "MAIL_PASSWORD" : os.environ.get('ULCA_SUPPORT_EMAIL_PASSWORD','xxx')
                            }


email_service_url             = os.environ.get('UTILITY_SERVICE_NOTIFY_COUNT_URL','http://utility-service:5001//ulca/apis/v1/send/mail')
