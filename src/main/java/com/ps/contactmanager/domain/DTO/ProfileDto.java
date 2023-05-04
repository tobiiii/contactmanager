package com.ps.contactmanager.domain.DTO;

import com.ps.contactmanager.domain.Profile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto {


    @NotNull
    @NotBlank
    @NotEmpty
    private String code;

    @NotNull
    @NotBlank
    @NotEmpty
    private String name;

    @NotNull
    @Size(min = 1)
    private List<Long> privileges;

    public ProfileDto(Profile profile) {
        this.code = profile.getCode();
        this.name = profile.getName();
    }
}
