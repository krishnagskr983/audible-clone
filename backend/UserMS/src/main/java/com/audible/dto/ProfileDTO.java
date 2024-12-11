package com.audible.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProfileDTO {
    private String userId;
    @NotNull(message="{profile.name.notpresent}")
    @Pattern(regexp="[A-Z][a-z]+( [A-Z][a-z]+)?", message="{profile.name.invalid}")
    private String fullName;
    @NotNull(message="{profile.email.notpresent}")
    @Email(message="{profile.email.invalid}")
    private String emailId;
    @NotNull(message="{profile.contact.notpresent}")
    @Pattern(regexp="[6-9]\\d{9}", message="{profile.contact.invalid}")
    private String contactNumber;
    @NotNull(message="{profile.gender.notpresent}")
    private String gender;
    @PastOrPresent(message="{profile.dob.notpresent}")
    private LocalDate dateOfBirth;
}
