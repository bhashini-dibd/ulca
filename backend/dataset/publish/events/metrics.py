import logging
import uuid
from datetime import datetime
from logging.config import dictConfig
from configs.configs import metric_event_input_topic
from kafkawrapper.producer import Producer

log = logging.getLogger('file')


mongo_instance = None
prod = Producer()

class MetricEvent:
    def __init__(self):
        pass

    def create_metric_event(self, data, is_del, is_upd):
        log.info(f'Publishing BI metric event for srn -- {data["serviceRequestNumber"]}')
        try:
            event = {"eventType": "dataset-training", "eventId": f'{data["serviceRequestNumber"]}|{data["id"]}',
                     "timestamp": str(datetime.now()), "submitterId": data["userId"]}
            if 'sourceLanguage' in data.keys():
                event["sourceLanguage"] = data["sourceLanguage"]
            if 'targetLanguage' in data.keys():
                event["targetLanguage"] = data["targetLanguage"]
            if 'domain' in data.keys():
                event["domains"] = data["domains"]
            if 'license' in data.keys():
                event["license"] = data["license"]
            if 'submitter' in data.keys():
                submitter = data["submitter"]
                event["primarySubmitterId"] = submitter["id"]
                if 'team' in submitter.keys():
                    secondary_submitters = []
                    for team in data["submitter"]["team"]:
                        secondary_submitters.append(team["id"])
                    if secondary_submitters:
                        event["secondarySubmitterIds"] = secondary_submitters
            if is_del:
                event["isDelete"] = True
                prod.produce(data, metric_event_input_topic, None)
            elif is_upd:
                event["isDelete"] = True
                prod.produce(data, metric_event_input_topic, None)
                event["isDelete"] = False
                prod.produce(data, metric_event_input_topic, None)
            else:
                event["isDelete"] = False
                prod.produce(data, metric_event_input_topic, None)
        except Exception as e:
            log.exception(e)
            return None


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