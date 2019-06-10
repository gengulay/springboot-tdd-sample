package com.tdd.gengulaytdd.unit.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.tdd.gengulaytdd.controller.HomeController;

public class HomeControllerTest {

	private MockMvc mockMvc;
	private HomeController controller;

	@Before
	public void setup() {
		controller = new HomeController();
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void home_shouldRenderHomeView() throws Exception {
		mockMvc.perform(get("/")).andExpect(view().name("home"));
	}
}
