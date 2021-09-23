import logging
import threading
import uuid
from logging.config import dictConfig
from configs.configs import redis_key_expiry, shared_storage_path,error_prefix
from .errorrepo import ErrorRepo
from utils.datasetutils import DatasetUtils
from service.cronrepo import StoreRepo
from datetime import datetime

log = logging.getLogger('file')
mongo_instance = None
error_repo = ErrorRepo()
utils = DatasetUtils()
store_repo = StoreRepo()

class ErrorEvent:
    def __init__(self):
        pass
    
    #dumping errors onto redis store
    def write_error_in_redis(self, data):
        log.info(f'Writing error for SRN -- {data["serviceRequestNumber"]} in redis')
        try:
            error_id = data["serviceRequestNumber"]+'.'+str(uuid.uuid4())
            expiry_time = redis_key_expiry
            store_repo.upsert(error_id,data,expiry_time)
        except Exception as e:
            log.exception(f'Exception while writing errors: {e}')
            return False

    #fetches back error record (object store link) from db
    def get_error_report(self, srn, internal):
        try:
            query = {"serviceRequestNumber": srn,"uploaded":True}
            exclude = {"_id": False}
            log.info(f'Search for error reports of SRN -- {srn} from db started')
            error_record = error_repo.search(query, exclude, None, None)
            if error_record:
                return error_record
            return [{"consolidated_file":None,"count":None,"file":None,"serviceRequestNumber":srn,"time_stamp":None,"uploaded":None}]

        except Exception as e:
            log.exception(f'Exception while fetching error report: {e}')
            return []

    #writing errors to mongostore
    def write_error_in_mongo(self,data):
        log.info(f'Writing error for SRN -- {data["serviceRequestNumber"]} in mongo')
        try:
            error_rec = {'datasetName':data['datasetName'],'serviceRequestNumber':data['serviceRequestNumber'],'stage':data['stage'],'message':data['message']}
            error_repo.upsert(error_rec)
        except Exception as e:
            log.exception(f'Exception while writing errors: {e}')
            return False
    
    def upload_error_to_object_store(self,srn,file,error_records_count):
        file_name = str(file).replace("/opt/","")
        #initiating upload API call
        error_object_path = utils.file_store_upload_call(file,file_name,error_prefix)
        if error_object_path == False:
            return  None
        log.info(f'Error file uploaded on to object store : {error_object_path} for srn -- {srn} ')
        error_record = {"serviceRequestNumber": srn, "uploaded": True, "time_stamp": str(datetime.now()), "internal_file": file, "file": error_object_path, "count": error_records_count}
        persister = threading.Thread(target=self.update_db_status, args=(error_record,srn))
        persister.start()
        return error_record

    def update_db_status(self,record,srn):
        error_repo.remove({"serviceRequestNumber": srn})
        error_repo.insert(record)



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