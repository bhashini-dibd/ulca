import threading
from threading import Thread
import logging
from azure.storage.blob import BlobServiceClient, BlobClient
from config import azure_link_prefix,azure_connection_string,azure_container_name,download_folder
from utilities import post_error
import os
from azure.storage.blob import ContainerClient


log = logging.getLogger('file')

class AzureFileRepo():

    def __init__(self):
        pass
    
    #uploading file to blob storage
    def upload_file_to_blob(self,file_path, file_name, folder):
        blob_file_name = folder + "/" + file_name
        blob_service_client =  BlobServiceClient.from_connection_string(azure_connection_string)
        blob_client = blob_service_client.get_blob_client(container=azure_container_name, blob=blob_file_name)
        log.info(f'Pushing {file_path} to azure at {blob_file_name} on a new fork......')
        persister = threading.Thread(target=self.upload_file, args=(blob_client,file_path))
        persister.start()
        return f'{azure_link_prefix}{blob_file_name}'

    #downloadin file from blob storage
    def download_file_from_blob(self, blob_file_name):
        blob_service_client =  BlobServiceClient.from_connection_string(azure_connection_string)
        blob_client = blob_service_client.get_blob_client(container=azure_container_name, blob=blob_file_name)
        try:
            output_filepath = os.path.join( download_folder, blob_file_name)
            log.info("\nDownloading blob to \n\t" + output_filepath)
            with open(output_filepath, "wb") as download_file:
                download_file.write(blob_client.download_blob().readall())
            return output_filepath
        except Exception as e:
            log.exception(e)
            return post_error("Service Exception",f"Exception occurred:{e}")

    #removing file from blob storage
    def remove_file_from_blob(self, blob_file_name):
        log.info(f'Deleting {blob_file_name} from blob storage......')
        container_client = ContainerClient.from_connection_string(conn_str=azure_connection_string, container_name=azure_container_name)
        try:
            container_client.delete_blob(blob=blob_file_name)
        except Exception as e:
            log.exception(e)
            return post_error("Service Exception",f"Exception occurred:{e}")


    def upload_file(self,blob_client,file_path):
        try:
            with open(file_path, "rb") as data:
                blob_client.upload_blob(data,overwrite=True)
            os.remove(file_path)
        except Exception as e:
            log.exception(f'Exception while pushing to azure blob storage: {e}', e)