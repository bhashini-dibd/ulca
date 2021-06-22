#!/bin/python
import logging
from multiprocessing import Process
from api.apis import ulca_error_consumer
from kafkawrapper.errorconsumer import error_consume
from configs.configs import app_host, app_port

log = logging.getLogger('file')


# Starts the kafka consumer in a different thread
def start_consumer():
    with ulca_error_consumer.test_request_context():
        try:
            error_consumer_process = Process(target=error_consume)
            error_consumer_process.start()
        except Exception as e:
            log.exception(f'Exception while starting the ULCA error-service kafka consumer: {str(e)}')


if __name__ == '__main__':
    start_consumer()
    ulca_error_consumer.run(host=app_host, port=app_port, threaded=True)