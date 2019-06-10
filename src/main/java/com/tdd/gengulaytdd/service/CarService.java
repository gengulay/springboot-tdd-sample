package com.tdd.gengulaytdd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.tdd.gengulaytdd.exceptionHandler.CarNotFoundException;
import com.tdd.gengulaytdd.model.Car;
import com.tdd.gengulaytdd.repository.CarRepository;

@Service
public class CarService {

	CarRepository carRepository;

	@Autowired
	public CarService(CarRepository carRepository) {
		this.carRepository = carRepository;
	}

	@Cacheable("cars")
	public Car getCarDetails(String model) throws CarNotFoundException {

		Car car = carRepository.findByModel(model);

		if (car == null) {
			throw new CarNotFoundException();
		}

		return car;
	}

	public Car saveCar(Car car) {
		return carRepository.save(car);
	}

	public List<Car> findAllCars() {

		return (List<Car>) carRepository.findAll();

	}

}
