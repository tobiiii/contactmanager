package com.ps.contactmanager.utils;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ParamsProvider {

    @Value("${TOKEN_DURATION}")
    private Long tokenDuration;

    @Value("${DURATION_REINIT_PASSWORD}")
    private Long durationReinitPassword;

    @Value("${REFRESH_TOKEN_DURATION}")
    private Long refreshTokenDuration;

    @Value("${GENERATED_PASSWORD_LENGTH}")
    private Integer generatedPasswordLength;

    @Value("${PASSWORD_ALLOW_DURATION_UNLOCK}")
    private Long vendorPasswordAllowDurationUnlock;

    @Value("${PASSWORD_ALLOWED_FAILS}")
    private Integer vendorPasswordAllowedFails;


    @Value("${jwt.authorization.header}")
    private String authorizationHeader;

    @Value("${jwt.token.prefix}")
    private String tokenPrefix;

    @Value("${jwt.authorities.key}")
    private String authoritiesKey;

    @Value("${APP_BUILD_INFO:}")
    private String appBuildInfo;

    @Value("${populate.init:true}")
    private Boolean populateInit;

    @Value("${populate.overwrite:false}")
    private Boolean populateOverwrite;




}
