package com.reed.cloud.micro.rest.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reed.cloud.micro.base.po.BasePo;
import com.reed.cloud.micro.service.FeignService;

@RestController
public class IndexController {

	@Autowired
	private FeignService feign;

	@RequestMapping("/")
	public String index() {
		String str = "hello>>>>" + new Date().toString();
		return feign.echo(str);
	}

	@RequestMapping("/obj")
	public BasePo obj() {
		return feign.findObj();
	}

	@RequestMapping("/hytrix")
	public String hytrix(@RequestParam("str") String str) {
		return feign.feign(str);
	}

}
