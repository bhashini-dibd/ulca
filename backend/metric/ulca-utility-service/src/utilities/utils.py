import requests
from config import data_filter_set_file_path,shared_storage_path,filter_file_name, file_store_host, file_store_upload_endpoint
import json
import logging
from logging.config import dictConfig
log = logging.getLogger('file')

class DataUtils:

    def read_from_config_file(self):
        """Reading filters from git config."""
        
        try:
            file = requests.get(data_filter_set_file_path, allow_redirects=True)
            file_path = shared_storage_path + filter_file_name
            # file_path = '/home/jainy/Desktop/datasetFilterParams.json'
            open(file_path, 'wb').write(file.content)
            log.info(f"Filters read from git and pushed to local {file_path}")
            with open(file_path, 'r') as stream:
                parsed = json.load(stream)
                filterconfigs = parsed['dataset']
            return filterconfigs, file_path
        except Exception as exc:
            log.exception("Exception while reading filters: " +str(exc))
            return None, None

    def write_to_config_file(self,filepath,data):
        try:
            with open (filepath,'w') as confile:
                json.dump(data,confile)
        except Exception as e:
            log.info(f"Exception while writing filter configs : {e}")



    def upload_to_object_store(self,file_path):
        """Uploading file to object store"""
        
        try:
            headers =   {"Content-Type": "application/json"}
            body    =   {"fileName":"datasetFilterParams.json","storageFolder":"error","fileLocation":file_path}
            request_url = file_store_host+file_store_upload_endpoint
            log.info("Intiating request to store data on object store %s"%request_url)
            response = requests.post(url=request_url, headers = headers, json = body)
            response_data = response.content
            log.info("Received data from upload end point of file store service")
            response = json.loads(response_data)
            if "data" not in response:
                return False
            log.info(response["data"])
        except Exception as e:
            log.exception(f'Exception while pushing config file to object store: {e}')
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