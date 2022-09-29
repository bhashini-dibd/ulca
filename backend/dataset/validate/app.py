#!/bin/python
import logging

from multiprocessing import Process
from api.apis import ulca_dataset_validate
from models.validation_pipeline import ValidationPipeline
from kafkawrapper.consumer import consume
from configs.configs import app_host, app_port

log = logging.getLogger('file')


# Starts the kafka consumer in a different thread
def start_consumer():
    with ulca_dataset_validate.test_request_context():
        try:
            consumer_process = Process(target=consume)
            consumer_process.start()
        except Exception as e:
            log.exception(f'Exception while starting the ULCA DS Validate kafka consumer: {str(e)}')


if __name__ == '__main__':

    v_pipeline = ValidationPipeline.getInstance()
    v_pipeline.loadValidators()

    start_consumer()

    ulca_dataset_validate.run(host=app_host, port=app_port, threaded=True)