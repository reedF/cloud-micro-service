package com.reed.cloud.micro.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ComponentScan;

/**
 * 注：
 * 1.SpringBootApplication默认扫描当前包及子包，如需扫描其他包请手动加入
 * 2.Hystrix是在“代理”模式下运行的，@HystrixCommand在本类内部未经过aop加强时是无效的，
 * 顾对@HystrixCommand这类命令的使用，需通过另一个类@Autowired注入此类时，方可生效使用，
 * 参见：http://stackoverflow.com/questions/29523604/hystrix-javanica-fallback-not-working-in-spring-cloud-1-0
 * 3.@HystrixCommand的fallbackmethod必须是本类内部定义，继承或其他类中的无效
 * @author reed
 *
 */
@SpringBootApplication
@EnableEurekaServer
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.reed.cloud.micro.service")
@EnableHystrix
@ComponentScan("com.reed.cloud.micro.service,com.reed.cloud.micro.feign")
public class ServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(ServiceApplication.class, args);
	}
}
