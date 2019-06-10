package com.tdd.gengulaytdd.repository;

import org.springframework.data.repository.CrudRepository;

import com.tdd.gengulaytdd.model.Car;

public interface CarRepository extends CrudRepository<Car, Long> {

	Car findByMaker(String string);

	Car findByModel(String string);

}
