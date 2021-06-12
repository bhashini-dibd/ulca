import logging
import uuid
from datetime import datetime
from logging.config import dictConfig
from configs.configs import metric_event_input_topic, user_mode_pseudo
from kafkawrapper.producer import Producer

log = logging.getLogger('file')


mongo_instance = None
prod = Producer()

class MetricEvent:
    def __init__(self):
        pass

    def build_metric_event(self, records, metadata, is_del, is_upd):
        if metadata["userMode"] == user_mode_pseudo:
            return
        if not isinstance(records, list):
            records["serviceRequestNumber"], records["userId"] = metadata["serviceRequestNumber"], metadata["userId"]
            records["datasetType"] = metadata["datasetType"]
            if is_del:
                records["isDelete"] = True
            if is_upd:
                records["isUpdate"] = True
            self.create_metric_event(records)
        else:
            for record in records:
                record["serviceRequestNumber"], record["userId"] = metadata["serviceRequestNumber"], metadata[
                    "userId"]
                record["datasetType"] = metadata["datasetType"]
                if is_del:
                    record["isDelete"] = True
                if is_upd:
                    record["isUpdate"] = True
                self.create_metric_event(record)

    def create_metric_event(self, data):
        log.info(f'Publishing BI metric event for srn -- {data["serviceRequestNumber"]}')
        try:
            event = {"eventType": "dataset-training", "eventId": f'{data["serviceRequestNumber"]}|{data["id"]}',
                     "timestamp": str(datetime.now()), "submitterId": data["userId"], "datasetType": data["datasetType"]}
            if 'sourceLanguage' in data.keys():
                event["sourceLanguage"] = data["sourceLanguage"]
            if 'targetLanguage' in data.keys():
                event["targetLanguage"] = data["targetLanguage"]
            if 'domain' in data.keys():
                event["domains"] = data["domain"]
            if 'license' in data.keys():
                event["license"] = data["license"]
            if 'collectionSource' in data.keys():
                event["collectionSource"] = data["collectionSource"]
            if 'submitter' in data.keys():
                submitter = data["submitter"][0]
                if 'id' in submitter.keys():
                    event["primarySubmitterId"] = submitter["id"]
                if 'team' in submitter.keys():
                    secondary_submitters = []
                    for team in submitter["team"]:
                        secondary_submitters.append(team["name"])
                    if secondary_submitters:
                        event["secondarySubmitterIds"] = secondary_submitters
            if 'collectionMethod' in data.keys():
                cm = data["collectionMethod"][0]
                if 'collectionDescription' in cm.keys():
                    event["collectionMethod_collectionDescriptions"] = cm["collectionDescription"]
                if 'collectionDetails' in cm.keys():
                    if cm["collectionDetails"]:
                        if 'alignmentTool' in cm["collectionDetails"].keys():
                            event["collectionMethod_collectionDetails_alignmentTool"] = cm["collectionDetails"]["alignmentTool"]
            if 'format' in data.keys():
                event["format"] = data["format"]
            if 'channel' in data.keys():
                event["channel"] = data["channel"]
            if 'samplingRate' in data.keys():
                event["samplingRate"] = data["samplingRate"]
            if 'bitsPerSample' in data.keys():
                event["bitsPerSample"] = data["bitsPerSample"]
            if 'gender' in data.keys():
                event["gender"] = data["gender"]
            if 'isDelete' in data.keys():
                event["isDelete"] = True
                prod.produce(data, metric_event_input_topic, None)
            elif 'isUpdate' in data.keys():
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