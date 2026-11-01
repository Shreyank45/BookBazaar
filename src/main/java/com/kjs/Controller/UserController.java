package com.kjs.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kjs.Entity.User;
import com.kjs.Service.UserService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
	
	private UserService service;
	
	
	@GetMapping("/getall")
	public ResponseEntity<List<User>> getAll()
	{
		List<User> li = service.getAll();
		if(!li.isEmpty())
		return ResponseEntity.status(HttpStatus.OK).body(li);
		else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@PostMapping("/insert")
	public ResponseEntity<User> insert(@RequestBody User user)
	{
		User us = service.insert(user);
		return ResponseEntity.status(HttpStatus.OK).body(us);
	}
	 

}
