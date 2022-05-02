from threading import Thread
from config import status_cron_interval_sec, process_db_schema,process_col, pending_jobs_duration, tasks_col, queued_pending_duration
import logging
from logging.config import dictConfig
from repositories import StatusUpdaterRepo
from datetime import datetime,timedelta
from services import NotifierService
from models.response import CustomResponse
from models.response import Status

service = NotifierService()

log         =   logging.getLogger('file')

repo = StatusUpdaterRepo()

class StatusCronProcessor(Thread):
    def __init__(self, event):
        Thread.__init__(self)
        self.stopped = event

    # Cron JOB to update filter set params
    def run(self):
        run = 0
        task_list = ['validate','publish','ingest']
        pub = ['publish']
        status_list = ['In-Progress','Pending','Queued']
        compl_list = ['Completed']
        while not self.stopped.wait(status_cron_interval_sec):
            service.notify_user()
            res = CustomResponse(Status.SUCCESS.value,None,None)
            log.info(f'response successfully generated.{res}')
            #return res.getres()
            log.info(f'Job status updater cron run :{run}')
            try:
                pending_srns = self.get_pending_tasks()
                queued_srns = self.get_queued_srns()
                #log.info(f'pending srns...{len(pending_srns)}')
                #log.info(f'queued srns...{len(queued_srns)}')
                if pending_srns:
                    for srn in pending_srns:
                        condition = {'serviceRequestNumber':srn}
                        query_pro = {'$set':{'status':'Queued','manuallyUpdated':True}}
                        compl_pro = {'$set':{'status':'Completed','manuallyUpdated':True}}
                        multi ={'multi':True}
                        repo.update(condition,query_pro,False,process_db_schema,process_col)
                        tasks_res = repo.find(condition,process_db_schema,tasks_col)
                        if tasks_res:
                            for task in tasks_res:
                                task_cond = {'serviceRequestNumber':srn,'tool':task['tool']}
                                if str(task['tool']) in task_list and str(task['status']) in status_list:
                                    repo.update(task_cond,query_pro,False,process_db_schema,tasks_col)
                        log.info(f"Updated status for srn -{srn}")   
                    log.info('Completed run!')
                if queued_srns:
                    for que in queued_srns:
                        q_condition = {'serviceRequestNumber':que}
                        set_failed = {'$set':{'status':'Failed','manuallyUpdated':True}}
                        repo.update(q_condition,set_failed,False,process_db_schema,process_col)
                        que_res = repo.find(q_condition,process_db_schema,tasks_col)
                        log.info(f'QUEUED {que_res}')
                        if que_res:
                            for qued in que_res:
                                que_cond = {'serviceRequestNumber':que,'tool':qued['tool']}
                                if str(qued['tool']) in task_list and str(qued['status']) == str(status_list[2]):
                                    repo.update(que_cond,set_failed,False,process_db_schema,tasks_col)
                    log.info(f"Updated status for srn -{que}")
                log.info('Completed run!')      
                run += 1
            except Exception as e:
                run += 1
                log.exception(f'Exception on Metric Cron Processor on run : {run} , exception : {e}')

    def get_pending_tasks(self):
        lastday = (datetime.now() - timedelta(hours=pending_jobs_duration))
        log.info(f'lastday{lastday}')
        query = [{ '$match':{'serviceRequestType':'dataset','serviceRequestAction':'submit','status':{'$in':['In-Progress','Pending']}}}, {
                                                     '$project': {'date': {'$dateFromString': {'dateString': '$startTime'}},'serviceRequestNumber': '$serviceRequestNumber'}},
                                                     {'$match': {'date': {'$lt': lastday}}}]
        aggresult = repo.aggregate(query,process_db_schema,process_col)
        log.info(f'aggregate for 1 day{aggresult}')
        if not aggresult:
            log.info("0 pending srns found >>")
            return None
        pending_srns = []
        for agg in aggresult:
            pending_srns.append(agg["serviceRequestNumber"])
        log.info(f"{len(pending_srns)} pending srns found >>")
        return pending_srns

    def get_queued_srns(self):
        que_lastday = (datetime.now() - timedelta(hours=queued_pending_duration))
        que_query = [{ '$match':{'serviceRequestType':'dataset','serviceRequestAction':'submit','status':'Queued'}}, {
                                                     '$project': {'date': {'$dateFromString': {'dateString': '$startTime'}},'serviceRequestNumber': '$serviceRequestNumber'}},
                                                     {'$match': {'date': {'$lt': que_lastday}}}]  
        que_aggregate = repo.aggregate(que_query,process_db_schema,process_col)
        log.info(f'aggregate for 5 day{que_aggregate}')
        if not que_aggregate:
            log.info("0 queued srns found >>")
            return None
        queued_srn   = []
        for que in que_aggregate:
            queued_srn.append(que["serviceRequestNumber"])
        log.info(f'{len(queued_srn)} queued srns found')
        return queued_srn
  


    

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
