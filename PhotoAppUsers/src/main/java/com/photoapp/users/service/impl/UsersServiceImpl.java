package com.photoapp.users.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.photoapp.users.api.model.AlbumResponseModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.photoapp.users.api.dto.UsersDto;
import com.photoapp.users.entity.UserEntity;
import com.photoapp.users.repository.UsersRepository;
import com.photoapp.users.service.UsersService;
import org.springframework.web.client.RestTemplate;

@Service
public class UsersServiceImpl implements UsersService {

	UsersRepository usersRepository;
	BCryptPasswordEncoder bCryptPasswordEncoder;
	RestTemplate restTemplate;
	Environment environment;

	@Autowired
	public UsersServiceImpl(UsersRepository usersRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
							RestTemplate restTemplate, Environment environment) {
		this.usersRepository = usersRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.restTemplate = restTemplate;
		this.environment = environment;
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

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserEntity entity = usersRepository.findByEmail(username);

		if (entity == null) {
			throw new UsernameNotFoundException(username);
		}

		return new User(entity.getEmail(), entity.getEncryptedPassword(), true, true, true, true, new ArrayList<>());
	}

	@Override
	public UsersDto getUserDetailsByEmail(String email) {
		
		UserEntity entity = usersRepository.findByEmail(email);
		
		if (entity == null) {
			throw new UsernameNotFoundException(email);
		}
		
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		UsersDto userDetials = modelMapper.map(entity, UsersDto.class);
		
		return userDetials;
	}

	@Override
	public UsersDto getUserById(String userId) {
		UserEntity userEntity = usersRepository.findByUserId(userId);

		if (userEntity == null) {
			throw new UsernameNotFoundException("User not found");
		}

		UsersDto usersDto = new ModelMapper().map(userEntity, UsersDto.class);

		String albumUrl = String.format(environment.getProperty("albums.url.path"), userId);

		ResponseEntity<List<AlbumResponseModel>> albumsResponse = restTemplate.exchange(albumUrl, HttpMethod.GET
				, null, new ParameterizedTypeReference<List<AlbumResponseModel>>() {
				});

		usersDto.setAlbums(albumsResponse.getBody());
		return usersDto;
	}
}
