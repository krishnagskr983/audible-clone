package com.audible.api;

import java.time.LocalDate;
import java.util.List;

import com.audible.dto.ProfileDTO;
import com.audible.dto.UserFileDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.audible.dto.UserDTO;
import com.audible.service.UserService;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@Validated
@RequestMapping(value="/user-api")
public class UserAPI {

	@Autowired
	private UserService userService;

	@Autowired
	private Environment environment;
	
	static Logger logger=LoggerFactory.getLogger(UserAPI.class);
	
	@PostMapping(value="/user/register")
	public ResponseEntity<String> registerNewUser(@RequestBody UserDTO userDTO) throws Exception{
		String msg = userService.registerNewUser(userDTO);
		String successMsg = environment.getProperty("UserAPI.USER_REGISTRATION_SUCCESS")+msg;
		return new ResponseEntity<String>(successMsg, HttpStatus.OK);
	}

	@PostMapping(value="/user/login")
	public ResponseEntity<UserDTO> authenticateUser(@RequestBody UserDTO userDTO) throws Exception{
		return new ResponseEntity<>(userService.authenticateUser(userDTO.getEmailId(), userDTO.getPassword()), HttpStatus.OK);
	}

	@GetMapping(value="/user/get-profile/{userId}")
	public ResponseEntity<UserDTO> getProfile(@PathVariable String userId) throws Exception{
		UserDTO userDTO=userService.getProfile(userId);
		return new ResponseEntity<>(userDTO, HttpStatus.OK);
	}

	@GetMapping(value="/user/get-profile-photo/{userId}")
	public ResponseEntity<UserFileDTO> getProfilePhoto(@PathVariable String userId) throws Exception{
		UserFileDTO userFileDTO = userService.getProfilePhoto(userId);
		return new ResponseEntity<>(userFileDTO, HttpStatus.OK);
	}

	@GetMapping(value="/users/userIds")
	public ResponseEntity<List<String>> getUserIds() throws Exception{
		return new ResponseEntity<>(userService.getUserIds(), HttpStatus.OK);
	}

	@GetMapping(value="/user/name/{name}")
	public ResponseEntity<String> getUser(@PathVariable String name) throws Exception{
		return new ResponseEntity<>("Hello "+name, HttpStatus.OK);
	}

	@PutMapping(value="/user/update-profile")
	public ResponseEntity<String> updateProfile(@RequestBody ProfileDTO profileDTO) throws Exception{
		String mssg = userService.updateProfile(profileDTO);
		String successMsg = environment.getProperty("UserAPI.UPDATED_PROFILE")+mssg;
		return new ResponseEntity<String>(successMsg, HttpStatus.OK);
	}


	@PutMapping(value="/user/update-profile-photo")
	public ResponseEntity<String> updateProfilePhoto(@RequestParam("userPhoto") MultipartFile photo,
													 @RequestParam("userId") String userId) throws Exception{
		String mssg = userService.updateProfilePhoto(photo, userId);
		String successMsg = environment.getProperty("UserAPI.UPDATED_PROFILE_PHOTO")+mssg;
		return new ResponseEntity<String>(successMsg, HttpStatus.OK);
	}

	@DeleteMapping(value="/user/delete-profile-photo/{userId}")
	public ResponseEntity<String> deleteProfilePhoto(@PathVariable String userId) throws Exception{
		String mssg = userService.deleteProfilePhoto(userId);
		String successMsg = environment.getProperty("UserAPI.DELETED_PROFILE_PHOTO")+mssg;
		return new ResponseEntity<String>(successMsg, HttpStatus.OK);
	}

}
