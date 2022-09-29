package com.ulca.dataset.download.kafka.config;

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

import com.ulca.dataset.kakfa.model.FileDownload;


@Configuration
@EnableKafka
public class KafkaFileDownloadConsumerConfig {

	
	@Value("${kafka.ulca.bootstrap.server.host}")
    private String bootstrapAddress;

	// config for json data
	
	@Bean
	public ConsumerFactory<String, FileDownload> filedownloadConsumerFactory() {
		Map<String, Object> configs = new HashMap<>();
		configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		configs.put(ConsumerConfig.GROUP_ID_CONFIG, "${KAFKA_ULCA_DS_FILEDOWNLOAD_IP_TOPIC_GROUP_ID}");
		return new DefaultKafkaConsumerFactory<>(configs, new StringDeserializer(), new JsonDeserializer<>(FileDownload.class));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, FileDownload> filedownloadKafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, FileDownload> factory = new ConcurrentKafkaListenerContainerFactory<String, FileDownload>();
		factory.setConsumerFactory(filedownloadConsumerFactory());
		return factory;
	}

}
