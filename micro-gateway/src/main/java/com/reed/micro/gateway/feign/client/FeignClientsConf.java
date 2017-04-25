package com.reed.micro.gateway.feign.client;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.zuul.context.RequestContext;

import feign.RequestInterceptor;
import feign.RequestTemplate;

//@Configuration
public class FeignClientsConf {
	@Bean
	public RequestInterceptor headerInterceptor() {
		return new RequestInterceptor() {
			@Override
			public void apply(RequestTemplate requestTemplate) {
				RequestContext ctx = RequestContext.getCurrentContext();
				HttpServletRequest request = ctx.getRequest();
				Enumeration<String> headerNames = request.getHeaderNames();
				if (headerNames != null) {
					while (headerNames.hasMoreElements()) {
						String name = headerNames.nextElement();
						Enumeration<String> values = request.getHeaders(name);
						while (values.hasMoreElements()) {
							String value = values.nextElement();
							requestTemplate.header(name, value);
						}
					}
				}
			}
		};
	}
}