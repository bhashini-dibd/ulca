from events.processrepo import ProcessRepo
import threading
from threading import Thread
from configs.configs import error_cron_interval_sec, shared_storage_path, error_prefix, error_batch_size
from .cronrepo import StoreRepo
import logging
from events.errorrepo import ErrorRepo
from events.processrepo import ProcessRepo
from utils.cronjobutils import StoreUtils
import os
from datetime import datetime
from logging.config import dictConfig


log         =   logging.getLogger('file')
storerepo   =   StoreRepo()
errorepo    =   ErrorRepo()
storeutils  =   StoreUtils()
prorepo     =   ProcessRepo()

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
                log.info(f'Creating aggregated error report for srn-- {srn}')
                er_query = {"serviceRequestNumber": srn}
                exclude =  {"_id": False}
                log.info(f'Search for error reports of SRN -- {srn} from db started')
                error_records = errorepo.search(er_query, exclude, None, None)
                error_records = [x for x in error_records if not x.get("uploaded")]
                log.info(f'Returned {len(error_records)} records')
                file = f'{shared_storage_path}consolidated-error-{error_records[0]["datasetName"].replace(" ","-")}-{srn}.csv'
                storeutils.write_to_csv(error_records,file,srn)
                storeutils.file_store_upload_call(file,file.replace("/opt/",""),error_prefix)
                update_query = {"serviceRequestNumber": srn, "uploaded": True, "time_stamp": str(datetime.now()), "consolidated_file": file, "file": None, "count" : None}
                condition = {"serviceRequestNumber": srn, "uploaded": True}
                errorepo.update(condition,update_query,True)

                query   = {"serviceRequestNumber" : srn,"status" : "Completed"}
                #getting record from mongo matching srn, if present
                completed_stats = prorepo.count(query)
                #create error file when the job is completed
                if completed_stats == 1:
                    #srn.uuid - pattern of all keys in redis
                    pattern = f'{srn}.*'
                    #getting all keys matching the patterns
                    error_records_keys = storerepo.get_keys_matching_pattern(pattern)
                    #counting keys matching the pattern, to get the count of error records on redis store against respective srn
                    error_records_count = len(error_records_keys)
                    log.info(f'{error_records_count} records found in redis store for srn -- {srn}')

                    keys_list=[error_records_keys[i:i + error_batch_size] for i in range(0, len(error_records_keys), error_batch_size)]
                    for i,keys in enumerate(keys_list):
                        #fetching back all the records from redis store using the srn keys
                        error_records = storerepo.get_all_records(keys,None)
                        log.info(f'Received {len(error_records)} records from redis store for srn -- {srn}')
                        if error_records:
                            zip_file,zip_file_name=self.create_error_file(error_records,srn,i)
                    log.info(f'Completed csv creation for srn-- {srn} ')  
                    #forking a new thread
                    log.info(f'Initiating upload process for srn -- {srn} on a new fork')
                    persister = threading.Thread(target=self.upload_error_to_object_store, args=(srn,zip_file,zip_file_name,error_records_count))
                    persister.start()
                    #removing records from redis
                    remover = threading.Thread(target=storerepo.delete,args=(error_records_keys,))
                    remover.start()
                        
                else:
                    log.info(f'No new records left for uploading, for srn -- {srn}')
                
        except Exception as e:
            log.exception(f"Exception on error processing {e}")

    #method to upload errors onto object store
    def create_error_file(self, error_records, srn,index):
        try:
            csv_file = f'{shared_storage_path}error-{error_records[0]["datasetName"].replace(" ","-")}-{srn}-{index}.csv'
            zip_file= f'{shared_storage_path}error-{error_records[0]["datasetName"].replace(" ","-")}-{srn}.zip'
            log.info(f'Writing {len(error_records)} errors to {csv_file} for srn -- {srn}')
            #writing to csv locally
            storeutils.write_to_csv(error_records,csv_file,srn)
            storeutils.zipfile_creation(csv_file,zip_file)
            log.info(f"zip file created :{zip_file} , for srn -- {srn}, ")
            return zip_file,zip_file.replace("/opt/","")
            
        except Exception as e:
            log.exception(f'Exception while ingesting errors to object store: {e}')
            return []

    def upload_error_to_object_store(self,srn,file,file_name,error_records_count):
        #initiating upload API call
        error_object_path = storeutils.file_store_upload_call(file,file_name,error_prefix)
        if error_object_path == False:
            return  None
        log.info(f'Error file uploaded on to object store : {error_object_path} for srn -- {srn} ')
        cond = {"serviceRequestNumber": srn, "uploaded": True}
        error_record = {"$set":{ "file": error_object_path, "count": error_records_count}}
        #updating record on mongo with uploaded error count
        errorepo.update(cond,error_record,False)
        log.info(f'Updated db record for SRN after creating final report -- {srn}')
        





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