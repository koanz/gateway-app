package com.example.gateway.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EntityScan({"com.example.gateway.commons.entities"})
@ComponentScan(basePackages = {"com.example.gateway.product", "com.example.gateway.commons.dtos", "com.example.gateway.commons.mappers"})
public class MsvcProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcProductApplication.class, args);
	}

}
