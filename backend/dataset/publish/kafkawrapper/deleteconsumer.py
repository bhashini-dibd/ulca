import json
import logging
import random
import string
from logging.config import dictConfig

from service.parallel import ParallelService
from service.asr import ASRService
from service.ocr import OCRService
from service.monolingual import MonolingualService

from configs.configs import kafka_bootstrap_server_host, delete_input_topic, publish_consumer_grp
from configs.configs import dataset_type_parallel, dataset_type_asr, dataset_type_ocr, dataset_type_monolingual
from kafka import KafkaConsumer

log = logging.getLogger('file')


# Method to instantiate the kafka consumer
def instantiate(topics):
    consumer = KafkaConsumer(*topics,
                             bootstrap_servers=list(str(kafka_bootstrap_server_host).split(",")),
                             api_version=(1, 0, 0),
                             group_id=publish_consumer_grp,
                             auto_offset_reset='latest',
                             enable_auto_commit=True,
                             value_deserializer=lambda x: handle_json(x))
    return consumer


# Method to read and process the requests from the kafka queue for dataset deletion
def delete_consume():
    try:
        topics = [delete_input_topic]
        consumer = instantiate(topics)
        p_service, m_service, a_service, o_service = ParallelService(), MonolingualService(), ASRService(), OCRService()
        rand_str = ''.join(random.choice(string.ascii_letters) for i in range(4))
        prefix = "DS-DELETE-" + "(" + rand_str + ")"
        log.info(f'{prefix} -- Running..........')
        while True:
            for msg in consumer:
                try:
                    data = msg.value
                    if data:
                        log.info(f'{prefix} | Received on Topic: " + msg.topic + " | Partition: {str(msg.partition)}')
                        if data["datasetType"] == dataset_type_parallel:
                            p_service.get_parallel_dataset(data)
                        if data["datasetType"] == dataset_type_ocr:
                            o_service.delete_ocr_dataset(data)
                        if data["datasetType"] == dataset_type_asr:
                            a_service.delete_asr_dataset(data)
                        if data["datasetType"] == dataset_type_monolingual:
                            m_service.delete_mono_dataset(data)
                    else:
                        break
                except Exception as e:
                    log.exception(f'{prefix} Exception in ds search consumer while consuming: {str(e)}', e)
    except Exception as e:
        log.exception(f'Exception in ds search consumer while consuming: {str(e)}', e)


# Method that provides a deserialiser for the kafka record.
def handle_json(x):
    try:
        return json.loads(x.decode('utf-8'))
    except Exception as e:
        log.exception(f'Exception while deserialising: {str(e)}', e)
        return {}

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