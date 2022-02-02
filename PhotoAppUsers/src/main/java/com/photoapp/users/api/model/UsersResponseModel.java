package com.photoapp.users.api.model;

import lombok.Data;

import java.util.List;

@Data
public class UsersResponseModel {
	
	private String firstName;
	private String lastName;
	private String email;
	private String userId;
	private List<AlbumResponseModel> albums;
}
