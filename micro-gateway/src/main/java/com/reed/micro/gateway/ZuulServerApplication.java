package com.reed.micro.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import com.reed.micro.gateway.feign.client.FeignClientsConf;
import com.reed.micro.gateway.filter.AuthFilter;
import com.reed.micro.gateway.filter.LogPostFilter;
import com.reed.micro.gateway.filter.LogPreFilter;

/**
 * zuul server
 *
 */
@EnableZuulProxy
@EnableFeignClients
@SpringBootApplication(exclude = { FeignClientsConf.class })
public class ZuulServerApplication {
	public static void main(String[] args) {

		SpringApplication.run(ZuulServerApplication.class, args);

	}

	/**
	 * 新版hystrix无需显式加载hystrix filter,初始化HystrixRequestContext
	 * 
	 * @return
	 */
//	@Bean
//	public FilterRegistrationBean HystrixFilterRegistration() {
//		FilterRegistrationBean registration = new FilterRegistrationBean();
//		registration.setFilter(HystrixFilter());
//		registration.addUrlPatterns("/*");
//		registration.setName("hystrixFilter");
//		return registration;
//	}
//
//	@Bean(name = "hystrixFilter")
//	public Filter HystrixFilter() {
//		return new HystrixRequestContextServletFilter();
//	}

	/**
	 * zuul filters
	 * @return
	 */
	@Bean
	public LogPreFilter logpre() {
		return new LogPreFilter();
	}

	@Bean
	public LogPostFilter logpost() {
		return new LogPostFilter();
	}

	@Bean
	public AuthFilter authRoute() {
		return new AuthFilter();
	}
}
