import logging
from logging.config import dictConfig
from os import name
from re import template
from configs import StaticConfigs
from configs.configs import base_url,ds_contribution_endpoint,model_bm_contribution_endpoint,ds_search_list_endpoint, receiver_email_ids
from utils.notifierutils import NotifierUtils
from repository import NotifierRepo
log     =   logging.getLogger('file')
utils   =   NotifierUtils()
repo    =   NotifierRepo()

class NotifierEvent:
    def __init__(self,userID):
        if userID == None:
            self.user_email = receiver_email_ids.split(',')
        else:
            query           =   {"userID":userID}
            exclude         =   {"_id":0}
            user_details    =   repo.search(query,exclude,None,None)
            self.user_email      =   user_details[0]["email"] 
            self.user_name       =   user_details[0]["firstName"]
    

    def model_check_notifier(self, data):
        models_list = []
        req_list = [] 
        task_list = []
        callbU_list = []
        try: 
            status = (data["event"].split('-'))[-1]
            if status == "failed":
                template = 'md_inf_failed.html'
                receiver_list = self.user_email
                subject = StaticConfigs.MD_INFR_FAILED.value
                for details in data["details"]:
                    if "request" not in details.keys():
                        details["request"] = 'N/A'
                    if "taskType" not in details.keys():
                        details["taskType"] = 'N/A'
                    if "callBackUrl" not in details.keys():
                        details["callBackUrl"] = 'N/A'
                    models_list.append(details["modelName"])
                    req_list.append(str(details["request"]))
                    task_list.append(details["taskType"])
                    callbU_list.append(details["callBackUrl"])
                leng = len(models_list)
                template_vars = {"firstname":None,"activity_link":None,"datasetName":None,"datasetType":None,"modelName": models_list,
                "taskType":task_list,"callbackUrl":callbU_list,"request":req_list,"len":leng}
                utils.generate_email_notification(template, template_vars,receiver_list,subject)

        except Exception as e:
                    log.exception(f'Exception while writing errors: {e}')
                    return False

    def data_submission_notifier(self, data):
        log.info(f'Request for notifying data submission updates for entityID:{data["entityID"]}')
        log.info(data)
        try:
            status  =   (data["event"].split('-'))[-1]
            if      status      == "completed":
                template        =   'ds_submit_success.html'
                receiver_list   =   [self.user_email]
                subject         =   StaticConfigs.DS_SUBMIT_SUCCESS.value
            elif    status      == "failed":
                template        =   'ds_submit_failed.html'
                subject         =   StaticConfigs.DS_SUBMIT_FAILED.value
            link                =   f'{base_url}{ds_contribution_endpoint}{data["entityID"]}'
            template_vars       =   {"firstname":self.user_name,"activity_link":link,"datasetName":data["details"]["datasetName"],"datasetType":None,"modelName":None}
            receiver_list       =   [self.user_email]
            utils.generate_email_notification(template,template_vars,receiver_list,subject)
            

        except Exception as e:
            log.exception(f'Exception while writing errors: {e}')
            return False


    def data_search_notifier(self, data):
        log.info(f'Request for notifying data search updates for entityID:{data["entityID"]}')
        log.info(data)
        try:
            status  =   (data["event"].split('-'))[-1]
            if      status      == "completed":
                template        =   'search_success.html'
                receiver_list   =   [self.user_email]
                subject         =   StaticConfigs.DS_SEARCH_COMPLETE.value
            types               =   {"parallel-corpus":"Parallel Dataset","monolingual-corpus":"Monolingual Dataset","asr-corpus":"ASR/TTS Dataset",
                                        "asr-unlabeled-corpus":"ASR Unlabeled Dataset","ocr-corpus":"OCR Dataset","document-layout-corpus":"Document Layout Dataset"}
            dtype               =   types.get(data["details"]["datasetType"])
            link                =   f'{base_url}{ds_search_list_endpoint}{data["entityID"]}'
            template_vars       =   {"firstname":self.user_name,"activity_link":link,"datasetType":dtype,"modelName":None,"datasetName":None}
            receiver_list       =   [self.user_email]
            utils.generate_email_notification(template,template_vars,receiver_list,subject)
        except Exception as e:
            log.exception(f'Exception while writing errors: {e}')
            return False

    
    def benchmark_submission_notifier(self, data):
        log.info(f'Request for notifying benchmark submission updates for entityID:{data["entityID"]}')
        log.info(data)
        try:
            status  =   (data["event"].split('-'))[-1]
            if      status      == "completed":
                template        =   'bm_run_success.html'
                receiver_list   =   [self.user_email]
                subject         =   StaticConfigs.BM_RUN_SUCCESS.value
            elif    status      == "failed":
                template        =   'bm_run_failed.html'
                subject         =   StaticConfigs.BM_RUN_FAILED.value
            link                =   f'{base_url}{model_bm_contribution_endpoint}{data["entityID"]}'
            template_vars       =   {"firstname":self.user_name,"activity_link":link,"datasetType":None,"datasetName":None}
            receiver_list       =   [self.user_email]
            utils.generate_email_notification(template,template_vars,receiver_list,subject)
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