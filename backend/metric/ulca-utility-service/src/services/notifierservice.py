from utilities import datautils
import config
import logging
from logging.config import dictConfig
from repositories import NotifierRepo
log         =   logging.getLogger('file')
from threading import Thread
from config import metric_cron_interval_sec



repo    =   NotifierRepo()

class NotifierService(Thread):

    # Cron JOB to update filter set params
    def __init__(self, event):
        Thread.__init__(self)
        self.stopped = event

    def run(self):
        run = 0
        while not self.stopped.wait(metric_cron_interval_sec):
            try:

                self.notify_user
                run+=1
            except Exception as e:
                log.info(f"error {e}")


    def notify_user(self,emails=None):
        try:
            parallel_count,ocr_count,mono_count,asr_count,asr_unlabeled_count,tts_count,pending_jobs,inprogress_jobs,file = self.calculate_counts()
            utility     =   datautils.DataUtils()
            utility.generate_email_notification({"parallel_count":parallel_count,"ocr_count":ocr_count,"mono_count":mono_count,"asr_count":round(asr_count,4),"asr_unlabeled_count":round(asr_unlabeled_count,4),"tts_count":round(tts_count,4),"pending":pending_jobs,"inprogress":inprogress_jobs,"file":file})
                
        except Exception as e:
            log.exception(f'Exception : {e}')

    def notify_mismatch(self):
        log.info("Checking for data mismatch.......")
        parallel_count,ocr_count,mono_count,asr_count,asr_unlabeled_count,pending_jobs,inprogress_jobs,file = self.calculate_counts()
        mismatch = self.check_for_mismatch(parallel_count,ocr_count,mono_count,asr_count,asr_unlabeled_count)
        if not mismatch:
            log.info("Data is stable ; no mismtach in counts")
            return None
        utility     =   datautils.DataUtils()
        utility.generate_email_notification(mismatch)


    def calculate_counts(self):
        log.info('Calculating counts!')
        try:
            parallel_count = repo.count_data_col({},config.data_db_schema,config.data_parallel)
            log.info(parallel_count)
            ocr_count = repo.count_data_col({},config.data_db_schema,config.data_ocr)
            log.info(ocr_count)
            mono_count = repo.count_data_col({},config.data_db_schema,config.data_mono)
            log.info(mono_count)
            asr_labeled = repo.aggregate_data_col([{'$group':{'_id': None, 'total': {'$sum': "$durationInSeconds"}}}],config.data_db_schema,config.data_asr)
            asr_count = (asr_labeled[0]["total"])/3600
            log.info(asr_count)
            asr_unlabeled = repo.aggregate_data_col([{'$group':{'_id': None, 'total': {'$sum': "$durationInSeconds"}}}],config.data_db_schema,config.data_asr_unlabeled)
            asr_unlabeled_count = (asr_unlabeled[0]["total"])/3600
            log.info(asr_unlabeled_count)
            tts = repo.aggregate_data_col([{'$group':{'_id': None, 'total': {'$sum': "$durationInSeconds"}}}],config.data_db_schema,config.data_tts)
            tts_count = (tts[0]["total"])/3600
            log.info(tts_count)
            aggquery = [{ "$match": { "$or": [{ "status": "In-Progress" }, { "status": "Pending" }] ,"$and":[{"serviceRequestAction" : "submit"}]}},
                        {"$lookup":{"from": "ulca-pt-tasks","localField": "serviceRequestNumber","foreignField": "serviceRequestNumber","as": "tasks"}},
                        ]
            aggresult = repo.aggregate_process_col(aggquery,config.process_db_schema,config.process_col)
            pending_jobs,inprogress_jobs,jobfile = self.process_aggregation_output(aggresult)
            log.info(f"Pending :{pending_jobs}")
            log.info(f"In-Progress:{inprogress_jobs}")
            log.info(f"file:{jobfile}")
            
            return parallel_count,ocr_count,mono_count,asr_count,asr_unlabeled_count,tts_count,pending_jobs,inprogress_jobs,jobfile
        except Exception as e:
            log.exception(f'{e}')

    
    def process_aggregation_output(self,aggdata):
        try:
            
            inprogress = 0
            pending = 0
            jobs=[]
            stages = ["download","ingest","validate","publish"]
            for agg in aggdata:
                if agg["serviceRequestAction"] == "search":
                    continue
                status={}
                status["serviceRequestNumber"] = agg["serviceRequestNumber"]
                for task in agg["tasks"]:
                    status[task["tool"]] = task["status"]
                jobs.append(status)
            
            for job in jobs:
                for stage in stages:
                    if job.get(stage) == None:
                        job[stage] = "Pending"
                        
            for job in jobs:
                if "Pending" in job.values():
                    pending = pending +1
                else:
                    inprogress = inprogress+1
            # csvfile_created = DataUtils.write_to_csv(jobs)
            csvfile_created = None
            return pending,inprogress,csvfile_created

        except Exception as e:
            log.exception(f"Exception:{e}") 

    def check_for_mismatch(self,parallel_count,ocr_count,monolingual_count,asr_count,asr_unlabeled_count,tts_count):
        try:
            mismatch = []
            dtypes              =   ["parallel-corpus","ocr-corpus","asr-corpus","monolingual-corpus","asr-unlabeled-corpus","tts-corpus"]
            for data in dtypes:
                if data == "ocr-corpus":
                    mongo_count =   round(ocr_count)
                    request     =   {"type":f"{data}","criterions":[{"field":"sourceLanguage","value":None}],"groupby":None}
                    label       =   "OCR Dataset"

                if data == "asr-corpus":
                    mongo_count =   round(asr_count)
                    request     =   {"type":f"{data}","criterions":[{"field":"sourceLanguage","value":None}],"groupby":None}
                    label       =   "ASR/TTS Dataset"

                if data == "parallel-corpus":
                    mongo_count =   round(parallel_count)
                    request     =   {"type":"parallel-corpus","criterions":[{"field":"sourceLanguage","value":"en"}],"groupby":None}
                    label       =   "Parallel Dataset"
                    
                if data == "monolingual-corpus":
                    mongo_count =   round(monolingual_count)
                    request     =   {"type":f"{data}","criterions":[{"field":"sourceLanguage","value":None}],"groupby":None}
                    label       =   "Monolingual Dataset"
                             
                if data == "asr-unlabeled-corpus":
                    mongo_count =   round(asr_unlabeled_count)
                    request     =   {"type":f"{data}","criterions":[{"field":"sourceLanguage","value":None}],"groupby":None}
                    label       =   "ASR Unlabeled Dataset"
                if data == "tts-corpus":
                    mongo_count = round(tts_count)
                    request     = {"type":f"{data}","criterions":[{"field":"sourceLanguage","value":None}],"groupby":None}
                    label       = "TTS Dataset" 
                utility     =   datautils.DataUtils()
                druid_count =   utility.get_statistics_from_metrics_service(request)
                log.info(f"Data Type: {label} Druid Count: {druid_count} Mongo Count: {mongo_count}")
                if druid_count == False:
                    return
                if (round(druid_count) < (mongo_count-10)) or (round(druid_count) > (mongo_count)):
                    mismatch.append({"Data Type": label,"Druid Count": druid_count,"Mongo Count": mongo_count})
            if mismatch:
                return mismatch
                
        except Exception as e:
            log.info(f"Exception occurred while comparing stats on mongo and druid: {e}")
            



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
