package com.kjs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice

public class ItemExceptionHandler {

	@ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<String> handleStockException(InsufficientStockException ex) {
		
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
	
	@ExceptionHandler(OrderNotFoundException.class)
	public ResponseEntity<String> handeOrderException(OrderNotFoundException or)
	{
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(or.getMessage());
	}
}
