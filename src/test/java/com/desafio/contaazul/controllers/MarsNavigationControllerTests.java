package com.desafio.contaazul.controllers;

import java.io.IOException;
import java.util.Arrays;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.desafio.contaazul.entities.Direction;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@SuppressWarnings({ "rawtypes", "unchecked" })
public class MarsNavigationControllerTests {

	private MockMvc mockMvc;

	private HttpMessageConverter mappingJackson2HttpMessageConverter;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	void setConverters(HttpMessageConverter<?>[] converters) {
		this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
				.filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().orElse(null);

		Assert.assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
	}

	@Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void testNavigate() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/rest/mars/MMRMMRMM").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("R2-D2")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.positionX", Matchers.is(2)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.positionY", Matchers.is(0)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.direction", Matchers.is(Direction.SOUTH.name())));
	}
	
	@Test
	public void failOnTestNavigateWithInvalidDirection() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/rest/mars/AAA?direction=Z").contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	protected String json(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}

}
