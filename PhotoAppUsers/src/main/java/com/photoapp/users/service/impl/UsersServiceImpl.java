package com.photoapp.users.service.impl;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.photoapp.users.api.dto.UsersDto;
import com.photoapp.users.entity.UserEntity;
import com.photoapp.users.repository.UsersRepository;
import com.photoapp.users.service.UsersService;

@Service
public class UsersServiceImpl implements UsersService {
	
	UsersRepository usersRepository;

	@Autowired
	public UsersServiceImpl(UsersRepository usersRepository) {
		this.usersRepository = usersRepository;
	}
	
	@Override
	public UsersDto createUser(UsersDto userDetails) {
		userDetails.setUserId(UUID.randomUUID().toString());
		
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);
		userEntity.setEncryptedPassword("test");
		
		usersRepository.save(userEntity);
		
		return userDetails;
	}
}
