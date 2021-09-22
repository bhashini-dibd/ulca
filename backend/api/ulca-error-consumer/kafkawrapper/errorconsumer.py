import json
import logging
import random
import string
from logging.config import dictConfig


from configs.configs import kafka_bootstrap_server_host, error_event_input_topic, publish_consumer_grp
from kafka import KafkaConsumer
from events.error import ErrorEvent

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
def error_consume():
    try:
        topics = [error_event_input_topic]
        consumer = instantiate(topics)
        error_event = ErrorEvent()
        rand_str = ''.join(random.choice(string.ascii_letters) for i in range(4))
        prefix = "DS-ERROR-" + "(" + rand_str + ")"
        log.info(f'{prefix} -- Running..........')
        while True:
            for msg in consumer:
                try:
                    data = msg.value
                    if data:
                        log.info(f'{prefix} | Received on Topic: {msg.topic} Partition: {str(msg.partition)}')
                        error_event.write_error_in_redis(data)
                        error_event.write_error_in_mongo(data)
                    else:
                        break
                except Exception as e:
                    log.exception(f'{prefix} Exception in ds error consumer while consuming: {str(e)}', e)
                    break
    except Exception as e:
        log.exception(f'Exception in ds error consumer while consuming: {str(e)}')


# Method that provides a deserialiser for the kafka record.
def handle_json(x):
    try:
        return json.loads(x.decode('utf-8'))
    except Exception as e:
        log.exception(f'Exception while deserialising: {str(e)}')
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