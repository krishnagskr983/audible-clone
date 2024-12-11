package com.audible.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private String userId;
    @NotNull(message="{user.name.notpresent}")
    @Pattern(regexp="[A-Z][a-z]+( [A-Z][a-z]+)?", message="{user.name.invalid}")
    private String fullName;
    @NotNull(message="{user.email.notpresent}")
    @Email(message="{user.email.invalid}")
    private String emailId;
    @NotNull(message="{user.contact.notpresent}")
    @Pattern(regexp="[6-9]\\d{9}", message="{user.contact.invalid}")
    private String contactNumber;
    @NotNull(message="{user.password.notpresent}")
    @Pattern(regexp="((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\\W).{6,20})", message="{user.password.invalid}")
    private String password;
    @NotNull(message="{user.gender.notpresent}")
    private String gender;
    @PastOrPresent(message="{user.dob.notpresent}")
    private LocalDate dateOfBirth;

}
