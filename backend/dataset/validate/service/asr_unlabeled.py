import logging
import hashlib
from datetime import datetime
from logging.config import dictConfig
from kafkawrapper.producer import Producer
from models.validation_pipeline import ValidationPipeline
from configs.configs import validate_output_topic, ulca_dataset_topic_partitions
from processtracker.processtracker import ProcessTracker
from events.error import ErrorEvent

log = logging.getLogger('file')

prod = Producer()
pt = ProcessTracker()
error_event = ErrorEvent()

class ASRUnlabeledValidate:
    def __init__(self):
        pass

    def execute_validation_pipeline(self, request):
        try:
            log.info("Executing ASR-Unlabeled dataset validation....  {}".format(datetime.now()))
            duration = 0

            v_pipeline = ValidationPipeline.getInstance()
            res = v_pipeline.runAsrUnlabeledValidators(request)
            if 'durationInSeconds' in request['record'].keys():
                duration = request["record"]["durationInSeconds"]

            if res:
                log.info("Validation complete....  {}".format(res))
                # Produce event for publish
                if res["status"] == "SUCCESS":
                    partition_key = request["record"]["audioHash"]
                    partition_no = int(partition_key,16)%ulca_dataset_topic_partitions
                    prod.produce(request, validate_output_topic, partition_no)
                else:
                    error = {"serviceRequestNumber": request["serviceRequestNumber"], "datasetType": request["datasetType"],
                             "message": res["message"], "code": res["code"], "record": request["record"], "datasetName": request["datasetName"]}
                    error_event.create_error_event(error)

                # Update task tracker
                tracker_data = {"status": res["status"], "code": res["message"], "serviceRequestNumber": request["serviceRequestNumber"], "currentRecordIndex": request["currentRecordIndex"], "duration": duration}
                pt.update_task_details(tracker_data)
            else:
                log.info("Exception occured, validation result: {}".format(res))
                tracker_data = {"status": "FAILED", "duration": duration}
                pt.update_task_details(tracker_data)
        except Exception as e:
            log.exception(f"Exception in validation of asr-unlabeled dataset: {str(e)}", e)
            tracker_data = {"status": "FAILED", "duration": duration}
            pt.update_task_details(tracker_data)
            return {"message": "EXCEPTION while validating dataset!!", "status": "FAILED"}


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