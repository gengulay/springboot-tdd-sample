package com.tdd.gengulaytdd.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tdd.gengulaytdd.model.Car;
import com.tdd.gengulaytdd.repository.CarRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CarServiceIntegrationTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	CarRepository carRepository;

	@Autowired
	private TestRestTemplate restTemplate;

	@Before
	public void setUp() {
		this.carRepository.save(new Car("rio", "sedan"));

	}

	@Test
	public void createCar_shouldReturnCar() throws Exception {

		mvc.perform(MockMvcRequestBuilders.post("/cars").content(asJsonString(new Car("rio", "sedan")))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.maker").value("rio"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.model").value("sedan"));

	}

	@Test
	public void getCar_shouldFindCar() throws Exception {

		mvc.perform(MockMvcRequestBuilders.get("/cars/{model}", "sedan").accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.maker").value("rio"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.model").value("sedan"));
	}

	@Test
	public void getCar() {

		ResponseEntity<Car> response = restTemplate.getForEntity("/cars/sedan", Car.class);
		// assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().getMaker()).isEqualTo("rio");
		assertThat(response.getBody().getModel()).isEqualTo("sedan");
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
