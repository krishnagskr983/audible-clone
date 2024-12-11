package com.audible.service;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.audible.dto.ProfileDTO;
import com.audible.dto.UserFileDTO;
import com.audible.entity.UserFile;
import com.audible.repository.UserFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.audible.dto.UserDTO;
import com.audible.entity.User;
import com.audible.exception.AudibleException;
import com.audible.repository.UserRepository;
import com.audible.utility.HashingUtility;
import org.springframework.web.multipart.MultipartFile;

@Service(value="userService")
@Transactional
public class UserServiceImpl implements UserService{
	
	@Autowired 
	private UserRepository userRepository;

	@Autowired
	private UserFileRepository userFileRepository;

	private static final String NO_PHOTO = "no_photo.jpg";
	private static final String CONTENT_TYPE = "image/jpeg";
	private static final String PHOTO_PATH = "../../assets/User_Photos/";
	private static final String PHOTO_FILE_PATH = "C:/MyProjects/Audible_Project/Audible_Frontend/src/assets" +
			"/User_Photos/";
	
	@Override
	public String registerNewUser(UserDTO userDTO) throws Exception{
		Optional<User> userRegistered = userRepository.findByEmailId(userDTO.getEmailId());
		if(userRegistered.isPresent())
			throw new AudibleException("UserService.USER_ALREADY_EXISTS");
		User user=convertToUser(userDTO);
		userRepository.save(user);
		return user.getUserId();
	}
	
	@Override
	public UserDTO authenticateUser(String emailId, String password) throws Exception{
		Optional<User> optionalUser = userRepository.findByEmailId(emailId);
		User user=optionalUser.orElseThrow(()-> new AudibleException("UserService.INVALID_CREDENTIALS"));
		UserDTO userDTO = convertToUserDTO(user);
		
		if(!HashingUtility.getHashValue(password).equals(userDTO.getPassword()))
			throw new AudibleException("UserService.INVALID_CREDENTIALS");
		return userDTO;
	}

	@Override
	public UserDTO getProfile(String userId) throws AudibleException, NoSuchAlgorithmException{
		Optional<User> optionalUser = userRepository.findById(userId);
		if(optionalUser.isEmpty()) throw new AudibleException("UserService.USER_NOT_FOUND");
		return convertToUserDTO(optionalUser.get());
	}

	@Override
	public UserFileDTO getProfilePhoto(String userId) throws Exception {
		Optional<User> optionalUser = userRepository.findById(userId);
		if(optionalUser.isEmpty()) throw new AudibleException("UserService.USER_NOT_FOUND");
		User user = optionalUser.get();
		UserFileDTO userFileDTO = new UserFileDTO();
		userFileDTO.setUserFileId(user.getUserPhoto().getUserFileId());
		userFileDTO.setName(user.getUserPhoto().getName());
		userFileDTO.setType(user.getUserPhoto().getType());
		userFileDTO.setFilePath(user.getUserPhoto().getFilePath());
		return userFileDTO;
	}

	@Override
	public List<String> getUserIds() throws AudibleException{
		List<String> userIdList = new ArrayList<>();
		List<User> usersList = userRepository.findAll();
		for(User user: usersList) {
			userIdList.add(user.getUserId());
		}
		return userIdList;
	}

	@Override
	public String updateProfile(ProfileDTO profileDTO) throws AudibleException, NoSuchAlgorithmException, IOException {
		Optional<User> optionalUser = userRepository.findById(profileDTO.getUserId());
		User user = optionalUser.orElseThrow(()-> new AudibleException("UserService.USER_NOT_FOUND"));
		convertProfileToUser(profileDTO, user);
		userRepository.save(user);
		return user.getUserId();
	}

	@Override
	public String updateProfilePhoto(MultipartFile photo, String userId) throws Exception {
		Optional<User> optionalUser = userRepository.findById(userId);
		User user = optionalUser.orElseThrow(()-> new AudibleException("UserService.USER_NOT_FOUND"));
		UserFile userFile = user.getUserPhoto();
		String photoFilePath = PHOTO_PATH + photo.getOriginalFilename();
		userFile.setName(photo.getOriginalFilename());
		userFile.setFilePath(photoFilePath);
		userFileRepository.save(userFile);

		photo.transferTo(new File(PHOTO_FILE_PATH+photo.getOriginalFilename()));
		user.setUserPhoto(userFile);
		userRepository.save(user);
		return user.getUserId();
	}

	@Override
	public String deleteProfilePhoto(String userId) throws Exception {
		Optional<User> optionalUser = userRepository.findById(userId);
		User user = optionalUser.orElseThrow(()-> new AudibleException("UserService.USER_NOT_FOUND"));
		UserFile userFile = user.getUserPhoto();
		String photoFilePath = PHOTO_PATH + NO_PHOTO;
		userFile.setName(NO_PHOTO);
		userFile.setFilePath(photoFilePath);
		userFileRepository.save(userFile);

		user.setUserPhoto(userFile);
		userRepository.save(user);
		return user.getUserId();
	}
	
	public User convertToUser(UserDTO userDTO) throws NoSuchAlgorithmException, IOException {
		User user = new User();
		user.setFullName(userDTO.getFullName());
		user.setEmailId(userDTO.getEmailId());
		user.setContactNumber(userDTO.getContactNumber());
		user.setPassword(HashingUtility.getHashValue(userDTO.getPassword()));
		user.setGender(userDTO.getGender());
		user.setDateOfBirth(userDTO.getDateOfBirth());

		String photoFilePath = PHOTO_PATH + NO_PHOTO;
		UserFile userFile = userFileRepository.save(UserFile.builder()
				.name(NO_PHOTO)
				.type(CONTENT_TYPE)
				.filePath(photoFilePath).build());
		user.setUserPhoto(userFile);
		userRepository.save(user);
		return user;
	}
	
	public static UserDTO convertToUserDTO(User user) throws NoSuchAlgorithmException {
		UserDTO userDTO = new UserDTO();
		userDTO.setUserId(user.getUserId());
		userDTO.setFullName(user.getFullName());
		userDTO.setEmailId(user.getEmailId());
		userDTO.setContactNumber(user.getContactNumber());
		userDTO.setPassword(user.getPassword());
		userDTO.setGender(user.getGender());
		userDTO.setDateOfBirth(user.getDateOfBirth());

		UserFileDTO userFileDTO = new UserFileDTO();
		userFileDTO.setUserFileId(user.getUserPhoto().getUserFileId());
		userFileDTO.setName(user.getUserPhoto().getName());
		userFileDTO.setType(user.getUserPhoto().getType());
		userFileDTO.setFilePath(user.getUserPhoto().getFilePath());
		userDTO.setUserPhotoDTO(userFileDTO);

		return userDTO;
	}

	public void convertProfileToUser(ProfileDTO profileDTO, User user) {
		user.setFullName(profileDTO.getFullName());
		user.setEmailId(profileDTO.getEmailId());
		user.setContactNumber(profileDTO.getContactNumber());
		user.setGender(profileDTO.getGender());
		user.setDateOfBirth(profileDTO.getDateOfBirth());
	}
	
}
