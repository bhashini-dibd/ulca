import os

DEBUG = True
API_URL_PREFIX = "/ulca/apis/"
HOST = '0.0.0.0'
PORT = 5001
#DRUID_DB_SCHEMA         =   os.environ.get('MATRIC_DRUID_DB_SCHEMA', 'dataset-training-v9')
DRUID_DB_SCHEMA     = 'dataset-training-v9'
TIME_CONVERSION_VAL     =   os.environ.get('ASR_DATA_CONERSION_VAL',3600)
if isinstance(TIME_CONVERSION_VAL, str):
    TIME_CONVERSION_VAL  =  eval(TIME_CONVERSION_VAL)
DRUID_CONNECTION_URL      = os.environ.get('DRUID_CLUSTER_URL', 'druid://localhost:8082/druid/v2/sql/')
ENABLE_CORS = False

#gmail server configs
MAIL_SETTINGS               =   {
                                "MAIL_SERVER"   : os.environ.get('ULCA_EMAIL_SERVER','smtp.gmail.com'),
                                "MAIL_PORT"     : eval(os.environ.get('ULCA_EMAIL_SECURE_PORT','587')),
                                "MAIL_USE_TLS"  : False,
                                "MAIL_USE_SSL"  : True,
                                #"MAIL_USERNAME" : os.environ.get('ULCA_EMAIL','notifer.tester@gmail.com'),
                                "MAIL_USERNAME" : 'notifier.tester12@gmail.com',
                                "MAIL_PASSWORD" : 'qpmpdjdidbgecewa'
                                #"MAIL_PASSWORD" : os.environ.get('ULCA_EMAIL_PASSWORD','Welcome@123')
                                }
#MAIL_SENDER                 =   os.environ.get('ULCA_SENDER_EMAIL','ulca@tarento.com')#
MAIL_SENDER                 = 'ulca@tarento.com'

#receiver_email_ids          =   os.environ.get('ULCA_DASHBOARD_COUNT_EMAIL_TO_LIST',"siddanth.shaiva@tarento.com")
receiver_email_ids          =   'siddanth.shaiva@tarento.com'
#ulca_email_group            =   os.environ.get('ULCA_EMAIL_GROUP','siddanth.shaiva@tarento.com')
ulca_email_group            =   'siddanth.shaiva@tarento.com'
data_connection_url         =   os.environ.get('ULCA_DS_PUBLISH_MONGO_CLUSTER', 'mongodb://localhost:27017')
process_connection_url      =   os.environ.get('ULCA_MONGO_CLUSTER', 'mongodb://localhost:27017')
data_db_schema              =   os.environ.get('DATA_PARALLEL', 'ulca')
data_parallel               =   os.environ.get('DATA_SCHEMA', 'parallel-dataset')
data_ocr                    =   os.environ.get('DATA_OCR', 'ocr-dataset')
data_mono                   =   os.environ.get('DATA_MONO', 'monolingual-dataset') 
data_asr                    =   os.environ.get('DATA_ASR', 'asr-dataset')
data_asr_unlabeled          =   os.environ.get('DATA_ASR_UNLABELED', 'asr-unlabeled-dataset') 
data_tts                    =   os.environ.get('DATA_TTS', 'tts-dataset')

process_db_schema           =   os.environ.get('PROCESS_DB','ulca-process-tracker')
process_col                 =   os.environ.get('PROCESS_COL','ulca-pt-processes')
tasks_col                   =   os.environ.get('TASKS_COL','ulca-pt-tasks')

metric_cron_interval_sec     =  60
filter_cron_interval_sec     =   os.environ.get('FILTER_CRON_INTERVAL_SEC',300)#14400
if isinstance(filter_cron_interval_sec, str):
    filter_cron_interval_sec =  eval(filter_cron_interval_sec)

status_cron_interval_sec     =   os.environ.get('STATUS_UPDATER_CRON_INTERVAL_SEC',17200)
if isinstance(status_cron_interval_sec, str):
    status_cron_interval_sec =  eval(status_cron_interval_sec)


data_filter_set_file_path   =   os.environ.get('GIT_DATA_FILTER_PARAMS_FILE','https://raw.githubusercontent.com/ULCA-IN/ulca/develop/master-data/dev/datasetFilterParams.json')
filter_dir_name             =   os.environ.get('FILTER_DIR_NAME','/app/utilities/')
shared_storage_path         =   os.environ.get('ULCA_SHARED_STORAGE_PATH', "/opt/")
filter_file_name            =   os.environ.get('FILTER_FILE_NAME','datasetFilterParams.json')

file_store_host             =   os.environ.get('ULCA_FILE_STORE_SERVER_URL', 'http://file-store:5001')
file_store_upload_endpoint  =   os.environ.get('ULCA_FILE_STORE_UPLOAD', '/ulca/file-store/v0/file/upload')
data_metric_host            =   os.environ.get('ULCA_DATA_METRIC_SERVER_URL', 'http://data-metric:5001')#
data_metric_endpoint        =   os.environ.get('ULCA_DATA_METRIC_SEARCH', '/ulca/data-metric/v0/store/search')
pending_jobs_duration       =   os.environ.get('PENDING_JOBS_DURATION_HRS',72)
queued_pending_duration     =   os.environ.get('QUEUED_JOBS_DURATION_HRS',120)

if isinstance(pending_jobs_duration, str):
    pending_jobs_duration =  eval(pending_jobs_duration)