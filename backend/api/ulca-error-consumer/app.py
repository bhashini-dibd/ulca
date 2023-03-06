#!/bin/python
import threading
from flask import Flask
import logging
from multiprocessing import Process
from kafkawrapper.errorconsumer import error_consume
from configs.configs import app_host, app_port,ENABLE_CORS,context_path
from flask.blueprints import Blueprint
from flask_cors import CORS
import routes
from service.cronerror import ErrorProcessor
from configs.configs import consumer_count
log = logging.getLogger('file')

ulca_error_consumer = Flask(__name__)

if ENABLE_CORS:
    cors    =   CORS(ulca_error_consumer, resources={r"/api/*": {"origins": "*"}})

for blueprint in vars(routes).values():
    if isinstance(blueprint, Blueprint):
        ulca_error_consumer.register_blueprint(blueprint, url_prefix=context_path)


def start_consumer():
    with ulca_error_consumer.test_request_context():
        try:
            # Starts multiple kafka consumers in a different thread
            for i in range(1,consumer_count+1):
                error_consumer = Process(target=error_consume)
                error_consumer.start()
            # starts cron job for writing errors onto object store
            error_processor_cron = ErrorProcessor(threading.Event())
            error_processor_cron.start()
        except Exception as e:
            log.exception(f'Exception while starting the ULCA error-service kafka consumer: {str(e)}')


if __name__ == '__main__':
    log.info("Error consumer service started <<<<<<<<<<<<>>>>>>>>>>>")
    start_consumer()
    ulca_error_consumer.run(host=app_host, port=app_port, threaded=True)