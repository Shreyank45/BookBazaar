package com.kjs.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kjs.Entity.User;
import com.kjs.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
	
	private UserRepository repo;

	public List<User> getAll() {
		
		return repo.findAll();
	}

	public User insert(User user) {
		
		return repo.save(user);
	}
	
	

}
