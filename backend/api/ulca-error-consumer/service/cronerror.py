from events.processrepo import ProcessRepo
import threading
from threading import Thread
from configs.configs import error_cron_interval_sec, shared_storage_path, error_prefix, error_batch_size, redis_key_expiry
from .cronrepo import StoreRepo
import logging
from events.errorrepo import ErrorRepo
from events.processrepo import ProcessRepo
from utils.cronjobutils import StoreUtils
import os
from datetime import datetime,timedelta
from logging.config import dictConfig


log         =   logging.getLogger('file')
storerepo   =   StoreRepo()
errorepo    =   ErrorRepo()
storeutils  =   StoreUtils()
prorepo     =   ProcessRepo()
error_batch_size = int(error_batch_size)
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
                # log.info('Fetching SRNs from redis store')
                # srn_list = storerepo.get_unique_srns() 
                # Getting all keys(SRN.UUID) from redis and filtering out the unique SRNs is again costly in terms of storage
                # so getting that from mongo; previous implementation : line no-33 
                log.info('Fetching SRNs from mongo store')
                srn_list = self.get_unique_srns()
                errorepo.remove({"uploaded" : { "$exists" : False},"serviceRequestNumber":{"$nin":srn_list}}) #removing old error records from mongo
                if srn_list:
                    log.info(f'{len(srn_list)} SRNs found from mongo store')
                    log.info(f'Error processing initiated --------------- run : {run}')
                    self.initiate_error_processing(srn_list)
                    log.info(f'Error Processing completed --------------- run : {run}')
                else:
                    log.info('Received 0 SRNs from mongo store')
                    # log.info('Received 0 SRNs from redis store')
                run += 1
            except Exception as e:
                run += 1
                log.exception(f'Exception on ErrorProcessor on run : {run} , exception : {e}')

    #fetching all error records from redis, and passing on to upload service
    def initiate_error_processing(self,srn_list):
        try:
            for srn in srn_list:
                #getting the total error count (summation of "count" field) for records stored on mongo
                agg_query = [{"$match":{"serviceRequestNumber": srn,"uploaded" : { "$exists" : False}}},
                            { "$group": { "_id" : None, "consolidatedCount" : { "$sum": "$count" } } },
                            {"$project":{ "_id":0,"consolidatedCount":1}}]
                present_count = errorepo.aggregate(agg_query)
                if len(present_count) == 0:
                    continue
                check_query   = {"serviceRequestNumber" : srn,"uploaded" : True} 
                consolidated_rec = errorepo.search(check_query, {"_id":False}, None, None) #  Respone - Null --> Summary report havent't generated yet
                if  not consolidated_rec:
                    log.info(f'consolidated count NULL')
                if (not consolidated_rec or (consolidated_rec[0]["consolidatedCount"] < present_count[0]["consolidatedCount"])):
                    log.info(f'Creating consolidated error report for srn-- {srn}')
                    search_query = {"serviceRequestNumber": srn,"uploaded" : { "$exists" : False}}
                    error_records =errorepo.search(search_query,{"_id":False},None,None)
                    if "datasetName" not in error_records[0]:
                        log.info(f'datasetName not found')
                    file = f'{shared_storage_path}consolidated-error-{error_records[0]["datasetName"].replace(" ","-")}-{srn}.csv'
                    headers =   ['Stage','Error Message', 'Record Count']
                    fields  =   ['stage','message','count']
                    storeutils.write_to_csv(error_records,file,srn,headers,fields)
                    agg_file = storeutils.file_store_upload_call(file,file.replace("/opt/",""),error_prefix)
                    update_query = {"serviceRequestNumber": srn, "uploaded": True, "time_stamp": str(datetime.now()), "consolidated_file": agg_file, "file": None, "count" : None,"consolidatedCount":present_count[0]["consolidatedCount"]}
                    condition = {"serviceRequestNumber": srn, "uploaded": True}
                    errorepo.update(condition,update_query,True)

                query   = {"serviceRequestNumber" : srn,"status" : {'$in':['Completed','Failed']}}
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
                    if error_records_count ==0:
                        continue
                    log.info(f'Creating full error report for srn-- {srn}')
                    #Errors are written into csv as batches to overcome memory exhaustion
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
                    log.info(f'The job is not yet completed for SRN --{srn}')
                
        except Exception as e:
            log.exception(f"Exception on error processing {e}")

    #method to upload errors onto object store
    def create_error_file(self, error_records, srn,index):
        try:
            csv_file = f'{shared_storage_path}error-{error_records[0]["datasetName"].replace(" ","-")}-{srn}-{index}.csv'
            zip_file= f'{shared_storage_path}error-{error_records[0]["datasetName"].replace(" ","-")}-{srn}.zip'
            log.info(f'Writing {len(error_records)} errors to {csv_file} for srn -- {srn}')
            #writing to csv locally
            headers = ['Stage', 'Error Message', 'Record', 'Original Record']
            fields = ['stage','message','record','originalRecord']
            storeutils.write_to_csv(error_records,csv_file,srn,headers,fields)
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
        

    def get_unique_srns(self):
        lastday = (datetime.now() - timedelta(seconds=redis_key_expiry*2))
        query = [{ '$match':{'serviceRequestType':{'$in':['dataset','benchmark']},'serviceRequestAction':'submit'}}, 
                {'$project': {'date': {'$dateFromString': {'dateString': '$startTime'}},'serviceRequestNumber': '$serviceRequestNumber'}},
                {'$match': {'date': {'$gt': lastday}}}]
        # log.info(f"Query :{query}")
        SRNlist = []
        aggresult = prorepo.aggregate(query)
        if aggresult:
            for agg in aggresult:
                SRNlist.append(agg["serviceRequestNumber"])
        return SRNlist
        





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