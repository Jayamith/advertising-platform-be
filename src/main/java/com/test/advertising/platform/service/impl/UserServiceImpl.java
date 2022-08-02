package com.test.advertising.platform.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.*;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.test.advertising.platform.domain.User;
import com.test.advertising.platform.domain.UserPrincipal;
import com.test.advertising.platform.enumeration.Role;
import com.test.advertising.platform.exception.domain.EmailExistException;
import com.test.advertising.platform.exception.domain.UserNotFoundException;
import com.test.advertising.platform.exception.domain.UsernameExistException;
import com.test.advertising.platform.repository.UserRepository;
import com.test.advertising.platform.service.EmailService;
import com.test.advertising.platform.service.UserService;

import static com.test.advertising.platform.constant.UserImplConstant.*;
import static com.test.advertising.platform.enumeration.Role.*;
import static com.test.advertising.platform.constant.FileConstant.*;

@Service
@Transactional
@Qualifier("UserDetailsService")
public class UserServiceImpl implements UserService, UserDetailsService {

	private Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EmailService emailService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findUserByUsername(username);

		if (user == null) {
			LOGGER.error(NO_USER_FOUND_BY_USERNAME + username);
			throw new UsernameNotFoundException(NO_USER_FOUND_BY_USERNAME + username);
		} else {
			user.setLastLoginDateDisplay(user.getLastLoginDate());
			user.setLastLoginDate(new Date());
			userRepository.save(user);
			UserPrincipal userPrincipal = new UserPrincipal(user);
			LOGGER.info(FOUND_USER_BY_USERNAME + username);
			return userPrincipal;
		}
	}

	@Override
	public User register(String firstName, String lastName, String username, String password, String email)
			throws UserNotFoundException, UsernameExistException, EmailExistException, AddressException,
			MessagingException {
		validateUsernameAndEmail(StringUtils.EMPTY, username, email);
		User user = new User();
		user.setUserId(generateUserId());
		String encodedPassword = encodePassword(password);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setUsername(username);
		user.setEmail(email);
		user.setJoinDate(new Date());
		user.setPassword(encodedPassword);
		user.setActive(true);
		user.setNotLocked(true);
		user.setRole(ROLE_USER.name());
		user.setAuthorities(ROLE_USER.getAuthorities());
		user.setProfileImageUrl(getTemporaryProfileImageUrl(username));
		userRepository.save(user);
		// LOGGER.info("New user password: " + password);
		// emailService.sendNewPasswordEmail(firstName, password, email);
		return user;
	}

	private String encodePassword(String password) {
		return passwordEncoder.encode(password);
	}

//    private String generatePassword() {
//        return RandomStringUtils.randomAlphanumeric(10);
//    }

	private String generateUserId() {
		return RandomStringUtils.randomNumeric(10);
	}

	private String getTemporaryProfileImageUrl(String username) {
		return ServletUriComponentsBuilder.fromCurrentContextPath().path(DEFAULT_USER_IMAGE_PATH + username)
				.toUriString();
	}

	private User validateUsernameAndEmail(String currentUsername, String newUsername, String email)
			throws UserNotFoundException, UsernameExistException, EmailExistException {
		User userByUsername = findByUsername(newUsername);
		User userByNewEmail = findUserByEmail(email);
		if (StringUtils.isNotBlank(currentUsername)) {
			User currentUser = findByUsername(currentUsername);
			if (currentUser == null) {
				throw new UserNotFoundException("No user found by username: " + currentUsername);
			}
			if (userByUsername != null && !currentUser.getId().equals(userByUsername.getId())) {
				throw new UsernameExistException("Username already Exists");
			}
			if (userByNewEmail != null && !currentUser.getId().equals(userByNewEmail.getId())) {
				throw new EmailExistException("Email already Exists");
			}
			return currentUser;
		} else {
			if (userByUsername != null) {
				throw new UsernameExistException("Username already Exists");
			}
			if (userByNewEmail != null) {
				throw new EmailExistException("Email already Exists");
			}
			return null;
		}
	}

	@Override
	public List<User> getUsers() {
		return userRepository.findAll();
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findUserByUsername(username);
	}

	@Override
	public User findUserByEmail(String email) {
		return userRepository.findUserByEmail(email);
	}

	@Override
	public User addNewUser(String firstName, String lastName, String username, String password, String email,
			String role, boolean isNonLocked, boolean isActive, MultipartFile profileImage)
			throws UserNotFoundException, UsernameExistException, EmailExistException, IOException {

		validateUsernameAndEmail(StringUtils.EMPTY, username, email);
		User user = new User();
		String encodedPassword = encodePassword(password);
		user.setUserId(generateUserId());
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setUsername(username);
		user.setEmail(email);
		user.setJoinDate(new Date());
		user.setPassword(encodedPassword);
		user.setActive(isActive);
		user.setNotLocked(isNonLocked);
		user.setRole(getRoleEnumName(role).name());
		user.setAuthorities(getRoleEnumName(role).getAuthorities());
		user.setProfileImageUrl(getTemporaryProfileImageUrl(username));
		userRepository.save(user);
		saveProfileImage(user, profileImage);
		return user;
	}

	@Override
	public User updateUser(String currentUsername, String newFirstName, String newLastName, String newUsername,
			String newPassword, String newEmail, String role, boolean isNonLocked, boolean isActive,
			MultipartFile profileImage)
			throws UserNotFoundException, UsernameExistException, EmailExistException, IOException {

		User currentUser = validateUsernameAndEmail(currentUsername, newUsername, newEmail);
		currentUser.setFirstName(newFirstName);
		currentUser.setLastName(newLastName);
		currentUser.setUsername(newUsername);
		currentUser.setEmail(newEmail);
		currentUser.setActive(isActive);
		currentUser.setNotLocked(isNonLocked);
		currentUser.setRole(getRoleEnumName(role).name());
		currentUser.setAuthorities(getRoleEnumName(role).getAuthorities());
		userRepository.save(currentUser);
		saveProfileImage(currentUser, profileImage);
		return currentUser;

	}

	@Override
	public void deleteUser(long id) {

		userRepository.deleteById(id);
	}

	@Override
	public User updateProfileImage(String username, MultipartFile profileImage)
			throws UserNotFoundException, UsernameExistException, EmailExistException, IOException {
		User user = validateUsernameAndEmail(username, null, null);
		saveProfileImage(user, profileImage);
		return user;
	}

	private void saveProfileImage(User user, MultipartFile profileImage) throws IOException {
		if (profileImage != null) {
			Path userFolder = Paths.get(USER_FOLDER + user.getUsername()).toAbsolutePath().normalize();
			if (!Files.exists(userFolder)) {
				Files.createDirectories(userFolder);
				LOGGER.info(DIRECTORY_CREATED + userFolder);
			}
			Files.deleteIfExists(Paths.get(userFolder + user.getUsername() + DOT + JPG_EXTENSION));
			Files.copy(profileImage.getInputStream(), userFolder.resolve(user.getUsername() + DOT + JPG_EXTENSION),
					REPLACE_EXISTING);
			user.setProfileImageUrl(setProfileImageUrl(user.getUsername()));
			userRepository.save(user);
			LOGGER.info(FILE_SAVED_IN_FILE_SYSTEM + profileImage.getOriginalFilename());
		}
	}

	private String setProfileImageUrl(String username) {
		return ServletUriComponentsBuilder.fromCurrentContextPath()
				.path(USER_IMAGE_PATH + username + FORWARD_SLASH + username + DOT + JPG_EXTENSION).toUriString();

	}

	private Role getRoleEnumName(String role) {
		return Role.valueOf(role.toUpperCase());
	}

}
