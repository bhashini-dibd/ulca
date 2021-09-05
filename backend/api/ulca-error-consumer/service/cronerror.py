import threading
from threading import Thread
from configs.configs import error_cron_interval_sec, shared_storage_path, error_prefix, error_batch_size
from .cronrepo import StoreRepo
import logging
from events.errorrepo import ErrorRepo
from utils.cronjobutils import StoreUtils
import os
from datetime import datetime
from logging.config import dictConfig


log         =   logging.getLogger('file')
storerepo   =   StoreRepo()
errorepo    =   ErrorRepo()
storeutils  =   StoreUtils()


class ErrorProcessor(Thread):
    def __init__(self, event):
        Thread.__init__(self)
        self.stopped = event

    # Cron JOB to fetch error records from redis store and to save on object store using file-store API
    def run(self):
        run = 0
        while not self.stopped.wait(eval(str(error_cron_interval_sec))):
            log.info(f'Error Processor run :{run}')
            try:
                log.info('Fetching SRNs from redis store')
                srn_list = storerepo.get_unique_srns()
                if srn_list:
                    log.info(f'Error records on {len(srn_list)} SRNs present in redis store')
                    log.info(f'Error processing initiated --------------- run : {run}')
                    self.initiate_error_processing(srn_list)
                    log.info(f'Error Processing completed --------------- run : {run}')
                else:
                    log.info('Received 0 SRNs from redis store')
                run += 1
            except Exception as e:
                run += 1
                log.exception(f'Exception on ErrorProcessor on run : {run} , exception : {e}')

    #fetching all error records from redis, and passing on to upload service
    def initiate_error_processing(self,srn_list):
        try:
            for srn in srn_list:
                log.info(f'Processing errors for srn -- {srn}')
                query   = {"serviceRequestNumber":srn}
                exclude = {"_id":0}
                #getting record from mongo matching srn, if present
                uploaded_record = errorepo.search(query,exclude,None,None)
                uploaded_count = 0
                if uploaded_record:
                    #count of previously uploaded errors
                    uploaded_count =uploaded_record[0]["count"]
                log.info(f'{uploaded_count} record/s were uploaded previously on to object store for srn -- {srn}')
                #srn.uuid - pattern of all keys in redis
                pattern = f'{srn}.*'
                #getting all keys matching the patterns
                error_records_keys = storerepo.get_keys_matching_pattern(pattern)
                #counting keys matching the pattern, to get the count of error records on redis store against respective srn
                error_records_count = len(error_records_keys)
                log.info(f'{error_records_count} records found in redis store for srn -- {srn}')
                #if both mongo and redis store count matches, no upload; Else if redis-count > mongo-count start uploading
                
                if error_records_count > uploaded_count:
                    keys_list=[error_records_keys[i:i + error_batch_size] for i in range(0, len(error_records_keys), error_batch_size)]
                    for keys in keys_list:
                        #fetching back all the records from redis store using the srn keys
                        error_records = storerepo.get_all_records(keys,None)
                        log.info(f'Received {len(error_records)} records from redis store for srn -- {srn}')
                        if error_records:
                            file,file_name=self.create_error_file(error_records,srn)
                    log.info(f'Completed csv creation for srn-- {srn} ')  
                    #forking a new thread
                    log.info(f'Initiating upload process for srn -- {srn} on a new fork')
                    persister = threading.Thread(target=self.upload_error_to_object_store, args=(srn,file,file_name,error_records_count))
                    persister.start()
                else:
                    log.info(f'No new records left for uploading, for srn -- {srn}')
                
        except Exception as e:
            log.exception(f"Exception on error processing {e}")

    #method to upload errors onto object store
    def create_error_file(self, error_records, srn):
        try:
            file = f'{shared_storage_path}error-{error_records[0]["datasetName"].replace(" ","-")}-{srn}.csv'
            log.info(f'Writing {len(error_records)} errors to {file} for srn -- {srn}')
            #writing to csv locally
            storeutils.write_to_csv(error_records,file,srn)
            # zipfile = storeutils.zipfile_creation(file)
            # log.info(f"zip file created :{zipfile} , for srn -- {srn}, ")
            file_name = file.replace("/opt/","")
            return file,file_name
            
        except Exception as e:
            log.exception(f'Exception while ingesting errors to object store: {e}')
            return []

    def upload_error_to_object_store(self,srn,file,file_name,error_records_count):
        #initiating upload API call
        error_object_path = storeutils.file_store_upload_call(file,file_name,error_prefix)
        if error_object_path == False:
            return  None
        log.info(f'Error file uploaded on to object store : {error_object_path} for srn -- {srn} ')
        error_record = {"serviceRequestNumber": srn, "uploaded": True, "time_stamp": str(datetime.now()), "internal_file": file, "file": error_object_path, "count": error_records_count}
        #updating record on mongo with uploaded error count
        errorepo.upsert(error_record)
        log.info(f'Updated db record for SRN -- {srn}')
        # os.remove(file)
        return error_record



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