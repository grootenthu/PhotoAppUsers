package com.photoapp.users.api.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
@Data
public class UsersRequestModel {
	
	@NotNull (message = "FirstName should not be null")
	@Size (min=2, message = "FirstName must not be less than 2 characters")
	private String firstName;
	
	@NotNull (message = "LastName should not be null")
	@Size (min=2, message = "LastName must not be less than 2 characters")
	private String lastName;
	
	@NotNull (message = "Password should not be null")
	@Size (min=8, max=16, message = "Password must be greater than 8 characters and less than 16 characters")
	private String password;
	
	@NotNull (message = "Email cannot be null")
	@Email
	private String email;
	

}
