import logging
import uuid
from datetime import datetime
from logging.config import dictConfig
from configs.configs import error_event_input_topic, publish_error_code
from kafkawrapper.producer import Producer

log = logging.getLogger('file')
mongo_instance = None
prod = Producer()

class ErrorEvent:
    def __init__(self):
        pass

    def create_error_event(self, error_list):
        log.info(f'Publishing error event to -- {error_event_input_topic}')
        for error in error_list:
            try:
                event = {"eventType": "dataset-training", "messageType": "error", "code": publish_error_code.replace("XXX", error["code"]),
                         "eventId": f'{error["serviceRequestNumber"]}|{str(uuid.uuid4())}', "timestamp": str(datetime.now()),
                         "datasetType": error["datasetType"], "message": error["message"], "record": error["record"]}
                if 'originalRecord' in error.keys():
                    event["originalRecord"] = error["originalRecord"]
                prod.produce(event, error_event_input_topic, None)
            except Exception as e:
                log.exception(e)
                continue

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