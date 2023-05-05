package com.ps.contactmanager.domain.DTO;

import com.ps.contactmanager.domain.view.UserView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collection;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class AuthenticationResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    private String token;
    private String refreshToken;
    private String redirectTo;
    private Long sessionDuration;
    private String appBuildInfo;
    private UserView user;
    private Collection<String> privileges;

    public AuthenticationResponse(String token, String refreshToken) {
        super();
        this.token = token;
        this.refreshToken = refreshToken;
    }
}
