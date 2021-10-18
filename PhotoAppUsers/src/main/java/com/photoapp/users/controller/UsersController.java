package com.photoapp.users.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {
	
	@GetMapping (path = "/users/status")
	public String status() {
		return "Up & Running";
	}
	
}
