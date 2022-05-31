#!/bin/python
import logging
from logging.config import dictConfig

from multiprocessing import Process
from api.apis import ulca_dataset_publish
from kafkawrapper.consumer import consume
from kafkawrapper.searchconsumer import search_consume
from kafkawrapper.deleteconsumer import delete_consume
from configs.configs import app_host, app_port

log = logging.getLogger('file')


# Starts the kafka consumers in different processes
def start_consumer():
    with ulca_dataset_publish.test_request_context():
        try:
            consumer_process = Process(target=consume)
            consumer_process.start()
            search_consumer_process = Process(target=search_consume)
            search_consumer_process.start()
            '''delete_consumer_process = Process(target=delete_consume)
            delete_consumer_process.start()'''
        except Exception as e:
            log.exception(f'Exception while starting the ULCA DS Publish kafka consumers: {e}', e)


if __name__ == '__main__':
    start_consumer()
    ulca_dataset_publish.run(host=app_host, port=app_port, threaded=True)

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