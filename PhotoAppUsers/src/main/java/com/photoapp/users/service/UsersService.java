package com.photoapp.users.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.photoapp.users.api.dto.UsersDto;

public interface UsersService extends UserDetailsService {
	
	UsersDto createUser(UsersDto userDetails);
	UsersDto getUserDetailsByEmail(String email);
	UsersDto getUserById(String userId);

}
