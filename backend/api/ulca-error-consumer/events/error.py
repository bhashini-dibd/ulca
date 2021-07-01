import csv
import json
import logging
import os
import threading
import uuid
from datetime import datetime
from logging.config import dictConfig
from configs.configs import shared_storage_path, error_prefix, pt_publish_tool, redis_key_expiry
from .errorrepo import ErrorRepo
from utils.datasetutils import DatasetUtils
from service.cronrepo import StoreRepo


log = logging.getLogger('file')
mongo_instance = None
error_repo = ErrorRepo()
utils = DatasetUtils()
store_repo = StoreRepo()

class ErrorEvent:
    def __init__(self):
        pass

    def write_error(self, data):
        log.info(f'Writing error for SRN -- {data["serviceRequestNumber"]}')
        try:
            error_id = data["serviceRequestNumber"]+'.'+str(uuid.uuid4())
            expiry_time = redis_key_expiry
            store_repo.upsert(error_id,data,expiry_time)
        except Exception as e:
            log.exception(f'Exception while writing errors: {e}', e)
            return False

    def search_error_report(self, srn, internal):
        try:
            query = {"serviceRequestNumber": srn,"uploaded":True}
            exclude = {"_id": False}
            log.info(f'Search for error reports of SRN -- {srn} from db started')
            error_records = error_repo.search(query, exclude, None, None)
            log.info(f'Error report returned for {srn}')
            return error_records
        except Exception as e:
            log.exception(f'Exception while fetching error report: {e}', e)
            return []

# Log config
dictConfig({
    'version': 1,
    'formatters': {'default': {
        'format': '[%(asctime)s] {%(filename)s:%(lineno)d} %(threadName)s %(levelname)s in %(module)s: %(message)s',
    }},
    'handlers': {
        'info': {
            'class': 'logging.FileHandler',
            'level': 'DEBUG',
            'formatter': 'default',
            'filename': 'info.log'
        },
        'console': {
            'class': 'logging.StreamHandler',
            'level': 'DEBUG',
            'formatter': 'default',
            'stream': 'ext://sys.stdout',
        }
    },
    'loggers': {
        'file': {
            'level': 'DEBUG',
            'handlers': ['info', 'console'],
            'propagate': ''
        }
    },
    'root': {
        'level': 'DEBUG',
        'handlers': ['info', 'console']
    }
})