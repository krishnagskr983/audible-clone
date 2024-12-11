package com.audible;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value={"messages.properties", "validation.properties"})
public class PaymentMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentMsApplication.class, args);
	}

}
