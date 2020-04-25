package com.shareit.dtos;

import lombok.Getter;
import lombok.Setter;

public class UserDTO {

	@Getter
	@Setter
	private String email;

	@Getter
	@Setter
	private String username;
	
	@Getter
	@Setter
	private String password;
	
	@Getter
	@Setter
	private String token;
	
}
