package com.ps.contactmanager.service;

import com.ps.contactmanager.domain.DTO.AuthenticationRequest;
import com.ps.contactmanager.domain.DTO.AuthenticationResponse;
import com.ps.contactmanager.domain.DTO.UserDto;
import com.ps.contactmanager.domain.User;
import com.ps.contactmanager.domain.view.UserView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserService {

    Page<UserView> getAllUsers(Pageable pageable);

    UserView create(UserDto user) ;

    User update(Long userId, UserDto user) ;

    UserView findUserById(Long userId) ;

    void delete(Long userId) ;

    AuthenticationResponse authentication(AuthenticationRequest authenticationRequest,
                                          HttpServletRequest request, HttpServletResponse response) ;





}


