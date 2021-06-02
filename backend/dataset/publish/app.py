#!/bin/python
import logging

from multiprocessing import Process
from api.apis import ulca_dataset_publish
from kafkawrapper.consumer import consume
from kafkawrapper.searchconsumer import search_consume
from kafkawrapper.deleteconsumer import delete_consume
from configs.configs import app_host, app_port

log = logging.getLogger('file')


# Starts the kafka consumer in a different thread
def start_consumer():
    with ulca_dataset_publish.test_request_context():
        try:
            consumer_process = Process(target=consume)
            consumer_process.start()
            search_consumer_process = Process(target=search_consume)
            search_consumer_process.start()
            delete_consumer_process = Process(target=delete_consume)
            delete_consumer_process.start()
        except Exception as e:
            log.exception(f'Exception while starting the ULCA DS Publish kafka consumer: {str(e)}')


if __name__ == '__main__':
    start_consumer()
    ulca_dataset_publish.run(host=app_host, port=app_port, threaded=True)