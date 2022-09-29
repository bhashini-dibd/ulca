import config
from utilities import post_error
from repositories import AwsFileRepo,AzureFileRepo
import logging

log         =   logging.getLogger('file')
awsrepo     =   AwsFileRepo()
azurerepo   =   AzureFileRepo()

class FileServices():

    #choosing upload mechanism as per config
    def upload_file(self,file_path,file_name,folder):
        #checking the file path received 
        if file_path.split('/')[1] != config.shared_storage_path:
            return post_error("Request Failed","filename received is corrupted")
        log.info(config.object_store)
        if config.object_store == "AWS":
            log.info("objectStore ----------- AWS")
            return awsrepo.upload_file_to_s3(file_path,file_name,folder)
        elif config.object_store == "AZURE":
            log.info("objectStore ----------- AZURE")
            return  azurerepo.upload_file_to_blob(file_path,file_name,folder)

    #choosing donload mechanism as per config
    def download_file(self,file_name):
        if config.object_store == "AWS":
            log.info("objectStore ------------ AWS")
            return awsrepo.download_file_from_s3(file_name)
        elif config.object_store == "AZURE":
            log.info("objectStore ----------- AZURE")
            return  azurerepo.upload_file_to_blob(file_name)

    #choosing delete mechanism as per config
    def remove_file(self,file_name):
        if config.object_store == "AWS":
            log.info("objectStore ------------- AWS")
            return awsrepo.remove_file_from_s3(file_name)
        elif config.object_store == "AZURE":
            log.info("objectStore ----------- AZURE")
            return  azurerepo.remove_file_from_blob(file_name)
            

            
        
        



                

