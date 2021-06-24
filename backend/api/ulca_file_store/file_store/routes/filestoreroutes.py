from flask import Blueprint
from flask_restful import Api
from resources import FileUploaderResource,FileDownloaderResource,FileRemoverResource

# end-point for independent service
FILE_STORE_BLUEPRINT = Blueprint("file_store", __name__)

Api(FILE_STORE_BLUEPRINT).add_resource(FileUploaderResource, "/vo/file/upload")

Api(FILE_STORE_BLUEPRINT).add_resource(FileDownloaderResource, "/vo/file/download")

Api(FILE_STORE_BLUEPRINT).add_resource(FileRemoverResource, "/vo/file/remove")