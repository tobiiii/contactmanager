package com.ps.contactmanager.domain.DTO;

import com.ps.contactmanager.validation.ValidEmail;
import com.ps.contactmanager.validation.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @ValidEmail
    private String email;

    @ValidPassword
    private String password;
}
