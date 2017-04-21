package com.reed.micro.gateway.filter;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.ZuulFilter;
import com.reed.micro.gateway.feign.client.FeignService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class LogPreFilter extends ZuulFilter {

	private static Logger log = LoggerFactory.getLogger(LogPreFilter.class);

	@Autowired
	private FeignService feignService;

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		log.info(String.format("feign---------->%s---------->%s",
				feignService.echo(new Date().toString()),feignService.findObj()));
		log.info(String.format("PRE=============>:%s request to %s",
				request.getMethod(), request.getRequestURL().toString()));

		return null;
	}

}