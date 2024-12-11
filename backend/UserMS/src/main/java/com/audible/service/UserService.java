package com.audible.service;

import java.util.List;

import com.audible.dto.ProfileDTO;
import com.audible.dto.UserDTO;
import com.audible.dto.UserFileDTO;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
	
	String registerNewUser(UserDTO userDTO) throws Exception;
	UserDTO authenticateUser(String emailId, String password) throws Exception;
	UserDTO getProfile(String userId) throws Exception;
	UserFileDTO getProfilePhoto(String userId) throws Exception;
	List<String> getUserIds() throws Exception;
	String updateProfile(ProfileDTO profileDTO) throws Exception;
	String updateProfilePhoto(MultipartFile photo, String userId) throws Exception;
	String deleteProfilePhoto(String userId) throws Exception;
}
