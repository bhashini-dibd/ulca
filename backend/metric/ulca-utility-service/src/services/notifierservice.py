
import os
import config
import logging
from datetime import datetime
from logging.config import dictConfig
from flask_mail import Mail, Message
from flask import render_template
from app import mail
from repositories import NotifierRepo
log         =   logging.getLogger('file')
import csv
import pytz

IST = pytz.timezone('Asia/Kolkata')

repo = NotifierRepo()

class NotifierService:

    # Cron JOB to update filter set params
    def notify_user(self,emails=None):
        try:
            parallel_count,ocr_count,mono_count,asr_count,asr_unlabeled_count,pending_jobs,inprogress_jobs,file = self.calculate_counts()
            self.generate_email_notification({"parallel_count":parallel_count,"ocr_count":ocr_count,"mono_count":mono_count,"asr_count":asr_count,"asr_unlabeled_count":asr_unlabeled_count,"pending":pending_jobs,"inprogress":inprogress_jobs,"file":file})
                
        except Exception as e:
            log.exception(f'Exception : {e}')

    

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
            asr_count = round(asr_count,4)
            log.info(asr_count)
            asr_unlabeled = repo.aggregate_data_col([{'$group':{'_id': None, 'total': {'$sum': "$durationInSeconds"}}}],config.data_db_schema,config.data_asr_unlabeled)
            asr_unlabeled_count = (asr_unlabeled[0]["total"])/3600
            asr_unlabeled_count=round(asr_unlabeled_count,4)
            log.info(asr_unlabeled_count)

            aggquery = [{ "$match": { "$or": [{ "status": "In-Progress" }, { "status": "Pending" }] } },
                        {"$lookup":{"from": "ulca-pt-tasks","localField": "serviceRequestNumber","foreignField": "serviceRequestNumber","as": "tasks"}},
                        ]
            aggresult = repo.aggregate_process_col(aggquery,config.process_db_schema,config.process_col)
            # log.info(aggresult)
            pending_jobs,inprogress_jobs,jobfile = self.process_aggregation_output(aggresult)
            log.info(f"Pending :{pending_jobs}")
            log.info(f"In-Progress:{inprogress_jobs}")
            log.info(f"file:{jobfile}")
            
            return parallel_count,ocr_count,mono_count,asr_count,asr_unlabeled_count,pending_jobs,inprogress_jobs,jobfile
        except Exception as e:
            log.exception(f'{e}')

    def generate_email_notification(self,data):

        try:
            users = config.receiver_email_ids.split(',')
            log.info(f"Generating emails for {users} ")
            for user in users:
                email       = user   
                tdy_date    =  datetime.now(IST).strftime('%Y:%m:%d %H:%M:%S')
                msg         = Message(subject=f" ULCA- Statistics {tdy_date}",
                              sender="anuvaad.support@tarento.com",
                              recipients=[email])
                msg.html    = render_template('count_mail.html',date=tdy_date,parallel=data["parallel_count"],ocr=data["ocr_count"],mono=data["mono_count"],asr=data["asr_count"],asrun=data["asr_unlabeled_count"],inprogress=data["inprogress"],pending=data["pending"])
                file= data["file"]
                with open (file,'rb') as fp:
                    msg.attach(f"statistics-{tdy_date}.csv", "text/csv", fp.read())

                mail.send(msg)
                os.remove(file)
                log.info(f"Generated email notification for {user}")
        except Exception as e:
            log.exception("Exception while generating email notification for ULCA statistics: " +
                          str(e))

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
            csvfile_created = self.write_to_csv(jobs)
            return pending,inprogress,csvfile_created

        except Exception as e:
            log.exception(f"Exception:{e}") 

    
    def write_to_csv(self, data_list):
        try:
            tdy_date    =  datetime.now(IST).strftime('%Y:%m:%d-%H:%M:%S')
            file = f'{config.shared_storage_path}{tdy_date}.csv'
            csv_headers = ["serviceRequestNumber","download","ingest","validate","publish"]
            log.info('Started csv writing !...')
            with open(file, 'w') as output_file:
                dict_writer = csv.DictWriter(output_file,fieldnames=csv_headers,extrasaction='ignore')
                dict_writer.writeheader()
                for data in data_list:
                    dict_writer.writerow(data)
            log.info(f'{len(data_list)} Jobs written to csv -{file}')
            return file
        except Exception as e:
            log.exception(f'Exception in csv writer: {e}')
            return


            



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
