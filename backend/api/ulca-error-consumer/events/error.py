import logging
import threading
from logging.config import dictConfig
from configs.configs import shared_storage_path,error_prefix
from .errorrepo import ErrorRepo
from utils.datasetutils import DatasetUtils
from datetime import datetime

log = logging.getLogger('file')
mongo_instance = None
error_repo = ErrorRepo()
utils = DatasetUtils()

class ErrorEvent:
    def __init__(self):
        pass
    
    #writing errors to mongostore
    def write_error_in_store(self,data):
        log.info(f'Writing error for SRN -- {data["serviceRequestNumber"]}')
        try:
            error_rec = {'serviceRequestNumber':data['serviceRequestNumber'],'stage':data['stage'],'message':data['message']}
            error_repo.upsert(error_rec)
        except Exception as e:
            log.exception(f'Exception while writing errors: {e}')
            return False

    
    def get_error_report(self, srn, internal):
        try:
            #fetches back error record (object store link) from db
            query = {"serviceRequestNumber": srn,"uploaded":True}
            exclude = {"_id": False}
            log.info(f'Search for error reports of SRN -- {srn} from db started')
            error_record = error_repo.search(query, exclude, None, None)
            if error_record:
                return error_record
            else:
                #searching for error records
                query = {"serviceRequestNumber": srn}
                exclude = {"_id": False}
                log.info(f'Search for error reports of SRN -- {srn} from db started')
                error_records = error_repo.search(query, exclude, None, None)
                log.info(f'Returned {len(error_records)} records')
                file = f'{shared_storage_path}error-{error_records[0]["datasetName"].replace(" ","-")}-{srn}.csv'
                utils.create_csv(error_records,file,srn)
                error_record = self.upload_error_to_object_store(srn,file,len(error_records))
                return error_record
            
        except Exception as e:
            log.exception(f'Exception while fetching error report: {e}')
            return []

    
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