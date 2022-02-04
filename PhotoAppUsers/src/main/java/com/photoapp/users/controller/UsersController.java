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
import org.springframework.web.bind.annotation.*;

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

		return "Up & Running on port - " + env.getProperty("local.server.port") + ", " + env.getProperty("token.secret");
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

	@GetMapping(value = "/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<UsersResponseModel> getUser(@PathVariable("userId") String userId) {
		ModelMapper modelMapper = new ModelMapper();
		UsersDto usersDto = usersService.getUserById(userId);
		UsersResponseModel responseModel = modelMapper.map(usersDto, UsersResponseModel.class);

		return ResponseEntity.status(HttpStatus.OK).body(responseModel);
	}

	@GetMapping(value = "/email/{email}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<UsersResponseModel> getUserByEmail(@PathVariable("email") String email) {
		ModelMapper modelMapper = new ModelMapper();
		UsersDto usersDto = usersService.getUserByEmail(email);
		UsersResponseModel responseModel = modelMapper.map(usersDto, UsersResponseModel.class);

		return ResponseEntity.status(HttpStatus.OK).body(responseModel);
	}

}
