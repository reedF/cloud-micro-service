package com.reed.cloud.micro.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

import com.reed.cloud.micro.eureka.AbstractApplication;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.reed.cloud.micro.service")
@EnableHystrix
public class ClientApplication extends AbstractApplication{

	public static void main(String[] args) {
		SpringApplication.run(ClientApplication.class, args);
	}

}
