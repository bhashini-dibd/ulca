import json
import logging
import random
import string
from logging.config import dictConfig

from service.parallel import ParallelValidate
from service.asr import ASRValidate
from service.ocr import OCRValidate
from service.monolingual import MonolingualValidate
from service.asr_unlabeled import ASRUnlabeledValidate
from service.tts import TTSValidate
from service.transliteration import TransliterationValidate
from service.glossary import GlossaryValidate


from configs.configs import kafka_bootstrap_server_host, validate_input_topic, validate_consumer_grp, validate_output_topic, user_mode_real
from configs.configs import dataset_type_parallel, dataset_type_asr, dataset_type_ocr, dataset_type_monolingual, dataset_type_asr_unlabeled, dataset_type_tts, dataset_type_transliteration, dataset_type_glossary
from kafka import KafkaConsumer
from processtracker.processtracker import ProcessTracker
from kafkawrapper.producer import Producer
from kafkawrapper.redis_util import RedisUtil

log = logging.getLogger('file')

# Method to instantiate the kafka consumer
def instantiate(topics):
    consumer = KafkaConsumer(*topics,
                             bootstrap_servers=list(str(kafka_bootstrap_server_host).split(",")),
                             api_version=(1, 0, 0),
                             group_id=validate_consumer_grp,
                             auto_offset_reset='latest',
                             enable_auto_commit=True,
                             value_deserializer=lambda x: handle_json(x))
    return consumer


# Method to read and process the requests from the kafka queue
def consume():
    try:
        topics = [validate_input_topic]
        consumer = instantiate(topics)
        p_service, o_service, a_service, m_service, au_service, t_service, tl_service, g_service = ParallelValidate(), OCRValidate(), ASRValidate(), MonolingualValidate(), ASRUnlabeledValidate(), TTSValidate(), TransliterationValidate(), GlossaryValidate()
        pt = ProcessTracker()
        prod = Producer()
        rand_str = ''.join(random.choice(string.ascii_letters) for i in range(4))
        prefix = "DS-VALIDATE-" + "(" + rand_str + ")"
        log.info(f'{prefix} -- Running..........')
        while True:
            for msg in consumer:
                try:
                    data = msg.value
                    if data:
                        log.info(f'{prefix} | Received on Topic: " + msg.topic + " | Partition: {str(msg.partition)}')
                        if check_relay(data):
                            log.info(f'RELAY record ID: {data["record"]["id"]}, SRN: {data["serviceRequestNumber"]}')
                            break

                        srn = data["serviceRequestNumber"]
                        log.info(f'data received from ingest -- SRN {srn}')

                        if data["datasetType"] == dataset_type_parallel:
                            p_service.execute_validation_pipeline(data)
                        if data["datasetType"] == dataset_type_ocr:
                            o_service.execute_validation_pipeline(data)
                        if data["datasetType"] == dataset_type_asr:
                            a_service.execute_validation_pipeline(data)
                        if data["datasetType"] == dataset_type_monolingual:
                            m_service.execute_validation_pipeline(data)
                        if data["datasetType"] == dataset_type_asr_unlabeled:
                            au_service.execute_validation_pipeline(data)
                        if data["datasetType"] == dataset_type_tts:
                            t_service.execute_validation_pipeline(data)
                        if data["datasetType"] == dataset_type_transliteration:
                            tl_service.execute_validation_pipeline(data)
                        if data["datasetType"] == dataset_type_glossary:
                            g_service.execute_validation_pipeline(data)
                    else:
                        break
                except Exception as e:
                    log.exception(f'{prefix} Exception in ds validate consumer while consuming: {str(e)}', e)
    except Exception as e:
        log.exception(f'Exception in ds validate consumer while consuming: {str(e)}', e)


# Method that provides a deserialiser for the kafka record.
def handle_json(x):
    try:
        return json.loads(x.decode('utf-8'))
    except Exception as e:
        log.exception(f'Exception while deserialising: {str(e)}', e)
        return {}

# Method to check if a record is getting relayed
def check_relay(data):
    repo = RedisUtil()
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