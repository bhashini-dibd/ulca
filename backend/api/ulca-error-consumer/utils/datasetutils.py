import json
import logging
from logging.config import dictConfig
from configs.configs import file_store_host, file_store_upload_endpoint
import requests

log = logging.getLogger('file')

mongo_instance = None

class DatasetUtils:
    def __init__(self):
        pass
    
    def file_store_upload_call(self, file,file_name ,folder_name):
        try:
            headers =   {"Content-Type": "application/json"}
            body    =   {"fileName":file_name,"storageFolder":folder_name,"fileLocation":file}
            request_url = file_store_host+file_store_upload_endpoint
            log.info("Intiating request to store data on object store %s"%request_url)
            response = requests.post(url=request_url, headers = headers, json = body)
            response_data = response.content
            log.info("Received data from upload end point of file store service")
            response = json.loads(response_data)
            return response["data"]
        except Exception as e:
            log.exception(f'Exception while pushing error file to object store: {e}')
        return False


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
