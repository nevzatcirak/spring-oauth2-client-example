package com.nevzatcirak.example;

import com.nevzatcirak.example.config.AppProperties;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class SpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringApplication.class, args);
	}
}
