import hashlib
import json
import logging
import os
from logging.config import dictConfig

import boto3 as boto3
from azure.storage.blob import BlobServiceClient

from configs.configs import aws_access_key, aws_secret_key, aws_bucket_name, shared_storage_path, dataset_prefix, \
    aws_link_prefix, object_store, azure_connection_string, azure_link_prefix, azure_container_name, azure_account_name

log = logging.getLogger('file')

mongo_instance = None

class DatasetUtils:
    def __init__(self):
        self.blob_service_client = BlobServiceClient.from_connection_string(azure_connection_string)
        self.s3_client = boto3.client('s3', aws_access_key_id=aws_access_key, aws_secret_access_key=aws_secret_key)
        try:
            container_client = self.blob_service_client.create_container(azure_container_name)
            if container_client:
                log.info(f'Azure container: {azure_container_name} already exists!')
            else:
                log.info(f'Azure container: {azure_container_name} CREATED!')
        except Exception as e:
            log.exception(f'Exception while creating Azure container: {e}',e)
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

    def push_result_to_object_store(self, result, service_req_no, size):
        log.info(f'Pushing results and sample to Object Store......')
        try:
            res_path = f'{shared_storage_path}{service_req_no}-ds.json'
            with open(res_path, 'w') as f:
                json.dump(result, f)
            res_path_os = f'{dataset_prefix}{service_req_no}-ds.json'
            upload = self.upload_file(res_path, res_path_os)
            res_path_sample = f'{shared_storage_path}{service_req_no}-sample-ds.json'
            with open(res_path_sample, 'w') as f:
                json.dump(result[:size], f)
            res_path_sample_os = f'{dataset_prefix}{service_req_no}-sample-ds.json'
            upload = self.upload_file(res_path_sample, res_path_sample_os)
            if upload:
                os.remove(res_path)
                os.remove(res_path_sample)
                return f'{aws_link_prefix}{res_path_os}', f'{aws_link_prefix}{res_path_sample_os}'
            else:
                return False, False
        except Exception as e:
            log.exception(f'Exception while pushing search results to s3: {e}', e)
            return False, False

    def delete_from_s3(self, file):
        log.info(f'Deleting {file} from S3......')
        s3_client = boto3.client('s3', aws_access_key_id=aws_access_key, aws_secret_access_key=aws_secret_key)
        try:
            objects = [{"Key": file}]
            response = s3_client.delete_objects(Bucket=aws_bucket_name, Delete={"Objects": objects})
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
    def upload_file(self, file_name, os_file_name):
        if object_store == "AWS":
            return self.upload_file_s3(file_name, os_file_name)
        if object_store == "Azure":
            return self.upload_file_azure(file_name, os_file_name)

    # Utility to upload files to ULCA S3 Bucket
    def upload_file_s3(self, file_name, s3_file_name):
        if s3_file_name is None:
            s3_file_name = file_name
        log.info(f'Pushing {file_name} to S3 at {s3_file_name} ......')
        try:
            self.s3_client.upload_file(file_name, aws_bucket_name, s3_file_name)
            return f'{aws_link_prefix}{s3_file_name}'
        except Exception as e:
            log.exception(f'Exception while pushing to s3: {e}', e)
        return False

    # Utility to upload files to ULCA Azure Blob storage
    def upload_file_azure(self, file_name, blob_file_name):
        if blob_file_name is None:
            blob_file_name = file_name
        log.info(f'Pushing {file_name} to Azure at {blob_file_name} ......')
        blob_client = self.blob_service_client.get_blob_client(container=azure_container_name, blob=blob_file_name)
        try:
            with open(file_name, "rb") as data:
                blob_client.upload_blob(data, overwrite=True)
            return f'{azure_link_prefix}{blob_file_name}'
        except Exception as e:
            log.exception(f'Exception while pushing to Azure: {e}', e)
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