package com.kjs.dto;

import lombok.Data;

@Data
public class OrderItemRequest {
	
	private Long bookId;
    private Integer quantity;

}
