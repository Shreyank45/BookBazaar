package com.kjs.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kjs.Entity.Orders;
import com.kjs.Service.OrderService;
import com.kjs.dto.OrderRequest;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
@RestController
@AllArgsConstructor
@RequestMapping("/order")
public class OrderController {

	private OrderService service;
	
	@PostMapping("/post")
    public ResponseEntity<Orders> placeOrder(
            @RequestBody OrderRequest request) {

        Orders order = service.placeOrder(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(order);
    }
	
	@PutMapping("/cancel/{id}")
	public ResponseEntity<Orders> cancelOrders(@PathVariable Long id)
	{
		Orders or = service.cancelOrder(id);
		
			return ResponseEntity.status(HttpStatus.OK).body(or);
		
	}
	
	@GetMapping("/allorders")
	public ResponseEntity<List<Orders>> getAllOrders()
	{
		List<Orders> li = service.getAllOrders();
		
		return ResponseEntity.status(HttpStatus.OK).body(li);
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<Page<Orders>> getOrdersByUser( @PathVariable Long userId, @RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "5") int size) {
	   
		Page<Orders> li = service.getOrdersByUserId(userId, page, size);

	    return ResponseEntity.status(HttpStatus.OK).body(li);
	}
	
	 
}
