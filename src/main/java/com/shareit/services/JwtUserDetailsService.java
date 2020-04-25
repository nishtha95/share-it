package com.shareit.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shareit.dtos.UserDTO;
import com.shareit.exception.ShareItException;
import com.shareit.models.UserDAO;
import com.shareit.repositories.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDAO user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Account doesn't exist for this username");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getSaltedHashedPassword(),
				new ArrayList<>());
	}
	
	public UserDAO saveUser(UserDTO user) throws ShareItException {
		UserDAO newUser = null;
		newUser= userRepository.findByUsername(user.getUsername());
		if(newUser!=null){
			throw new ShareItException("Account already exists with this username");
		}
		newUser= userRepository.findByEmail(user.getEmail());
		if(newUser!=null){
			throw new ShareItException("Account already exists with this email id");
		}
		newUser=new UserDAO();
		newUser.setUsername(user.getUsername());
		newUser.setSaltedHashedPassword(bcryptEncoder.encode(user.getPassword()));
		newUser.setEmail(user.getEmail());
		return userRepository.save(newUser);
	}
	
	
}