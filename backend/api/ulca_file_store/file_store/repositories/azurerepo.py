import logging
import boto3

log = logging.getLogger('file')

class AzureFileRepo():

    def upload_file_to_blob(self, file_name, s3_file_name):
        if s3_file_name is None:
            s3_file_name = file_name
        log.info(f'Pushing {file_name} to S3 at {s3_file_name} ......')
        s3_client = boto3.client('s3', aws_access_key_id=aws_access_key, aws_secret_access_key=aws_secret_key)
        try:
            s3_client.upload_file(file_name, aws_bucket_name, s3_file_name)
            return f'{aws_link_prefix}{s3_file_name}'
        except Exception as e:
            log.exception(f'Exception while pushing to s3: {e}', e)
        return False
    
    def download_file_from_blob(self, blob_file_name):
        s3_client = boto3.client('s3', aws_access_key_id=aws_access_key, aws_secret_access_key=aws_secret_key)
        try:
            response = s3_client.download_file(aws_bucket_name, s3_file_name)
            return response
        except Exception as e:
            log.exception(e)
            return False

    def remove_file_from_blob(self, s3_file_name):
        s3_client = boto3.client('s3', aws_access_key_id=aws_access_key, aws_secret_access_key=aws_secret_key)
        try:
            response = s3_client.download_file(aws_bucket_name, s3_file_name)
            return response
        except Exception as e:
            log.exception(e)
            return False