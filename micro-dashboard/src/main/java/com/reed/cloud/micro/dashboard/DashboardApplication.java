package com.reed.cloud.micro.dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 1.通过http://{turbine_hostname:port}/turbine.stream查看集群状态时，
 * 需在application.yml中配置turbine相关参数
 * 2.通过http://{hystrix_nodeIp:port}/hystrix.stream查询单个节点状态
 *
 */
@SpringBootApplication
@EnableEurekaClient
@EnableHystrixDashboard
@EnableTurbine
@Controller
public class DashboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(DashboardApplication.class, args);
	}

	@RequestMapping("/")
	public String index() {
		return "forward:/hystrix";
	}

}
