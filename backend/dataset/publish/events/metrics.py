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

    def build_metric_event(self, records, srn, user_id, is_del, is_upd):
        if not isinstance(records, list):
            records["serviceRequestNumber"], records["userId"] = srn, user_id
            if is_del:
                records["isDelete"] = True
            if is_upd:
                records["isUpdate"] = True
            self.create_metric_event(records)
        else:
            for record in records:
                record["serviceRequestNumber"], record["userId"] = srn, user_id
                if is_del:
                    record["isDelete"] = True
                if is_upd:
                    record["isUpdate"] = True
                self.create_metric_event(record)

    def create_metric_event(self, data):
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
                if 'id' in submitter.keys():
                    event["primarySubmitterId"] = submitter["id"]
                if 'team' in submitter.keys():
                    secondary_submitters = []
                    for team in data["submitter"]["team"]:
                        secondary_submitters.append(team["name"])
                    if secondary_submitters:
                        event["secondarySubmitterIds"] = secondary_submitters
            if data["isDelete"]:
                event["isDelete"] = True
                prod.produce(data, metric_event_input_topic, None)
            elif data["isUpdate"]:
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