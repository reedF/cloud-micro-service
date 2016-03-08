package com.reed.cloud.micro.service;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.reed.cloud.micro.base.po.BasePo;
import com.reed.cloud.micro.base.service.BaseService;

@FeignClient("eureka-service")
public interface FeignService extends BaseService {


	@RequestMapping(value = "/echo", method = RequestMethod.GET)
	public String echo(@RequestParam("str") String str);

	@RequestMapping(value = "/service", method = GET)
	public ServiceInstance findServiceInstance();

	@RequestMapping(value = "/feign", method = { RequestMethod.POST })
	public String feign(@RequestParam(value = "str") String str);

	@RequestMapping(value = "/obj", method = GET)
	public BasePo findObj();

}
