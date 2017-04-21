package com.reed.micro.gateway.feign.client;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.reed.cloud.micro.base.po.BasePo;

@FeignClient("eureka-service")
public interface FeignService {

	@RequestMapping(value = "/echo", method = RequestMethod.GET)
	public String echo(@RequestParam("str") String str);

	@RequestMapping(value = "/obj", method = GET)
	public BasePo findObj();

}
