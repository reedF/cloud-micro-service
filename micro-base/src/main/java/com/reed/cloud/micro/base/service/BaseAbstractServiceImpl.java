package com.reed.cloud.micro.base.service;


public abstract class BaseAbstractServiceImpl implements BaseService {

	@Override
	public String echo(String str) {

		return str;
	}

	@Override
	public String fallBackCall() {
		String fallback = ("FAILED SERVICE CALL! - FALLING BACK");
		return fallback;
	}
}
