package com.ps.contactmanager.domain.DTO;

import com.ps.contactmanager.validation.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordDto {

    @ValidPassword
    private String oldPassword;

    @ValidPassword
    private String newPassword;
}
