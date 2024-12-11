package com.audible.entity;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection="User")
public class User {

	@Id
	private String userId;
	private String fullName;
	private String emailId;
	private String contactNumber;
	private String password;
	private String gender;
	private LocalDate dateOfBirth;
	private UserFile userPhoto;

}
