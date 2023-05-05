package com.ps.contactmanager.service;

import com.ps.contactmanager.domain.UserSession;
import com.ps.contactmanager.domain.Profile;
import com.ps.contactmanager.domain.User;
import com.ps.contactmanager.exceptions.ValidationException;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;


public interface UserSessionService {

        void disconnectByToken(final String jwtToken);

        void disconnectByProfile(final Profile profile);

        void disconnectByUser(final User user);

        Optional<UserSession> findBy(final String field, final String value);

        void checkSession(Optional<UserSession> dbSession) throws ValidationException;

        void logout() throws ValidationException;

        void startSession(final HttpServletRequest request, final String jwtToken, User user);

        void refreshToken(String expiredToken, final String newToken) throws ValidationException;

}
