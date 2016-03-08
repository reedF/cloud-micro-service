package com.reed.cloud.micro.service.test;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.reed.cloud.micro.eureka.ServiceApplication;
import com.reed.cloud.micro.feign.controller.FeignController;
import com.reed.cloud.micro.service.FeignService;
import com.reed.cloud.micro.service.HystrixService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ServiceApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class AppTest {
	private final static String url = "http://localhost:8761";
	@Autowired
	private WebApplicationContext wac;
	private MockMvc mvc;
	@Autowired
	private HystrixService testService;
	@Autowired
	@Qualifier("feignService")
	private FeignService feign;

	@Before
	public void setUp() {
		// test feign controller
		// mvc = MockMvcBuilders.standaloneSetup(new FeignController()).build();
		mvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void testService() throws Exception {
		String str = "test";
		Assert.assertEquals(str, testService.echo(str));
	}

	/**
	 * need to start server
	 * 
	 * @throws Exception
	 */
	@Test
	public void testFeign() throws Exception {
		String str = "1";
		Assert.assertEquals(true, feign.feign(str).startsWith(str));
	}

	/**
	 * need to start server
	 * 
	 * @throws Exception
	 */
	@Test
	public void testHome() throws Exception {
		ResponseEntity<String> entity = new TestRestTemplate().getForEntity(url
				+ "/service", String.class);
		assertEquals(HttpStatus.OK, entity.getStatusCode());
	}

	@Test
	public void testRest() throws Exception {
		String str = "1";
		ResultActions actions = mvc.perform(MockMvcRequestBuilders
				.post("/feign").param("str", str)
				.accept(MediaType.APPLICATION_JSON_UTF8));
		ContentResultMatchers c = content();
		actions.andExpect(status().isOk());
		String r = actions.andReturn().getResponse().getContentAsString();
		System.out.println(r);
	}
}
