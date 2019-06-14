package com.tdd.gengulaytdd.unit.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.tdd.gengulaytdd.controller.CarController;
import com.tdd.gengulaytdd.exceptionHandler.CarNotFoundException;
import com.tdd.gengulaytdd.model.Car;
import com.tdd.gengulaytdd.service.CarService;

@RunWith(SpringRunner.class)
@WebMvcTest(CarController.class)
public class CarControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CarService carService;

	@Test
	public void getCar_shouldReturnCar() throws Exception {
		given(carService.getCarDetails(anyString())).willReturn(new Car("prius", "hybrid"));

		mockMvc.perform(MockMvcRequestBuilders.get("/cars/hybrid")).andExpect(status().isOk())
				.andExpect(jsonPath("maker").value("prius")).andExpect(jsonPath("model").value("hybrid"));

	}

	@Test
	public void getCars_shouldGetAllCars() throws Exception {
		List<Car> cars = new ArrayList<Car>();
		Car c = new Car("Toyota", "gt86");
		cars.add(c);
		when(carService.findAllCars()).thenReturn(cars);

		mockMvc.perform(get("/cars")).andExpect(status().isOk());

	}

	@Test
	public void getCar_notFound() throws Exception {
		given(carService.getCarDetails(anyString())).willThrow(new CarNotFoundException());

		mockMvc.perform(MockMvcRequestBuilders.get("/cars/prius")).andExpect(status().isNotFound());

	}

//	@Test
//	public void saveCar_shouldReturnCar() throws Exception {
//		doAnswer(invocation -> {
//			Car c = (Car) invocation.getArguments()[0];
//			c.setMaker("toyota");
//			return null;
//		}).when(carService).saveCar((Car) any(Car.class));
//
//		mockMvc.perform(post("/cars").param("maker", "toyota").param("model", "gt86")).andExpect(status().isOk());
//
//	}

}
