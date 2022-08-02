package com.test.advertising.platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.springframework.http.MediaType.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.test.advertising.platform.domain.HttpResponse;
import com.test.advertising.platform.domain.User;
import com.test.advertising.platform.domain.UserPrincipal;
import com.test.advertising.platform.exception.domain.EmailExistException;
import com.test.advertising.platform.exception.domain.ExceptionHandling;
import com.test.advertising.platform.exception.domain.UserNotFoundException;
import com.test.advertising.platform.exception.domain.UsernameExistException;
import com.test.advertising.platform.service.UserService;
import com.test.advertising.platform.utility.JWTTokenProvider;

import static com.test.advertising.platform.constant.SecurityConstant.*;
import static com.test.advertising.platform.constant.FileConstant.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

@RestController
@RequestMapping(path = { "/", "/user" })
public class UserController extends ExceptionHandling {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JWTTokenProvider jwtTokenProvider;

	@Autowired
	private UserService userService;

	@Autowired
	public UserController(AuthenticationManager authenticationManager, JWTTokenProvider jwtTokenProvider,
			UserService userService) {
		super();
		this.authenticationManager = authenticationManager;
		this.jwtTokenProvider = jwtTokenProvider;
		this.userService = userService;
	}

	@PostMapping("/login")
	public ResponseEntity<User> login(@RequestBody User user) {
		authenticate(user.getUsername(), user.getPassword());
		User loginUser = userService.findByUsername(user.getUsername());
		UserPrincipal userPrincipal = new UserPrincipal(loginUser);
		HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
		return new ResponseEntity<>(loginUser, jwtHeader, HttpStatus.OK);
	}

	@PostMapping("/register")
	public ResponseEntity<User> register(@RequestBody User user) throws UserNotFoundException, UsernameExistException,
			EmailExistException, AddressException, MessagingException {
		User newUser = userService.register(user.getFirstName(), user.getLastName(), user.getUsername(),
				user.getPassword(), user.getEmail());
		return new ResponseEntity<>(newUser, HttpStatus.OK);
	}

	@PostMapping("/add")
	public ResponseEntity<User> addNewUser(@RequestParam("firstName") String firstName,
										   @RequestParam("lastName") String lastName, 
										   @RequestParam("username") String username,
										   @RequestParam("email") String email,
										   @RequestParam("password") String password,
										   @RequestParam("role") String role,
										   @RequestParam("isActive") String isActive,
										   @RequestParam("isNotLocked") String isNotLocked,
										   @RequestParam(value = "profileImage", required = false) MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException {
		User newUser = userService.addNewUser(firstName, lastName, username, password, email, role, Boolean.parseBoolean(isActive), Boolean.parseBoolean(isNotLocked), profileImage);
		return new ResponseEntity<>(newUser, HttpStatus.OK);

	}
	
	@PutMapping("/update")
	public ResponseEntity<User> update(@RequestParam("currentUsername") String currentUsername,
										   @RequestParam("firstName") String firstName,
										   @RequestParam("lastName") String lastName, 
										   @RequestParam("username") String username,
										   @RequestParam("email") String email,
										   @RequestParam("password") String password,
										   @RequestParam("role") String role,
										   @RequestParam("isActive") String isActive,
										   @RequestParam("isNotLocked") String isNotLocked,
										   @RequestParam(value = "profileImage", required = false) MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException {
		User updatedUser = userService.updateUser(currentUsername,firstName, lastName, username, password, email, role, Boolean.parseBoolean(isActive), Boolean.parseBoolean(isNotLocked), profileImage);
		return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	}
	
	@GetMapping("/find/{username}")
	public ResponseEntity<User> getUser(@PathVariable("username") String username){
		User user = userService.findByUsername(username);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@GetMapping("/list")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = userService.getUsers();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<HttpResponse> deleteUser(@PathVariable("id") long id){
		userService.deleteUser(id);
		return response(HttpStatus.OK, "User Deleted Successfully!");
	}
	
	@PutMapping("/updateProfileImage")
	public ResponseEntity<User> updateProfileImage(@RequestParam("username") String username,
										   @RequestParam(value = "profileImage") MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException {
		
		User user = userService.updateProfileImage(username, profileImage);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@GetMapping(path = "/image/{username}/{fileName}", produces = IMAGE_JPEG_VALUE)
	public byte[] getProfileImage(@PathVariable("username") String username, @PathVariable("fileName") String fileName) throws IOException {
		
		return Files.readAllBytes(Paths.get(USER_FOLDER+username+FORWARD_SLASH+fileName));
	}
	
	@GetMapping(path = "/image/profile/{username}", produces = IMAGE_JPEG_VALUE)
	public byte[] getTempProfileImage(@PathVariable("username") String username) throws IOException {
		URL url = new URL(TEMP_PROFILE_IMAGE_BASE_URL + username);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		try(InputStream inputStream = url.openStream()){
			int bytesRead;
			byte[] chunk = new byte[1024];
			while((bytesRead = inputStream.read(chunk))>0) {
				byteArrayOutputStream.write(chunk,0,bytesRead);
			}
		}
		return byteArrayOutputStream.toByteArray();
	}
	
	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus,String message){
		return new ResponseEntity<>(new HttpResponse(httpStatus.value(),httpStatus,httpStatus.getReasonPhrase(),
				message), httpStatus);	
	}
	
	private HttpHeaders getJwtHeader(UserPrincipal userPrincipal) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(JWT_TOKEN_HEADER, jwtTokenProvider.generateJwtToken(userPrincipal));
		return headers;
	}

	private void authenticate(String username, String password) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	}
}
