import config
from utilities import post_error
from repositories import AwsFileRepo,AzureFileRepo
import logging

log         =   logging.getLogger('file')
awsrepo     =   AwsFileRepo()
azurerepo   =   AzureFileRepo()

class FileServices():

    #choosing upload mechanism as per config
    def upload_file(self,file_name,folder):
        #checking the file path received 
        if file_name.split('/')[1] != config.shared_storage_path:
            return post_error("Request Failed","filename received is corrupted")
        
        if config.ojectStore == "AWS":
            return awsrepo.upload_file_to_s3(file_name,folder)
        elif config.ojectStore == "AZURE":
            return  azurerepo.upload_file_to_blob(file_name,folder)
    
    def download_file(self,file_name):
        if config.ojectStore == "AWS":
            return awsrepo.download_file_from_s3(file_name)
        elif config.ojectStore == "AZURE":
            return  azurerepo.upload_file_to_blob(file_name)

    def remove_file(self,file_name):
        if config.ojectStore == "AWS":
            return awsrepo.remove_file_from_s3(file_name)
        elif config.ojectStore == "AZURE":
            return  azurerepo.remove_file_from_blob(file_name)
            

            
        
        



                

