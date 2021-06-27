import json
import logging
import random
import string
from logging.config import dictConfig

from service.parallel import ParallelService
from service.asr import ASRService
from service.ocr import OCRService
from service.monolingual import MonolingualService

from configs.configs import kafka_bootstrap_server_host, publish_input_topic, publish_consumer_grp
from configs.configs import dataset_type_parallel, dataset_type_asr, dataset_type_ocr, dataset_type_monolingual
from kafka import KafkaConsumer
from processtracker.processtracker import ProcessTracker
from repository.datasetrepo import DatasetRepo

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


# Method to read and process the requests from the kafka queue
def consume():
    try:
        topics = [publish_input_topic]
        consumer = instantiate(topics)
        p_service, m_service, a_service, o_service = ParallelService(), MonolingualService(), ASRService(), OCRService()
        repo = DatasetRepo()
        rand_str = ''.join(random.choice(string.ascii_letters) for i in range(4))
        prefix = "DS-CONS-" + "(" + rand_str + ")"
        log.info(f'{prefix} -- Running..........')
        while True:
            for msg in consumer:
                try:
                    data = msg.value
                    if data:
                        log.info(f'{prefix} | Received on Topic: {msg.topic} Partition: {str(msg.partition)}')
                        if repo.search([data["record"]["id"]]):
                            log.info(f'RELAY found for record --- {data}')
                            break
                        else:
                            rec = {"srn": data["serviceRequestNumber"], "datasetId": data["datasetId"], "datasetType": data["datasetType"]}
                            repo.upsert(data["record"]["id"], rec)
                        if data["datasetType"] == dataset_type_parallel:
                            p_service.load_parallel_dataset_single(data)
                        if data["datasetType"] == dataset_type_ocr:
                            o_service.load_ocr_dataset_single(data)
                        if data["datasetType"] == dataset_type_asr:
                            a_service.load_asr_dataset_single(data)
                        if data["datasetType"] == dataset_type_monolingual:
                            m_service.load_monolingual_dataset_single(data)
                        break
                    else:
                        break
                except Exception as e:
                    log.exception(f'{prefix} Exception in ds consumer while consuming: {str(e)}', e)
    except Exception as e:
        log.exception(f'Exception in ds consumer while consuming: {str(e)}', e)


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