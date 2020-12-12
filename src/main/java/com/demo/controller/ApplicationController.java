package com.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.modal.User;
import com.demo.services.UserService;

@RestController
public class ApplicationController {

	@Autowired
	UserService userService;

	@PostMapping("/register")
	public User registerUser(@Valid @RequestBody User user) {
		return userService.saveMyUser(user);
	}

	@GetMapping("/show-users")
	public List<User> showAllUsers() {
		return userService.showAllUsers();


	}

	@DeleteMapping("/delete-user/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable(value="id") int id) {
		userService.deleteMyUser(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/edit-user/{id}")////
	public ResponseEntity<User> editUser(@PathVariable(value="id") int id,@RequestBody User userDetails) {
		User user= userService.editUser(id);
		user.setId(userDetails.getId());
		user.setUsername(userDetails.getUsername());
		user.setFirstname(userDetails.getFirstname());
		user.setLastname(userDetails.getLastname());
		user.setAge(userDetails.getAge());
		userService.saveMyUser(user);
		return ResponseEntity.ok().body(user);

	}

	@GetMapping ("/login")
	public String loginUser(@RequestBody User user) {
		if(userService.findByUsernameAndPassword(user.getUsername(), user.getPassword())!=null) {
			return "successful";
		}
		else {
			return "{Invald username or password}";

		}
	}

}
