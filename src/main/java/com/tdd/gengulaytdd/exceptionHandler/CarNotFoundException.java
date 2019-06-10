package com.tdd.gengulaytdd.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CarNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5337376666931964934L;

	public CarNotFoundException() {

	}

	public CarNotFoundException(String message) {
		super(message);
	}

}
