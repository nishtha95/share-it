package com.shareit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.shareit.dtos.UserDTO;
import com.shareit.exception.ShareItException;
import com.shareit.security.config.JwtTokenUtil;
import com.shareit.services.JwtUserDetailsService;

import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin
@Slf4j
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;
	

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody UserDTO authenticationRequest) throws Exception {

		try {
			authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		} catch (ShareItException e) {
			
			return new ResponseEntity<>(e.getMessage(),HttpStatus.UNAUTHORIZED);
		}

		UserDetails userDetails;
		
		userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);
		
		log.info("Authentication token created");
		UserDTO authenticationResponse=new UserDTO();
		authenticationResponse.setUsername(authenticationRequest.getUsername());
		authenticationResponse.setToken(token);
		return ResponseEntity.ok(authenticationResponse);
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> saveUser(@RequestBody UserDTO user){
		try {
			return ResponseEntity.ok(userDetailsService.saveUser(user));
		} catch (ShareItException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.IM_USED);
		}
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			log.info("Authentication Successfull");
		} catch (DisabledException e) {
			log.error("User Disabled");
			throw new ShareItException("USER WITH THIS USERNAME IS DISABLED", e);
		} catch (BadCredentialsException e) {
			log.error("Invalid credentials entered by user");
			throw new ShareItException("INVALID CREDENTIALS", e);
		}
	}

}
