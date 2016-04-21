package com.reed.micro.server.zipkin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.sleuth.zipkin.stream.EnableZipkinStreamServer;

import zipkin.server.EnableZipkinServer;

/**
 * IDE中run main时，请指定vm parma:-Dspring.profiles.active=zipkin
 * @author reed
 *
 */
@SpringBootApplication
//只启动zipkin server
//@EnableZipkinServer
//使用mq：Rabbitmq\kafka接收trace数据时,使用@EnableZipkinStreamServer注解，以stream方式订阅msg
@EnableZipkinStreamServer
public class ZipkinServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZipkinServerApplication.class, args);
	}

}
