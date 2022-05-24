from threading import Thread
from config import mismatch_cron_interval_sec, mismatch_email_service_url
import logging
from src.db import ModelRepo
import requests
from logging.config import dictConfig
log         =   logging.getLogger('file')
from logging.config import dictConfig
repo = ModelRepo()

class AlertCronProcessor(Thread):
    def __init__(self, event):
        Thread.__init__(self)
        self.stopped = event

    # Cron JOB to update filter set params
    def run(self):
        run = 0
        while not self.stopped.wait(mismatch_cron_interval_sec):
            log.info(f'Mismatch identifier cron Processor run :{run}')
            try:
                headers =   {"Content-Type": "application/json"}
                body    =   {"emails":[]}
                request_url = mismatch_email_service_url
                log.info("Intiating request to check data mismatch %s"%request_url)
                result=requests.post(url=request_url, headers = headers, json = body)
                log.info(result.content)
                
                run += 1
            except Exception as e:
                run += 1
                log.exception(f'Exception on Metric Cron Processor on run : {run} , exception : {e}')

    


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
