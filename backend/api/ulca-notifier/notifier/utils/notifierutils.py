import time
import logging
from logging.config import dictConfig
from configs.configs import MAIL_SENDER
from app import mail
from flask_mail import Mail, Message
from flask import render_template

log = logging.getLogger('file')

mongo_instance = None

class NotifierUtils:
    def __init__(self):
        pass
    
    def generate_email_notification(self,template,template_vars,receiver_list,subject):
        log.info("Generating email.........")
        timestamp   =   eval(str(time.time()).replace('.', '')[0:13])
        try:
            msg = Message(subject=subject,sender=MAIL_SENDER,recipients=receiver_list)
            msg.html = render_template(template,firstname=template_vars["firstname"],activity_link=template_vars["activity_link"],\
                                        datasetName=template_vars["datasetName"],datasetType=template_vars["datasetType"],modelName=template_vars["modelName"],
                                        taskType=template_vars["taskType"],callbackUrl=template_vars["callbackUrl"],len=template_vars["len"])
        
            mail.send(msg)
            log.info(f"Generated email notification for {receiver_list} ")
        except Exception as e:
            log.exception(f"Exception while generating email notification | {e}")
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
