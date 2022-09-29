import logging
import uuid
from datetime import datetime
from logging.config import dictConfig
from configs.configs import error_event_input_topic, validate_error_code, pt_publish_tool
from kafkawrapper.producer import Producer
import time

log = logging.getLogger('file')
prod = Producer()



class ErrorEvent:
    def __init__(self):
        pass

    def create_error_event(self, error):
        log.info(f'Publishing error event for srn -- {error["serviceRequestNumber"]}')
        try:
            event = {"eventType": "dataset-training", "messageType": "error", "code": validate_error_code.replace("XXX", error["code"]),
                     "eventId": f'{error["serviceRequestNumber"]}|{str(uuid.uuid4())}', "timestamp": eval(str(time.time()).replace('.', '')[0:13]),
                     "serviceRequestNumber": error["serviceRequestNumber"], "stage": pt_publish_tool, "datasetName": error["datasetName"],
                     "datasetType": error["datasetType"], "message": error["message"], "record": error["record"]}
            prod.produce(event, error_event_input_topic, None)
        except Exception as e:
            log.exception(e)

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