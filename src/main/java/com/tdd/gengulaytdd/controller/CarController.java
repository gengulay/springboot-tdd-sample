package com.tdd.gengulaytdd.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tdd.gengulaytdd.exceptionHandler.CarNotFoundException;
import com.tdd.gengulaytdd.model.Car;
import com.tdd.gengulaytdd.service.CarService;

@RestController
public class CarController {

	@Autowired
	private CarService carService;

	@GetMapping("/cars/{model}")
	private Car getCar(@PathVariable String model) throws CarNotFoundException {
		return carService.getCarDetails(model);
	}

	@GetMapping("/cars")
	public List<Car> getAllCars(Model model) {

		List<Car> cars = carService.findAllCars();

		model.addAttribute("cars", cars);

		return cars;

	}

	@PostMapping("/cars")
	public Car addCar(@Valid @RequestBody Car car) {

		return carService.saveCar(car);

	}

	@PutMapping("/cars/{model}")
	public ResponseEntity<Car> updateCar(@PathVariable String model, @Valid @RequestBody Car carDetails)
			throws CarNotFoundException {

		Car car = carService.getCarDetails(model);

		car.setMaker(carDetails.getMaker());
		car.setModel(carDetails.getModel());

		final Car updatedCar = carService.saveCar(car);
		return ResponseEntity.ok(updatedCar);
	}

	@GetMapping("/gen/{age}")
	private String getHome(@PathVariable int age) {
		return "hello world" + age;
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	private void carNotFoundException(CarNotFoundException e) {

	}

}
