import json
import logging
import random

from kafka import KafkaProducer
from configs.configs import kafka_bootstrap_server_host, ulca_dataset_topic_partitions


log = logging.getLogger('file')
topic_partition_map = {}

class Producer:

    def __init__(self):
        pass

    # Method to instantiate producer
    # Any other method that needs a producer will get it from here
    def instantiate(self):
        producer = KafkaProducer(bootstrap_servers=list(str(kafka_bootstrap_server_host).split(",")),
                                 api_version=(1, 0, 0),
                                 value_serializer=lambda x: json.dumps(x).encode('utf-8'))
        return producer

    # Method to push records to a topic in the kafka queue
    def produce(self, object_in, topic, partition_in):
        global topic_partition_map
        producer = self.instantiate()
        try:
            if object_in:
                if partition_in is None:
                    partition = random.choice(list(range(0, ulca_dataset_topic_partitions)))
                    if topic in topic_partition_map.keys():
                        while partition == topic_partition_map[topic]:
                            partition = random.choice(list(range(0, ulca_dataset_topic_partitions)))
                    topic_partition_map[topic] = partition
                    partition_push = partition
                else:
                    partition_push = partition_in
                producer.send(topic, value=object_in, partition=partition_push)
                log.info(f'Pushing to topic from validate: {topic} message: {object_in}')
            producer.flush()
        except Exception as e:
            log.exception(f'Exception in dataset validate while producing: {str(e)}', e)
