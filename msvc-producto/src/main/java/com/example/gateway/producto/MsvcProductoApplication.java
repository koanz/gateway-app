package com.example.gateway.producto;

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
@ComponentScan(basePackages = {"com.example.gateway.producto", "com.example.gateway.commons.mappers"})
public class MsvcProductoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcProductoApplication.class, args);
	}

}
