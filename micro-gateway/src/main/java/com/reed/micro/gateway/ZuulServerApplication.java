package com.reed.micro.gateway;

import javax.servlet.Filter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.sleuth.instrument.zuul.TraceZuulAutoConfiguration;
import org.springframework.context.annotation.Bean;

import com.reed.micro.gateway.filter.HystrixRequestContextServletFilter;

/**
 * zuul server
 *
 */
@EnableZuulProxy
@SpringBootApplication(exclude = { TraceZuulAutoConfiguration.class })
public class ZuulServerApplication {
	public static void main(String[] args) {

		SpringApplication.run(ZuulServerApplication.class, args);

	}

	/**
	 * 加载hystrix filter,初始化HystrixRequestContext
	 * 
	 * @return
	 */
	@Bean
	public FilterRegistrationBean HystrixFilterRegistration() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(HystrixFilter());
		registration.addUrlPatterns("/*");
		registration.setName("hystrixFilter");
		return registration;
	}

	@Bean(name = "hystrixFilter")
	public Filter HystrixFilter() {
		return new HystrixRequestContextServletFilter();
	}
}
