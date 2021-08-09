from threading import Thread
from config import error_cron_interval_sec
import config
import logging
from src.db import ModelRepo
import os
from datetime import datetime
from logging.config import dictConfig
import time
from flask_mail import Mail, Message
from flask import render_template
from app import mail
log         =   logging.getLogger('file')

repo = ModelRepo()
class CronProcessor(Thread):
    def __init__(self, event):
        Thread.__init__(self)
        self.stopped = event

    # Cron JOB to update filter set params
    def run(self):
        run = 0
        while not self.stopped.wait(error_cron_interval_sec):
            log.info(f'Metric Cron Processor run :{run}')
            try:
                parallel_count,ocr_count,mono_count,asr_count,asr_unlabeled_count = self.calculate_counts()
                self.generate_email_notification({"parallel_count":parallel_count,"ocr_count":ocr_count,"mono_count":mono_count,"asr_count":asr_count,"asr_unlabeled_count":asr_unlabeled_count})
                
                run += 1
            except Exception as e:
                run += 1
                log.exception(f'Exception on Metric Cron Processor on run : {run} , exception : {e}')

    

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
            asr_count = asr_labeled[0]["sum"]
            log.info(asr_count)
            asr_unlabeled = repo.aggregate_data_col([{'$group':{'_id': None, 'total': {'$sum': "$durationInSeconds"}}}],config.data_db_schema,config.data_asr_unlabeled)
            asr_unlabeled_count = asr_unlabeled[0]["sum"]
            log.info(asr_unlabeled_count)
            return parallel_count,ocr_count,mono_count,asr_count,asr_unlabeled_count
        except Exception as e:
            log.exception(f'{e}')

    def generate_email_notification(self,data):
        log.info('Generating email notification!')

        for email in config.receiver_email_ids:
            tdy_date        =   str(datetime.utcnow)
            mail_server = config.MAIL_SETTINGS["MAIL_USERNAME"]
            email_subject   =   "Satistics for the ULCA data corpus"
            template        =   'count_mail.html'
            try:
                msg = Message(subject=email_subject,sender=mail_server,recipients=[email])
                msg.html = render_template(template,date=tdy_date,parallel=data["parallel_count"],ocr=data["ocr_count"],mono=data["mono_count"],asr=data["asr_count"],asrun=data["asr_unlabeled_count"])
                mail.send(msg)
                log.info("Generated email notification for {} ".format(email))
            except Exception as e:
                log.exception("Exception while generating email notification | {}".format(str(e)))




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