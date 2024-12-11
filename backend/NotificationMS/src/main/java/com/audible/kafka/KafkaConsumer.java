package com.audible.kafka;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class KafkaConsumer {

	private static final Logger LOGGER=LoggerFactory.getLogger(KafkaConsumer.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
//	@KafkaListener(topics="${spring.kafka.topic.name}",
//			groupId="${spring.kafka.consumer.group-id}")
	@KafkaListener(topics="book_topics",
	groupId="book")
	
	public void consume(String event) throws Exception{
		LOGGER.info(String.format("New book notification consumed => %s", event));
		@SuppressWarnings("unchecked")
		List<String> userIdsList=restTemplate.getForObject("http://localhost:6100/audible/user-api/users/userIds", ArrayList.class);
		for(String userId: userIdsList) {
			restTemplate.postForObject("http://localhost:6400/audible/notification-api/notification/add-book/"+event+"/user/"+userId, null, String.class);
		}
	}
	
	
}
