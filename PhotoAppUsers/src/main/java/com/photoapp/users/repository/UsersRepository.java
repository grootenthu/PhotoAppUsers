package com.photoapp.users.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.photoapp.users.entity.UserEntity;

@Repository
public interface UsersRepository extends CrudRepository<UserEntity, Long> {
	
	UserEntity findByEmail(String email);
	UserEntity findByUserId(String userId);
	
}
