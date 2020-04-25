package com.shareit.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="users")
public class UserDAO extends AuditableDAO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotBlank
	@Getter
	@Setter
	private String username;

	@Email @NotBlank
	@Column(unique = true)
	@Getter
	@Setter
	private String email;
	
	@NotBlank
	@Getter
	@Setter
	private String saltedHashedPassword;
	
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="user")
	@Getter
	@Setter
	@JsonIgnore
	private List<FileDAO> files;
}
