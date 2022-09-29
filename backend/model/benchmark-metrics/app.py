#!/bin/python
import logging

from multiprocessing import Process

from models.metric_manager import MetricManager
from kafkawrapper.consumer import consume

log = logging.getLogger('file')


# Starts the kafka consumer in a different thread
def start_consumer():
    # with ulca_dataset_validate.test_request_context():
    try:
        consumer_process = Process(target=consume)
        consumer_process.start()
    except Exception as e:
        log.exception(f'Exception while starting the ULCA Model Metric Eval kafka consumer: {str(e)}')


if __name__ == '__main__':

    metric_mgr = MetricManager.getInstance()
    metric_mgr.load_metrics()

    start_consumer()