package com.reed.cloud.micro.eureka;

import javax.servlet.Filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.zipkin.ZipkinSpanReporter;
import org.springframework.context.annotation.Bean;

import com.reed.cloud.micro.filter.HystrixRequestContextServletFilter;

/**
 * 1.集成spring-sleuth与Hystrix，使用zipkin实现跟踪链数据，如不继承本类，在使用zipkin时，Hystrix会无效，报异常：
 * HystrixRequestContext.initializeContext() must be called at the beginning of
 * each request before RequestVariable functionality can be used.
 * 2.引入spring-cloud-starter-zipkin依赖，实现跟踪链，
 * spring-cloud-starter-zipkin会同时引入spring-cloud-starter-sleuth和spring-cloud-sleuth-zipkin，无需单配二者的依赖,
 * 但同时引入二者时，spring-cloud-sleuth-zipkin不可与spring-cloud-sleuth-stream同时使用，
 * 否则会有冲突导致org.springframework.cloud.sleuth.stream.HostLocator无法加载，
 * 无法使用stream传送trace data，
 * 故二者只可使用其一,如二者的依赖包都存在时，可通过enabled配置哪个启动：
 * (1)Http直连zipkin方式：
 * 引入spring-cloud-sleuth-zipkin jar, 
 * 配置文件中使用：
 * spring.zipkin:baseUrl=http://${zipkin_server}:9411/
 * spring.zipkin.enabled=true 
 * spring.stream.enabled=false
 * (2)Stream方式：
 * 引入spring-cloud-starter-sleuth和spring-cloud-sleuth-stream及stream相关的binder,如：spring-cloud-starter-stream-rabbit, 
 * 配置文件中使用： 
 * spring.stream.enabled=true
 * spring.zipkin.enabled=false 
 * 同时配置相关stream接收方式：Rabbitmq\kafka\redis
 * 
 * @author reed
 *
 */
public abstract class AbstractApplication {

	private static final Log log = LogFactory.getLog(AbstractApplication.class);

	@Autowired(required = false)
	private Span sleuthSpan;

	// Use this for debugging (or if there is no Zipkin server running on port
	// 9411)
	/**
	 * 无 zipkin server时，此方法作为输出查看trace data， 通过配置文件配置开启调试
	 * sample.zipkin.enabled=false spring.zipkin.enabled=true
	 */
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
