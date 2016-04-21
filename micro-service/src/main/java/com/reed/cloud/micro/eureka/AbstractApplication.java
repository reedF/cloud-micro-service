package com.reed.cloud.micro.eureka;

import javax.servlet.Filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.cloud.sleuth.zipkin.ZipkinSpanReporter;
import org.springframework.context.annotation.Bean;

import com.reed.cloud.micro.filter.HystrixRequestContextServletFilter;

/**
 * 集成spring-sleuth与Hystrix，使用zipkin实现跟踪链数据，如不继承本类，在使用zipkin时，Hystrix会无效，报异常：
 * HystrixRequestContext.initializeContext() must be called at the beginning of each request before RequestVariable functionality can be used.
 * @author reed
 *
 */
public abstract class AbstractApplication {

	private static final Log log = LogFactory.getLog(ServiceApplication.class);

	// Use this for debugging (or if there is no Zipkin server running on port
	// 9411)
	@Bean
	@ConditionalOnProperty(value = "sample.zipkin.enabled", havingValue = "false")
	public ZipkinSpanReporter spanCollector() {
		return new ZipkinSpanReporter() {
			@Override
			public void report(zipkin.Span span) {
				log.info(String.format("Reporting span [%s]", span));
			}
		};
	}

	/**
	 * 加载hystrix filter,初始化HystrixRequestContext
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
