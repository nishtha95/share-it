package com.shareit.dtos;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class FileDTO {

	@Getter
	@Setter
	private Long id;
	
	@Getter
	@Setter
	private String title;
	
	@Getter
	@Setter
	private String description;
	
	@Getter
	@Setter
	private Date createdAt;
}
