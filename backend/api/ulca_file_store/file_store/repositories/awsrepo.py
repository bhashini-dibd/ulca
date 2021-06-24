import logging
from utilities.response import post_error
import boto3 
from config import aws_access_key, aws_secret_key, aws_bucket_name,aws_link_prefix


log = logging.getLogger('file')

class AwsFileRepo():

    #uploading file to S3 bucket
    def upload_file_to_s3(self, file_name,folder):
        s3_file_name = folder + "/" + file_name.split('/')[2]
        log.info(f'Pushing {file_name} to S3 at {s3_file_name} ......')
        s3_client = boto3.client('s3', aws_access_key_id=aws_access_key, aws_secret_access_key=aws_secret_key)
        try:
            s3_client.upload_file(file_name, aws_bucket_name, s3_file_name)
            return f'{aws_link_prefix}{s3_file_name}'
        except Exception as e:
            log.exception(f'Exception while pushing to s3: {e}', e)
            return post_error("Service Exception",f"Exception occurred:{e}")

    def download_file_from_s3(self, s3_file_name):
        s3_client = boto3.client('s3', aws_access_key_id=aws_access_key, aws_secret_access_key=aws_secret_key)
        try:
            response = s3_client.download_file(aws_bucket_name, s3_file_name)
            return response
        except Exception as e:
            log.exception(e)
            return post_error("Service Exception",f"Exception occurred:{e}")

    def remove_file_from_s3(self, s3_file_name):
        s3_client = boto3.client('s3', aws_access_key_id=aws_access_key, aws_secret_access_key=aws_secret_key)
        try:
            response = s3_client.download_file(aws_bucket_name, s3_file_name)
            return response
        except Exception as e:
            log.exception(e)
            return post_error("Service Exception",f"Exception occurred:{e}")