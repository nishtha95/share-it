package com.shareit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shareit.models.UserDAO;

@Repository
public interface UserRepository extends JpaRepository<UserDAO, Long> {

	UserDAO findByUsername(String username);
	UserDAO findByEmail(String email);
	
}
