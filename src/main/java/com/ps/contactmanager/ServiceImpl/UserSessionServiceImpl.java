package com.ps.contactmanager.ServiceImpl;

import com.ps.contactmanager.domain.UserSession;
import com.ps.contactmanager.domain.Profile;
import com.ps.contactmanager.domain.User;
import com.ps.contactmanager.domain.enums.ERROR_CODE;
import com.ps.contactmanager.exceptions.ValidationException;
import com.ps.contactmanager.repository.UserSessionRepository;
import com.ps.contactmanager.security.TokenProvider;
import com.ps.contactmanager.service.UserService;
import com.ps.contactmanager.service.UserSessionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
public class UserSessionServiceImpl implements UserSessionService {

    @Autowired
    private UserSessionRepository userSessionRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenProvider jwtTokenUtil;

    @Transactional
    @Override
    public void disconnectByToken(final String jwtToken) {
        Optional<UserSession> op = userSessionRepository.findByToken(jwtToken);
        if (op.isPresent()) {
            var dbsession = op.get();
            dbsession.setActive(false);
            dbsession.setLogoutTime(new Date());
            userSessionRepository.save(dbsession);
        }
    }

    @Transactional
    @Override
    public void disconnectByProfile(Profile profile) {
        userSessionRepository.closeUserSessionsByProfile(profile);
    }

    @Override
    public Optional<UserSession> findBy(final String field, final String value) {
        if (field == null) {
            return Optional.ofNullable(null);
        }

        switch (field) {
            case "token":
                return userSessionRepository.findByToken(value);
            default:
                break;
        }

        return Optional.ofNullable(null);
    }

    @Override
    public void refreshToken(final String expiredToken, final String newToken) throws ValidationException {
        Optional<UserSession> currentSession = userSessionRepository.findByToken(expiredToken);
        if (currentSession.isPresent()) {

            final var idToken = this.jwtTokenUtil.getIdFromToken(newToken);
            currentSession.get().setToken(idToken);

            final var expiration = this.jwtTokenUtil.getExpirationDateFromToken(newToken);
            currentSession.get().setLogoutTime(expiration);
            userSessionRepository.save(currentSession.get());
        } else {
            log.trace("Invalid Session");
            throw new ValidationException(ERROR_CODE.INVALID_SESSION);
        }

     }

    @Override
    public void disconnectByUser(User user) {
        userSessionRepository.closeUserSessions(user.getEmailAddress());
    }

    @Override
    public void checkSession(Optional<UserSession> userSession) throws ValidationException {
        var getSession = userSession
                .orElseThrow(() -> new ValidationException(ERROR_CODE.INVALID_SESSION));

        if (!getSession.isActive()) {
            throw new ValidationException(ERROR_CODE.INVALID_SESSION);
        }

        if (getSession.isBlocked()) {
            throw new ValidationException(ERROR_CODE.INVALID_SESSION);
        }
    }

    @Override
    public void logout() throws ValidationException {
        User user = userService.getConnectedUser();
        //Close active session
        userSessionRepository.closeUserSessions(user.getEmailAddress());
    }

    @Override
    public void startSession(HttpServletRequest request, String jwtToken, User user) {
        final var currentLocale = request.getLocale();

        //Close active session
        userSessionRepository.closeUserSessions(user.getEmailAddress());

        //Create new session
        var userSession = new UserSession();
        userSession.setUser(user);
        userSession.setFullName(user.getFirstName() + " " + user.getLastName());
        userSession.setIpAddress(
                StringUtils.isNotEmpty(request.getHeader("X-Forwarded-For")) ? request.getHeader("X-Forwarded-For")
                        : request.getRemoteAddr());
        userSession.setLanguage(currentLocale.getDisplayLanguage());
        userSession.setActive(true);
        userSession.setBlocked(false);
        userSession.setEmail(user.getEmailAddress());
        userSession.setLogonTime(new Date());
        final var expiration = jwtTokenUtil.getExpirationDateFromToken(jwtToken);
        userSession.setLogoutTime(expiration);
        final var idFromToken = jwtTokenUtil.getIdFromToken(jwtToken);
        userSession.setToken(idFromToken);
        try {
            userSessionRepository.save(userSession);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
