package com.reed.micro.eureka.server;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * 注意静态导入：
 * import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
 * 否则status()等方法无法识别
 * @author reed
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EurekaServerApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
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
		ResultActions actions = mvc
				.perform(
						MockMvcRequestBuilders.post("/health").accept(
								MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("UP"));
		String r = actions.andReturn().getResponse().getContentAsString();
		System.out.println(r);
	}
}