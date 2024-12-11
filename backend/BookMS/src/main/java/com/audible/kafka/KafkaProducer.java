package com.audible.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

	private static final Logger LOGGER=LoggerFactory.getLogger(KafkaProducer.class);
	
	private NewTopic topic;
	
	private KafkaTemplate<String, String> kafkaTemplate;
	
	public KafkaProducer(NewTopic topic, KafkaTemplate<String, String> kafkaTemplate) {
		this.topic=topic;
		this.kafkaTemplate=kafkaTemplate;
	}
	
	public void sendMessage(String event) {
		LOGGER.info(String.format("New book notification produced => %s", event));
		Message<String> message=MessageBuilder
				.withPayload(event)
				.setHeader(KafkaHeaders.TOPIC, topic.name())
				.build();
		kafkaTemplate.send(message);
				
	}
	
}
