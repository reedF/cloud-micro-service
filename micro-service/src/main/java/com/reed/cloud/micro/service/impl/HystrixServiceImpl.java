package com.reed.cloud.micro.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.reed.cloud.micro.base.po.BasePo;
import com.reed.cloud.micro.base.service.BaseAbstractServiceImpl;
import com.reed.cloud.micro.service.HystrixService;

@Service
public class HystrixServiceImpl extends BaseAbstractServiceImpl implements
		HystrixService {

	@Autowired
	DiscoveryClient client;

	@Override
	@HystrixCommand(groupKey = "testGroup", fallbackMethod = "fallBackCallObj")
	public BasePo findObj() {
		BasePo r = new BasePo();
		ServiceInstance localInstance = findServiceInstance();
		r.setMonitors("serviceID:>>>>>" + localInstance.getServiceId() + ":"
				+ localInstance.getHost() + ":" + localInstance.getPort());
		return r;
	}

	@Override
	public ServiceInstance findServiceInstance() {
		return client.getLocalServiceInstance();
	}

	@Override
	@HystrixCommand(groupKey = "testGroup", fallbackMethod = "fallBackCall")
	public String feign(String str) {
		String s = Integer.valueOf(str) + ">>>>" + new Date().toString();
		return s;
	}

	@Override
	public BasePo fallBackCallObj() {
		BasePo r = new BasePo();
		r.setMonitors(super.fallBackCall());
		return r;
	}

	public String fallBackCall(String str, Throwable e) {
		return super.fallBackCall() + "\nex:" + e.getClass().getName()
				+ "\nmsg:" + e.getMessage();
	}

}
