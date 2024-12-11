package com.audible;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value={"messages.properties", "validation.properties"})
public class NotificationMsApplication {
	
	private static final Logger logInfo = LoggerFactory.getLogger(NotificationMsApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(NotificationMsApplication.class, args);
	}

}
