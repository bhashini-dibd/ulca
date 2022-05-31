from threading import Condition, Thread
from config import eta_cron_interval_sec
import logging
from logging.config import dictConfig
from .eta_service import ETACalculatorService
from repositories import ETARepo

log         =   logging.getLogger('file')
service     =   ETACalculatorService()
repo        =   ETARepo()
class ETACronProcessor(Thread):
    def __init__(self, event):
        Thread.__init__(self)
        self.stopped = event

    # Cron JOB to to calculate ETA for dataset tasks
    def run(self):
        run = 0
        while not self.stopped.wait(eta_cron_interval_sec):
            log.info(f'ETA Cron Processor run :{run}')
            try:
                # Real time calculation of eta
                estimates = service.calculate_average_eta(queries=None)
                log.info(str(estimates))
                if estimates:
                    for est in estimates:
                        query       =   {"$set":est}
                        condition   =   {"type":est["type"]} 
                        log.info("Updating db with latest estimates!")
                        repo.upsert(condition,query)  
                run += 1
            except Exception as e:
                run += 1
                log.exception(f'Exception on ETA Cron Processor on run : {run} , exception : {e}')

    


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