package com.photoapp.users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {
	
	@Autowired
	private Environment env;
	
	@GetMapping (path = "/users/status")
	public String status(@RequestHeader HttpHeaders httpHeaders) {
		System.out.println("Headers : " + httpHeaders.toString() );
		
		return "Up & Running on port - " + env.getProperty("local.server.port");
	}
	
}
