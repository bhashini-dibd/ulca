import os
import time

DEBUG           = False
API_URL_PREFIX  = "/ulca/data-metric"
HOST            = '0.0.0.0'
PORT            = 5001
BASE_DIR        = 'upload'
download_folder = 'upload'
ENABLE_CORS     = False

# mongodb
MONGO_DB_SCHEMA         = os.environ.get('MONGO_DB_SCHEMA', 'sangrah')
MONGO_CONNECTION_URL    = os.environ.get('MONGO_CLUSTER_URL', 'mongodb://localhost:27017')

DRUID_DB_SCHEMA         = os.environ.get('MATRIC_DRUID_DB_SCHEMA', 'TEST2')
DRUID_CONNECTION_URL    = os.environ.get('DRUID_CLUSTER_URL', 'druid://localhost:8082/druid/v2/sql/')