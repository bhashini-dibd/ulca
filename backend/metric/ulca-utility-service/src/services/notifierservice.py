
import config
import logging
from datetime import datetime
from logging.config import dictConfig
from flask_mail import Mail, Message
from flask import render_template
from app import mail
from repositories import NotifierRepo
log         =   logging.getLogger('file')
import pytz

IST = pytz.timezone('Asia/Kolkata')

repo = NotifierRepo()

class NotifierService:

    # Cron JOB to update filter set params
    def notify_user(self,emails=None):
        try:
            parallel_count,ocr_count,mono_count,asr_count,asr_unlabeled_count,pending_jobs,inprogress_jobs = self.calculate_counts()
            self.generate_email_notification({"parallel_count":parallel_count,"ocr_count":ocr_count,"mono_count":mono_count,"asr_count":asr_count,"asr_unlabeled_count":asr_unlabeled_count,"pending":pending_jobs,"inprogress":inprogress_jobs})
                
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
            log.info(asr_count)
            asr_unlabeled = repo.aggregate_data_col([{'$group':{'_id': None, 'total': {'$sum': "$durationInSeconds"}}}],config.data_db_schema,config.data_asr_unlabeled)
            asr_unlabeled_count = (asr_unlabeled[0]["total"])/3600
            log.info(asr_unlabeled_count)

            pending_jobs = repo.count_process_col({"status": "Pending"},config.process_db_schema,config.process_col)
            log.info(pending_jobs)
            inprogress_jobs = repo.count_process_col({"status": "In-Progress"},config.process_db_schema,config.process_col)
            log.info(inprogress_jobs)
            return parallel_count,ocr_count,mono_count,asr_count,asr_unlabeled_count,pending_jobs,inprogress_jobs
        except Exception as e:
            log.exception(f'{e}')
            return 0,0,0,0,0,0,0

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
                mail.send(msg)
                log.info(f"Generated email notification for {user}")
        except Exception as e:
            log.exception("Exception while generating email notification for ULCA statistics: " +
                          str(e))



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