package com.photoapp.users.api.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class UsersDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1356373492697465397L;

	private String firstName;
	private String lastName;
	private String password;
	private String email;
	private String userId;
	private String encryptedPassword;

}
