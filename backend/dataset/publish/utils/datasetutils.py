import hashlib
import json
import logging
import os
from logging.config import dictConfig

import boto3 as boto3
from configs.configs import aws_access_key, aws_secret_key, aws_bucket_name, shared_storage_path, aws_dataset_prefix, aws_link_prefix

log = logging.getLogger('file')

mongo_instance = None

class DatasetUtils:
    def __init__(self):
        pass

    # Utility to get tags out of an object
    def get_tags(self, d):
        for v in d.values():
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

    def push_result_to_s3(self, result, service_req_no, size):
        log.info(f'Pushing results and sample to S3......')
        try:
            res_path = f'{shared_storage_path}{service_req_no}-ds.json'
            with open(res_path, 'w') as f:
                json.dump(result, f)
            res_path_aws = f'{aws_dataset_prefix}{service_req_no}-ds.json'
            upload = self.upload_file(res_path, res_path_aws)
            res_path_sample = f'{shared_storage_path}{service_req_no}-sample-ds.json'
            with open(res_path_sample, 'w') as f:
                json.dump(result[:size], f)
            res_path_sample_aws = f'{aws_dataset_prefix}{service_req_no}-sample-ds.json'
            upload = self.upload_file(res_path_sample, res_path_sample_aws)
            if upload:
                os.remove(res_path)
                os.remove(res_path_sample)
                return f'{aws_link_prefix}{res_path_aws}', f'{aws_link_prefix}{res_path_sample_aws}'
            else:
                return False, False
        except Exception as e:
            log.exception(f'Exception while pushing search results to s3: {e}', e)
            return False, False

    def delete_from_s3(self, file):
        s3_client = boto3.client('s3', aws_access_key_id=aws_access_key, aws_secret_access_key=aws_secret_key)
        try:
            response = s3_client.delete_file(aws_bucket_name, file)
            return response
        except Exception as e:
            log.exception(e)
            return False

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

    # Utility to upload files to ULCA S3 Bucket
    def upload_file(self, file_name, s3_file_name):
        log.info(f'Pushing file to S3......')
        if s3_file_name is None:
            s3_file_name = file_name
        s3_client = boto3.client('s3', aws_access_key_id=aws_access_key, aws_secret_access_key=aws_secret_key)
        try:
            response = s3_client.upload_file(file_name, aws_bucket_name, s3_file_name)
            if response:
                log.info(response)
            return f'{aws_link_prefix}{s3_file_name}'
        except Exception as e:
            log.exception(f'Exception while pushing to s3: {e}', e)
        return False

    # Utility to download files to ULCA S3 Bucket
    def download_file(self, s3_file_name):
        s3_client = boto3.client('s3', aws_access_key_id=aws_access_key, aws_secret_access_key=aws_secret_key)
        try:
            response = s3_client.download_file(aws_bucket_name, s3_file_name)
            return response
        except Exception as e:
            log.exception(e)
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