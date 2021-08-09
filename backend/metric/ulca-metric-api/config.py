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
