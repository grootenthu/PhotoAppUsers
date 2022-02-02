package com.photoapp.users.api.dto;

import java.io.Serializable;
import java.util.List;

import com.photoapp.users.api.model.AlbumResponseModel;
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
	private List<AlbumResponseModel> albums;

}
