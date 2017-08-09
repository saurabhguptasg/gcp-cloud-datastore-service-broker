package com.google.cloud.datastore.cf.sb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableAutoConfiguration
@SpringBootApplication
public class ServiceBrokerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceBrokerApplication.class, args);
	}
}
