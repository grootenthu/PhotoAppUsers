package com.photoapp.users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {
	
	@Autowired
	private Environment env;
	
	@GetMapping (path = "/users/status")
	public String status() {
		return "Up & Running on port - " + env.getProperty("local.server.port");
	}
	
}
