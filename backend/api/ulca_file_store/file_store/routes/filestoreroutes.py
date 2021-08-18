from flask import Blueprint
from flask_restful import Api
from resources import FileUploaderResource,FileDownloaderResource,FileRemoverResource

# end-point for independent service
FILE_STORE_BLUEPRINT = Blueprint("file_store", __name__)

#file upload end-point
Api(FILE_STORE_BLUEPRINT).add_resource(FileUploaderResource, "/v0/file/upload")
#file download end-point
Api(FILE_STORE_BLUEPRINT).add_resource(FileDownloaderResource, "/v0/file/download")
#file delete ebd-point
Api(FILE_STORE_BLUEPRINT).add_resource(FileRemoverResource, "/v0/file/remove")