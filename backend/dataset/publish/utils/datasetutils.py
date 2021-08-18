import hashlib
import json
import logging
import os
from logging.config import dictConfig

import requests

from configs.configs import shared_storage_path, dataset_prefix, file_store_host, file_store_upload_endpoint

log = logging.getLogger('file')

mongo_instance = None

class DatasetUtils:
    def __init__(self):
        pass

    # Utility to get tags out of an object
    def get_tags(self, d):
        for v in d.values():
            if not v:
                continue
            if isinstance(v, dict):
                yield from self.get_tags(v)
            elif isinstance(v, list):
                for entry in v:
                    if isinstance(entry, dict):
                        yield from self.get_tags(entry)
                    elif isinstance(entry, int) or isinstance(entry, float):
                        continue
                    else:
                        yield entry
            elif isinstance(v, int) or isinstance(v, float):
                continue
            else:
                yield v

    # Method to push search results to object store
    def push_result_to_object_store(self, result, service_req_no, size):
        log.info(f'Writing results and sample to Object Store......')
        try:
            res_path = f'{shared_storage_path}{service_req_no}-ds.json'
            with open(res_path, 'w') as f:
                json.dump(result, f)
            res_path_sample = f'{shared_storage_path}{service_req_no}-sample-ds.json'
            with open(res_path_sample, 'w') as f:
                json.dump(result[:size], f)
            log.info(f'Publishing results and sample to Object Store......')
            res_path_os = self.upload_file(res_path, dataset_prefix, f'{service_req_no}-ds.json')
            res_path_sample_os = self.upload_file(res_path_sample, dataset_prefix, f'{service_req_no}-sample-ds.json')
            return res_path_os, res_path_sample_os
        except Exception as e:
            log.exception(f'Exception while pushing search results to object store: {e}', e)
            return False, False

    # Utility to hash a file
    def hash_file(self, filename):
        h = hashlib.sha256()
        try:
            with open(filename, 'rb') as file:
                chunk = 0
                while chunk != b'':
                    chunk = file.read(1024)
                    h.update(chunk)
            return h.hexdigest()
        except Exception as e:
            log.exception(e)
            return None

    # Utility to upload files to ULCA Object store
    def upload_file(self, file_location, folder, file_name):
        log.info(f'Uploading file to the Object Store......')
        file_store_req = {"fileLocation": file_location, "storageFolder": folder, "fileName": file_name}
        uri = f'{file_store_host}{file_store_upload_endpoint}'
        try:
            file_store_res = self.call_api(uri, file_store_req, "user_id")
            if file_store_res:
                return file_store_res["data"]
            else:
                return None
        except Exception as e:
            log.exception(f'Exception while uploading using filestore: {e}', e)
            return None

    # Util method to make an API call and fetch the result
    def call_api(self, uri, api_input, user_id):
        try:
            log.info(f'URI: {uri}')
            api_headers = {'userid': user_id, 'x-user-id': user_id, 'Content-Type': 'application/json'}
            response = requests.post(url=uri, json=api_input, headers=api_headers)
            if response is not None:
                if response.text is not None:
                    log.info(response.text)
                    data = json.loads(response.text)
                    return data
                else:
                    log.error("API response was None !")
                    return None
            else:
                log.error("API call failed!")
                return None
        except Exception as e:
            log.exception(f'Exception while making the api call: {str(e)}')
            return None

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