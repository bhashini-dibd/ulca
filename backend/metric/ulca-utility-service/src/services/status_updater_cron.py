from threading import Thread
from config import error_cron_interval_sec
import logging
from logging.config import dictConfig
from repositories import StatusUpdaterRepo
from datetime import datetime,timedelta
log         =   logging.getLogger('file')

repo = StatusUpdaterRepo()

class StatusCronProcessor(Thread):
    def __init__(self, event):
        Thread.__init__(self)
        self.stopped = event

    # Cron JOB to update filter set params
    def run(self):
        run = 0
        while not self.stopped.wait(error_cron_interval_sec):
            log.info(f'Job status updater cron run :{run}')
            try:
                pending_srns = self.get_pending_tasks()
                if pending_srns:
                    for srn in pending_srns:
                        log.info(f"Updating status for srn -{srn}")
                        condition = {'serviceRequestNumber':srn}
                        query = {'$set':{'status':'Abandoned'}}
                        repo.update(condition,query)
                        
                run += 1
            except Exception as e:
                run += 1
                log.exception(f'Exception on Metric Cron Processor on run : {run} , exception : {e}')

    def get_pending_tasks(self):
        lastday = datetime.now() - timedelta(days=1)
        query = [{ '$match':{'serviceRequestType':'dataset','status':'Pending'}},
                 {'$project': {'startedOn': {'$dateFromString': {'dateString': '$startTime'}},'serviceRequestNumber':'$serviceRequestNumber'}},
                    { '$match':{'startedOn':{ '$lt': lastday }}} ]
        aggresult = repo.aggregate(query)
        if not aggresult:
            log.info("0 pending srns found >>")
            return None
        pending_srns = []
        for agg in aggresult:
            pending_srns.append(agg["serviceRequestNumber"])
        log.info(f"{len(pending_srns)} pending srns found >>")
        return pending_srns


        


    

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
