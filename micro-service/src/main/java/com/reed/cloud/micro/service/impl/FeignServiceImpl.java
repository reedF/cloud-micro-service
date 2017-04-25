package com.reed.cloud.micro.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import com.reed.cloud.micro.base.po.BasePo;
import com.reed.cloud.micro.base.service.BaseAbstractServiceImpl;
import com.reed.cloud.micro.service.FeignService;
import com.reed.cloud.micro.service.HystrixService;

@Service("feignService")
public class FeignServiceImpl extends BaseAbstractServiceImpl implements
		FeignService {

	@Autowired
	private DiscoveryClient client;

	@Autowired
	private HystrixService hystrixService;

	@Override
	public String echo(String str) {
		return super.echo(str);
	}

	@Override
	public ServiceInstance findServiceInstance() {
		return hystrixService.findServiceInstance();
	}

	@Override
	public String feign(String str) {
		return hystrixService.feign(str);
	}

	@Override
	public BasePo findObj() {
		return hystrixService.findObj();
	}

	@Override
	public String checkAuth(HttpServletRequest req) {
		String token = req.getHeader("X-User-Token");
		return token;
	}
	
	

}
