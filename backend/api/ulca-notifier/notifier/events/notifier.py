import logging
from logging.config import dictConfig
from configs import StaticConfigs
from utils.notifierutils import NotifierUtils
from repository import NotifierRepo
log     =   logging.getLogger('file')
utils   =   NotifierUtils()
repo    =   NotifierRepo()

class NotifierEvent:
    def __init__(self,userID):
        query           =   {"userID":userID}
        exclude         =   {"_id":0}
        user_details    =   repo.search(query,exclude,None,None)
        self.user_email      =   user_details[0]["email"] 
        self.user_name       =   user_details[0]["firstName"]
    
    #dumping errors onto redis store
    def data_submission_notifier(self, data):
        log.info(f'Request for notifying data submission updates for entityID:{data["entityID"]}')
        try:
            template        =   'samplemail.html'
            template_vars   =   data
            receiver_list   =   [self.user_email]
            subject         =   StaticConfigs.DS_SUCCESS.value
            utils.generate_email_notification(template,None,receiver_list,subject)
            

        except Exception as e:
            log.exception(f'Exception while writing errors: {e}')
            return False


    def data_search_notifier(self, data):
        log.info(f'Request for notifying data search updates for entityID:{data["entityID"]}')
        try:
            templates       =   'samplemail.html'
            template_vars   =   data
            receiver_list   =   [self.user_email]
            subject         =   None
            utils.generate_email_notification()
        except Exception as e:
            log.exception(f'Exception while writing errors: {e}')
            return False

    
    def benchmark_submission_notifier(self, data):
        log.info(f'Request for notifying benchmark submission updates for entityID:{data["entityID"]}')
        try:
            templates       =   'samplemail.html'
            template_vars   =   data
            receiver_list   =   [self.user_email]
            subject         =   None
            utils.generate_email_notification()
        except Exception as e:
            log.exception(f'Exception while writing errors: {e}')
            return False
 

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