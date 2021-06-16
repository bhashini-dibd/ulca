import logging
from datetime import datetime
from logging.config import dictConfig
from kafkawrapper.producer import Producer
from models.validation_pipeline import ValidationPipeline
from configs.configs import validate_output_topic
from processtracker.processtracker import ProcessTracker
from events.error import ErrorEvent

log = logging.getLogger('file')

prod = Producer()
pt = ProcessTracker()
error_event = ErrorEvent()

class ASRValidate:
    def __init__(self):
        pass

    def execute_validation_pipeline(self, request):
        try:
            log.info("Executing ASR dataset validation....  {}".format(datetime.now()))
            v_pipeline = ValidationPipeline.getInstance()
            res = v_pipeline.runAsrValidators(request)
            if res:
                log.info("Validation complete....  {}".format(res))
                # Produce event for publish
                if res["status"] == "SUCCESS":
                    prod.produce(request, validate_output_topic, None)
                else:
                    error = {"serviceRequestNumber": request["serviceRequestNumber"], "datasetType": request["datasetType"],
                             "message": res["message"], "code": res["code"], "record": request["record"]}
                    error_event.create_error_event(error)

                # Update task tracker
                tracker_data = {"status": res["status"], "code": res["message"], "serviceRequestNumber": request["serviceRequestNumber"], "currentRecordIndex": request["currentRecordIndex"]}
                pt.create_task_event(tracker_data)

        except Exception as e:
            log.exception(e)
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