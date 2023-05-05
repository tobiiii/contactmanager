package com.ps.contactmanager.domain.DTO;

import com.ps.contactmanager.validation.ValidEmail;
import com.ps.contactmanager.validation.ValidName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @ValidName
    private String firstName;

    @ValidName
    private String lastName;

    @ValidEmail
    private String emailAddress;

    @NotNull(message = "Profil {REQUIRED}")
    private Long profile;


}
