package com.reed.micro.gateway;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ZuulServerApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class AppTest {
	private final static String url = "http://localhost:8761";
	@Autowired
	private WebApplicationContext wac;
	private MockMvc mvc;

	@Before
	public void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void testHealth() throws Exception {
		ResultActions actions = mvc.perform(MockMvcRequestBuilders.post(
				"/health").accept(MediaType.APPLICATION_JSON_UTF8));
		ContentResultMatchers c = content();
		actions.andExpect(status().isOk());
		String r = actions.andReturn().getResponse().getContentAsString();
		System.out.println(r);
	}

	@Test
	public void testRoutes() throws Exception {
		ResultActions actions = mvc.perform(MockMvcRequestBuilders.post(
				"/routes").accept(MediaType.APPLICATION_JSON_UTF8));
		ContentResultMatchers c = content();
		actions.andExpect(status().isOk());
		String r = actions.andReturn().getResponse().getContentAsString();
		System.out.println(r);
	}
}