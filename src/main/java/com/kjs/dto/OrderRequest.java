package com.kjs.dto;

import java.util.List;

import lombok.Data;

@Data
public class OrderRequest {

    private Long userId;
    private List<OrderItemRequest> items;
}
