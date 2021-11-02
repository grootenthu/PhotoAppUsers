package com.photoapp.users.service.impl;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.photoapp.users.api.dto.UsersDto;
import com.photoapp.users.entity.UserEntity;
import com.photoapp.users.repository.UsersRepository;
import com.photoapp.users.service.UsersService;

@Service
public class UsersServiceImpl implements UsersService {

	UsersRepository usersRepository;
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public UsersServiceImpl(UsersRepository usersRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.usersRepository = usersRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	public UsersDto createUser(UsersDto userDetails) {
		userDetails.setUserId(UUID.randomUUID().toString());
		userDetails.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
		
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);

		UserEntity createdUser = usersRepository.save(userEntity);
		UsersDto createdUserResponse = modelMapper.map(createdUser, UsersDto.class);

		return createdUserResponse;
	}
}
