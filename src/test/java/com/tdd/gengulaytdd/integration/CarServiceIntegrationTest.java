package com.tdd.gengulaytdd.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

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
	private CarRepository carRepository;

	@Autowired
	private TestRestTemplate restTemplate;

	@Before
	public void setUp() {
		carRepository.save(new Car("rio", "sedan"));
		carRepository.save(new Car("bugatti", "veyron"));
		carRepository.save(new Car("toyota", "vios"));

	}

	@Test
	public void getCar() {

		ResponseEntity<Car> response = restTemplate.getForEntity("/cars/veyron", Car.class);
		// assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().getMaker()).isEqualTo("bugatti");
		assertThat(response.getBody().getModel()).isEqualTo("veyron");
	}

	@Test
	public void postCar() {

		ResponseEntity<Car> response = restTemplate.postForEntity("/cars", new Car("mercedes", "amg"), Car.class);
		// assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().getMaker()).isEqualTo("mercedes");
		assertThat(response.getBody().getModel()).isEqualTo("amg");
	}

	@Test
	public void getCars() {

		ResponseEntity<List<Car>> response = restTemplate.exchange("/cars", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Car>>() {
				});

		assertNotNull(response.getBody());
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().get(0).getMaker()).isEqualTo("rio");
		assertThat(response.getBody().get(0).getModel()).isEqualTo("sedan");
		assertThat(response.getBody().get(1).getMaker()).isEqualTo("bugatti");
		assertThat(response.getBody().get(1).getModel()).isEqualTo("veyron");
		assertThat(response.getBody().get(2).getMaker()).isEqualTo("toyota");
		assertThat(response.getBody().get(2).getModel()).isEqualTo("vios");

	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
