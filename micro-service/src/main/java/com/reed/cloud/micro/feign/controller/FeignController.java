package com.reed.cloud.micro.feign.controller;

import org.springframework.web.bind.annotation.RestController;

import com.reed.cloud.micro.service.impl.FeignServiceImpl;

@RestController("feignController")
public class FeignController extends FeignServiceImpl {

}
