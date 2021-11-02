package com.photoapp.users.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.photoapp.users.api.dto.UsersDto;
import com.photoapp.users.api.model.UsersRequestModel;
import com.photoapp.users.api.model.UsersResponseModel;
import com.photoapp.users.service.UsersService;

@RestController
@RequestMapping("/users")
public class UsersController {

	@Autowired
	private Environment env;

	@Autowired
	private UsersService usersService;

	@GetMapping(path = "/status")
	public String status(@RequestHeader HttpHeaders httpHeaders) {
		System.out.println("Headers : " + httpHeaders.toString());

		return "Up & Running on port - " + env.getProperty("local.server.port");
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Object> createUser(@Valid @RequestBody UsersRequestModel userDetails) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UsersDto usersDto = modelMapper.map(userDetails, UsersDto.class);
		UsersDto createdUser = usersService.createUser(usersDto);

		UsersResponseModel responseModel = modelMapper.map(createdUser, UsersResponseModel.class);

		return ResponseEntity.status(HttpStatus.CREATED).body(responseModel);
	}

}
