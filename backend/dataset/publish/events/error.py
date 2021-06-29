import csv
import json
import logging
import os
import uuid
from datetime import datetime
from configs.configs import error_event_input_topic, publish_error_code, \
     error_prefix, pt_publish_tool
from kafkawrapper.producer import Producer
from .errorrepo import ErrorRepo
from utils.datasetutils import DatasetUtils


log = logging.getLogger('file')
mongo_instance = None
prod = Producer()
error_repo = ErrorRepo()
utils = DatasetUtils()


class ErrorEvent:
    def __init__(self):
        pass

    def create_error_event(self, error_list):
        for error in error_list:
            try:
                event = {"eventType": "dataset-training", "messageType": "error", "code": publish_error_code.replace("XXX", error["code"]),
                         "eventId": f'{error["serviceRequestNumber"]}|{str(uuid.uuid4())}', "timestamp": str(datetime.now()),
                         "serviceRequestNumber": error["serviceRequestNumber"], "stage": pt_publish_tool, "datasetName": error["datasetName"],
                         "datasetType": error["datasetType"], "message": error["message"], "record": error["record"]}
                if 'originalRecord' in error.keys():
                    event["originalRecord"] = error["originalRecord"]
                prod.produce(event, error_event_input_topic, 0)
            except Exception as e:
                log.exception(e)
                continue