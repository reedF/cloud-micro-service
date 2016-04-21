package com.reed.micro.server.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.sleuth.instrument.hystrix.SleuthHystrixAutoConfiguration;
import org.springframework.cloud.sleuth.stream.SleuthStreamAutoConfiguration;

/**
 * 1.由于同时加载zipkin相关的jar依赖，故在单独启动Eureka server时，无需启动sleuth等配置
 * 2.IDE中run main时，请指定vm parma:-Dspring.profiles.active=eureka
 * @author reed
 *
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class,
		SleuthHystrixAutoConfiguration.class,
		SleuthStreamAutoConfiguration.class })
@EnableEurekaServer
public class EurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaServerApplication.class, args);
	}

}
