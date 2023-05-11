package com.ps.contactmanager.controller;

import com.ps.contactmanager.domain.DTO.AuthenticationRequest;
import com.ps.contactmanager.domain.DTO.AuthenticationResponse;
import com.ps.contactmanager.exceptions.ValidationException;
import com.ps.contactmanager.service.UserService;
import com.ps.contactmanager.service.UserSessionService;
import com.ps.contactmanager.utils.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/auth")
@Validated
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserSessionService userSessionService;

    /**
     * Admin authentication
     *
     * @param authenticationRequest authentication request (username, password)
     * @return JSON response
     * @throws ValidationException validation exception
     */
    @PostMapping(value = "/authentification")
    public JsonResponse authentication(@Valid @RequestBody final AuthenticationRequest authenticationRequest,
                                       final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) {
        AuthenticationResponse response = userService.authentication(authenticationRequest, httpServletRequest, httpServletResponse);
        return JsonResponse.builder()
                .data(response)
                .status(JsonResponse.STATUS.SUCCESS).build();

    }

    /**
     * Admin refresh token
     *
     * @param tokenId id expired token request (refresh token)
     * @return JSON response
     * @throws ValidationException validation exception
     */
    @PostMapping(value = "/refresh_token")
    public JsonResponse refreshToken(
            @NotNull(message = "Token {REQUIRED}") String tokenId,
            HttpServletRequest request) {
        AuthenticationResponse authenticationResponse = userService.renewSession(tokenId, request);
        return JsonResponse.builder().data(authenticationResponse)
                .status(JsonResponse.STATUS.SUCCESS).build();
    }

    /**
     * Logout admin user
     *
     * @return JSON response
     * @throws ValidationException validation exception
     */
    @PreAuthorize("isFullyAuthenticated()")
    @GetMapping(value = "/logout")
    public JsonResponse logout() throws ValidationException {
        userSessionService.logout();
        return JsonResponse.builder().data(null)
                .status(JsonResponse.STATUS.SUCCESS).build();
    }
}
