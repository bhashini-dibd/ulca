package com.ulca.dataset.kakfa.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.ulca.dataset.kakfa.model.DatasetIngest;


@Configuration
@EnableKafka
public class KafkaDatasetIngestConsumerConfig {

	
	@Value("${kafka.ulca.bootstrap.server.host}")
    private String bootstrapAddress;

	// config for json data
	
	@Bean
	public ConsumerFactory<String, DatasetIngest> datasetIngestConsumerFactory() {
		Map<String, Object> configs = new HashMap<>();
		configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		configs.put(ConsumerConfig.GROUP_ID_CONFIG, "${KAFKA_ULCA_DS_INGEST_IP_TOPIC_GROUP_ID}");
		return new DefaultKafkaConsumerFactory<>(configs, new StringDeserializer(), new JsonDeserializer<>(DatasetIngest.class));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, DatasetIngest> datasetIngestKafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, DatasetIngest> factory = new ConcurrentKafkaListenerContainerFactory<String, DatasetIngest>();
		factory.setConsumerFactory(datasetIngestConsumerFactory());
		return factory;
	}

}
