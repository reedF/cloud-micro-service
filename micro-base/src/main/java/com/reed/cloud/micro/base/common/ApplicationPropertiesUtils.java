package com.reed.cloud.micro.base.common;

import org.springframework.beans.factory.annotation.Value;

public class ApplicationPropertiesUtils {

	@Value("${spring.application.name}")
	public static String APP_NAME;
}
