package com.test.advertising.platform.service;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.web.multipart.MultipartFile;

import com.test.advertising.platform.domain.User;
import com.test.advertising.platform.exception.domain.EmailExistException;
import com.test.advertising.platform.exception.domain.UserNotFoundException;
import com.test.advertising.platform.exception.domain.UsernameExistException;

public interface UserService {

	User register(String firstName, String lastName, String username,String password, String email) throws UserNotFoundException, UsernameExistException, EmailExistException, AddressException, MessagingException;

	List<User> getUsers();

	User findByUsername(String username);

	User findUserByEmail(String email);
	
	User addNewUser(String firstName, String lastName, String username,String password, String email, String role, boolean isNonLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException;

	User updateUser(String currentUsername, String newFirstName, String newLastName, String newUsername,String newPassword, String newEmail, String role, boolean isNonLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException;
	
	void deleteUser(long id);
	
	User updateProfileImage(String username, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException;

}
