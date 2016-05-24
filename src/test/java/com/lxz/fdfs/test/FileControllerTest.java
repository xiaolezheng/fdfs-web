package com.lxz.fdfs.test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.lxz.fdfs.api.FileController;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;



@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MockServletContext.class)
@WebAppConfiguration
@Slf4j
public class FileControllerTest {

	private MockMvc mvc;

	@Before
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.standaloneSetup(new FileController()).build();
	}

	@Test
	public void getHello() throws Exception {
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/fs/hello").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();

		log.info("result: {}", result.getResponse().getContentAsString());
	}
}
