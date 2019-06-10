package com.tdd.gengulaytdd.unit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.tdd.gengulaytdd.exceptionHandler.CarNotFoundException;
import com.tdd.gengulaytdd.model.Car;
import com.tdd.gengulaytdd.repository.CarRepository;
import com.tdd.gengulaytdd.service.CarService;

@RunWith(MockitoJUnitRunner.class)
public class CarServiceTest {

	@Mock
	private CarRepository carRepository;

	private CarService carService;

	@Before
	public void setUp() throws Exception {
		carService = new CarService(carRepository);

	}

	@Test
	public void getCar_shouldReturnCar() throws Exception {

		given(carRepository.findByModel("hybrid")).willReturn(new Car("prius", "hybrid"));

		Car car = carService.getCarDetails("hybrid");

		assertThat(car.getMaker()).isEqualTo("prius");
		assertThat(car.getModel()).isEqualTo("hybrid");

	}

	@Test(expected = CarNotFoundException.class)
	public void getCar_notFound() throws Exception {
		given(carRepository.findByModel("prius")).willReturn(null);

		carService.getCarDetails("prius");

	}

}
