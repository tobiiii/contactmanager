package com.ps.contactmanager.service;

import com.ps.contactmanager.domain.DTO.AuthenticationRequest;
import com.ps.contactmanager.domain.DTO.AuthenticationResponse;
import com.ps.contactmanager.domain.DTO.ChangePasswordDto;
import com.ps.contactmanager.domain.DTO.UserDto;
import com.ps.contactmanager.domain.Profile;
import com.ps.contactmanager.domain.User;
import com.ps.contactmanager.domain.view.UserView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {

    Page<UserView> getAllUsers(Pageable pageable);

    UserView create(UserDto user) ;

    boolean isProfileAttributed(Profile profile);

    User update(Long userId, UserDto user) ;

    UserView findUserViewById(Long userId) ;

    void delete(Long userId) ;

    AuthenticationResponse authentication(AuthenticationRequest authenticationRequest,
                                          HttpServletRequest request, HttpServletResponse response) ;

    User checkEmail(String email);

    User getConnectedUser() ;

    AuthenticationResponse renewSession(String tokenId, HttpServletRequest request) ;

    void changePassword(ChangePasswordDto passwordDto);


}


