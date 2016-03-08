package com.reed.cloud.micro.rest.client.test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

import com.reed.cloud.micro.rest.ClientApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ClientApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class AppTest {
	private MockMvc mvc;

	@Before
	public void setUp() {
		mvc = MockMvcBuilders.standaloneSetup(new ClientApplication()).build();
	}


	@Test
	public void testRest() throws Exception {
		ResultActions actions = mvc.perform(MockMvcRequestBuilders.get("/")
				.accept(MediaType.APPLICATION_JSON_UTF8));
		ContentResultMatchers c = content();
		actions.andExpect(status().isOk());
		// actions.andExpect(c.string(equalTo("UP")));
	}
}
