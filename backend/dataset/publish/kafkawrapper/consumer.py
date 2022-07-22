import json
import logging
import random
import string
from logging.config import dictConfig

from service.parallel import ParallelService
from service.asr import ASRService
from service.ocr import OCRService
from service.tts import TTSService
from service.monolingual import MonolingualService
from service.asrunlabeled import ASRUnlabeledService
from service.transliteration import TransliterationService
from service.glossary import GlossaryService

from configs.configs import kafka_bootstrap_server_host, publish_input_topic, publish_consumer_grp, user_mode_real
from configs.configs import dataset_type_parallel, dataset_type_asr, dataset_type_ocr, dataset_type_monolingual, \
    dataset_type_asr_unlabeled, dataset_type_tts, dataset_type_transliteration, dataset_type_glossary
from kafka import KafkaConsumer
from repository.datasetrepo import DatasetRepo

log = logging.getLogger('file')


# Method to instantiate the kafka consumer
def instantiate(topics):
    consumer = KafkaConsumer(*topics,
                             bootstrap_servers=list(str(kafka_bootstrap_server_host).split(",")),
                             api_version=(1, 0, 0),
                             group_id=publish_consumer_grp,
                             auto_offset_reset='latest',
                             max_poll_interval_ms=300000,
                             enable_auto_commit=True,
                             value_deserializer=lambda x: handle_json(x))
    return consumer


# Method to read and process the requests from the kafka queue for submit dataset action
def consume():
    try:
        topics = [publish_input_topic]
        consumer = instantiate(topics)
        p_service, m_service, a_service, o_service, au_service, tts_service, trans_service, glos_service = ParallelService(), MonolingualService(), \
                                                                              ASRService(), OCRService(), \
                                                                              ASRUnlabeledService(), TTSService(), TransliterationService(), GlossaryService()
        rand_str = ''.join(random.choice(string.ascii_letters) for i in range(4))
        prefix = "DS-CONS-" + "(" + rand_str + ")"
        log.info(f'{prefix} -- Running..........')
        while True:
            for msg in consumer:
                try:
                    data = msg.value
                    if data:
                        log.info(f'{prefix} | Received on Topic: {msg.topic} Partition: {str(msg.partition)}')
                        if check_relay(data):
                            break
                        log.info(
                            f'PROCESSING - start - ID: {data["record"]["id"]}, Dataset: {data["datasetType"]}, SRN: {data["serviceRequestNumber"]}')
                        if data["datasetType"] == dataset_type_parallel:
                            p_service.load_parallel_dataset(data)
                        if data["datasetType"] == dataset_type_ocr:
                            o_service.load_ocr_dataset(data)
                        if data["datasetType"] == dataset_type_asr:
                            a_service.load_asr_dataset(data)
                        if data["datasetType"] == dataset_type_monolingual:
                            m_service.load_monolingual_dataset(data)
                        if data["datasetType"] == dataset_type_asr_unlabeled:
                            au_service.load_asr_unlabeled_dataset(data)
                        if data["datasetType"] == dataset_type_tts:
                            tts_service.load_tts_dataset(data)
                        if data["datasetType"] == dataset_type_transliteration:
                            trans_service.load_transliteration_dataset(data)
                        if data["datasetType"] == dataset_type_glossary:
                            glos_service.load_glossary_dataset(data)
                        log.info(f'PROCESSING - end - ID: {data["record"]["id"]}, Dataset: {data["datasetType"]}, SRN: {data["serviceRequestNumber"]}')
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


# Method to check if a record is getting relayed
def check_relay(data):
    repo = DatasetRepo()
    record = repo.search([data["record"]["id"]])
    if record:
        record = record[0]
        if 'mode' in record.keys():
            if record['mode'] == user_mode_real:
                log.info(f'RELAY record ID: {data["record"]["id"]}, SRN: {data["serviceRequestNumber"]}')
                return True
            else:
                rec = {"srn": data["serviceRequestNumber"], "datasetId": data["datasetId"],
                       "mode": data["userMode"], "datasetType": data["datasetType"]}
                repo.upsert(data["record"]["id"], rec, True)
                return False
        else:
            log.info(f'RELAY record ID: {data["record"]["id"]}, SRN: {data["serviceRequestNumber"]}')
            return True
    else:
        rec = {"srn": data["serviceRequestNumber"], "datasetId": data["datasetId"], "mode": data["userMode"],
               "datasetType": data["datasetType"]}
        repo.upsert(data["record"]["id"], rec, True)
        return False


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
