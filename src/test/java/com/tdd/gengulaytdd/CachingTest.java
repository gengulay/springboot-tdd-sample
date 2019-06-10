package com.tdd.gengulaytdd;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.tdd.gengulaytdd.model.Car;
import com.tdd.gengulaytdd.repository.CarRepository;
import com.tdd.gengulaytdd.service.CarService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureTestDatabase
@AutoConfigureCache
public class CachingTest {

	@Autowired
	private CarService carService;

	@MockBean
	private CarRepository carRepository;

	@Test
	public void caching() throws Exception {
		given(carRepository.findByModel(anyString())).willReturn(new Car("prius", "hybrid"));

		carService.getCarDetails("hybrid");
		carService.getCarDetails("hybrid");

		verify(carRepository, times(2)).findByModel("hybrid");
	}

}
