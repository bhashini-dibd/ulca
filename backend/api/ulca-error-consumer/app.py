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
log = logging.getLogger('file')

ulca_error_consumer = Flask(__name__)

if ENABLE_CORS:
    cors    =   CORS(ulca_error_consumer, resources={r"/api/*": {"origins": "*"}})

for blueprint in vars(routes).values():
    if isinstance(blueprint, Blueprint):
        ulca_error_consumer.register_blueprint(blueprint, url_prefix=context_path)


# Starts the kafka consumer in a different thread
def start_consumer():
    with ulca_error_consumer.test_request_context():
        try:
            error_consumer_process = Process(target=error_consume)
            error_consumer_process.start()

            error_processor_thread = ErrorProcessor(threading.Event())
            error_processor_thread.start()
        except Exception as e:
            log.exception(f'Exception while starting the ULCA error-service kafka consumer: {str(e)}')


if __name__ == '__main__':
    log.info("Error consumer service started <<<<<<<<<<<<>>>>>>>>>>>")
    start_consumer()
    ulca_error_consumer.run(host=app_host, port=app_port, threaded=True)