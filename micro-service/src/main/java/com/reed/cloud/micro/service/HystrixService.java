package com.reed.cloud.micro.service;

import org.springframework.cloud.client.ServiceInstance;

import com.reed.cloud.micro.base.po.BasePo;
import com.reed.cloud.micro.base.service.BaseService;

public interface HystrixService extends BaseService {

	public ServiceInstance findServiceInstance();

	public BasePo findObj();

	public String feign(String str);

	public BasePo fallBackCallObj();

}
