package com.reed.micro.gateway.feign.client;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

@FeignClient(name = "eureka-service")
public interface FeignService {

	@RequestMapping(value = "/echo", method = RequestMethod.GET)
	public String echo(@RequestParam("str") String str);

	@RequestMapping(value = "/obj", method = GET)
	public String findObj();

	/**
	 * 获取当前header，并设置到feign请求header中,通过feign转发给远程服务接口
	 * @param token
	 * @return
	 */
	//@Headers({ "X-User-Token: {X-User-Token}" })
	@RequestMapping(value = "/user/author", method = RequestMethod.GET)
	public String checkAuth(@RequestHeader("X-User-Token") String token);

}
