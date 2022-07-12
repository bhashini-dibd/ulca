import json
import logging
import random
import string
from logging.config import dictConfig

from service.asr import ASRMetricEvalHandler
from service.translation import TranslationMetricEvalHandler
from service.ocr import OcrMetricEvalHandler
from service.transliteration import TransliterationMetricEvalHandler

from configs.configs import kafka_bootstrap_server_host, metric_eval_input_topic, metric_eval_consumer_grp
from configs.configs import model_task_type_translation, model_task_type_asr, model_task_type_ocr, model_task_type_transliteration
from kafka import KafkaConsumer
# from processtracker.processtracker import ProcessTracker
# from kafkawrapper.producer import Producer
from kafkawrapper.redis_util import RedisUtil

log = logging.getLogger('file')

# Method to instantiate the kafka consumer
def instantiate(topics):
    consumer = KafkaConsumer(*topics,
                             bootstrap_servers=list(str(kafka_bootstrap_server_host).split(",")),
                             api_version=(1, 0, 0),
                             group_id=metric_eval_consumer_grp,
                             auto_offset_reset='latest',
                             enable_auto_commit=True,
                             value_deserializer=lambda x: handle_json(x))
    return consumer


# Method to read and process the requests from the kafka queue
def consume():
    try:
        topics = [metric_eval_input_topic]
        consumer = instantiate(topics)
        asr_handler, translation_handler, ocr_handler, transliteration_handler = ASRMetricEvalHandler(), TranslationMetricEvalHandler(), OcrMetricEvalHandler(), TransliterationMetricEvalHandler()
        rand_str = ''.join(random.choice(string.ascii_letters) for i in range(4))
        prefix = "MODEL-METRIC-EVAL-" + "(" + rand_str + ")"
        log.info(f'{prefix} -- Running..........')
        while True:
            for msg in consumer:
                try:
                    data = msg.value
                    if data:
                        log.info(f'{prefix} | Received on Topic: " + msg.topic + " | Partition: {str(msg.partition)}')
                        if check_relay(data):
                            log.info(f'RELAY record with benchmarkingProcessId: {data["benchmarkingProcessId"]}')
                            break

                        benchmarking_process_id = data["benchmarkingProcessId"]
                        log.info(f'data received from benchmarking -- id {benchmarking_process_id}')

                        if data["modelTaskType"] == model_task_type_translation:
                            translation_handler.execute_translation_metric_eval(data)
                        if data["modelTaskType"] == model_task_type_asr:
                            asr_handler.execute_asr_metric_eval(data)
                        if data["modelTaskType"] == model_task_type_ocr:
                            ocr_handler.execute_ocr_metric_eval(data)
                        if data["modelTaskType"] == model_task_type_transliteration:
                            transliteration_handler.execute_transliteration_metric_eval(data)

                    else:
                        break
                except Exception as e:
                    log.exception(f'{prefix} Exception in model metric eval consumer while consuming: {str(e)}')
    except Exception as e:
        log.exception(f'Exception in model metric eval consumer while consuming: {str(e)}')


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
    record = repo.search([data["benchmarkingProcessId"]])
    if record:
        return True
    else:
        rec = {"benchmarkingProcessId": data["benchmarkingProcessId"], "modelTaskType": data["modelTaskType"]}
        repo.upsert(data["benchmarkingProcessId"], rec, True)
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