package com.test.advertising.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.advertising.platform.domain.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findUserByUsername(String username);
	
	User findUserByEmail(String email);

}
