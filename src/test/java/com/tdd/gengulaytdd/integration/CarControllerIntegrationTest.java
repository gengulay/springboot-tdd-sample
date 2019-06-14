package com.tdd.gengulaytdd.integration;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.tdd.gengulaytdd.GengulayTddApplication;
import com.tdd.gengulaytdd.model.Car;
import com.tdd.gengulaytdd.repository.CarRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GengulayTddApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class CarControllerIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	@Autowired
	CarRepository carRepository;

	@Before
	public void setUp() {
		Car cra = carRepository.save(new Car("bugatti", "veyron"));
		Car car = carRepository.save(new Car("pagani", "huayra"));

	}

	@Test
	public void testGetAllCars() {
		ResponseEntity<List<Car>> rateResponse = restTemplate.exchange("/cars", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Car>>() {
				});
		List<Car> cars = rateResponse.getBody();

		assertEquals("veyron", cars.get(0).getModel());
		assertEquals("pagani", cars.get(1).getMaker());
		assertEquals("huayra", cars.get(1).getModel());

	}

	@Test
	public void testGetCarByModel() {
		Car car = restTemplate.getForObject("/car/huayr", Car.class);

		Assert.assertNotNull(car);

	}

	@Test
	public void testCreateCar() {
		Car car = new Car();
		car.setMaker("Toyota");
		car.setModel("prius");

		ResponseEntity<Car> postResponse = restTemplate.postForEntity("/cars", car, Car.class);
		Assert.assertNotNull(postResponse);
		Assert.assertNotNull(postResponse.getBody());
	}

	@Test
	public void testUpdateCar() {
		String model = "veyron";
		Car car = restTemplate.getForObject("/cars/" + model, Car.class);
		car.setMaker("Ettori Bugatti");
		restTemplate.put("/cars/" + model, car);

		Car updatedCar = restTemplate.getForObject("/cars/" + model, Car.class);
		assertEquals("Ettori Bugatti", updatedCar.getMaker());
	}

}
