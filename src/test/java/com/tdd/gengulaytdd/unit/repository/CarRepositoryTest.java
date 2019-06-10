package com.tdd.gengulaytdd.unit.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.tdd.gengulaytdd.model.Car;
import com.tdd.gengulaytdd.repository.CarRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CarRepositoryTest {

	@Autowired
	private CarRepository carRepository;

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void getCar_returnsCarDetails() throws Exception {

		Car savedCar = entityManager.persistFlushFind(new Car("prius", "hybrid"));
		Car car = carRepository.findByModel("hybrid");

		assertThat(car.getMaker()).isEqualTo(savedCar.getMaker());
		assertThat(car.getModel()).isEqualTo(savedCar.getModel());
	}

}
