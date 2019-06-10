package com.tdd.gengulaytdd.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Car {

	@Id
	@GeneratedValue
	private Long id;
	private String maker;
	private String model;

	public Car() {

	}

	public Car(String maker, String model) {
		this.maker = maker;
		this.model = model;
	}

	public String getMaker() {
		return maker;
	}

	public void setMaker(String maker) {
		this.maker = maker;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Long getId() {
		return id;
	}

}
