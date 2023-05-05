package com.ps.contactmanager.security;

import com.ps.contactmanager.ServiceImpl.UserDetailsServiceImpl;
import com.ps.contactmanager.domain.User;
import com.ps.contactmanager.domain.UserSession;
import com.ps.contactmanager.domain.enums.ERROR_CODE;
import com.ps.contactmanager.exceptions.ValidationException;
import com.ps.contactmanager.service.UserService;
import com.ps.contactmanager.service.UserSessionService;
import com.ps.contactmanager.utils.JsonResponse;
import com.ps.contactmanager.utils.ParamsProvider;
import com.ps.contactmanager.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.Optional;

@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private UserService userService;

    @Autowired
    private ParamsProvider paramsProvider;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private TokenProvider jwtUtil;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserSessionService userSessionService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String token = null;

        final String tokenHeader = request.getHeader(paramsProvider.getAuthorizationHeader());
        if (tokenHeader != null && tokenHeader.startsWith(paramsProvider.getTokenPrefix())) {

            //Check token validation
            try {
                token = jwtUtil.getTokenFromRequest(request);
                jwtUtil.validateToken(token);
            } catch (ValidationException e) {
                if (request.getRequestURI().endsWith("/refresh_token") &&
                    ERROR_CODE.valueOf(e.getErrorCode()).equals(ERROR_CODE.EXPIRED_TOKEN)) {
                    //Let him pass filter
                    chain.doFilter(request, response);
                    return;
                }

                generateFailResponse(request, response, HttpStatus.UNAUTHORIZED, ERROR_CODE.valueOf(e.getErrorCode()));
                return;
            }

            //Get username from token
            String email = jwtUtil.getUsernameFromToken(token);
            if (StringUtils.isBlank(email)) {
                generateFailResponse(request, response, HttpStatus.UNAUTHORIZED, ERROR_CODE.INVALID_SESSION);
                return;
            }
            User user = null;
            try {
                user = userService.checkEmail(email);
            } catch (ValidationException e) {
                // TODO close sessions if exist
                generateFailResponse(request, response, HttpStatus.UNAUTHORIZED, ERROR_CODE.valueOf(e.getErrorCode()));
                return;
            }


            //Check session
            final var idToken = jwtUtil.getIdFromToken(token);
            Optional<UserSession> optionalSession;

            try {
                optionalSession = userSessionService.findBy("token", idToken);
                if (optionalSession.isEmpty() || !optionalSession.get().getEmail().equals(user.getEmailAddress())) {
                    throw new ValidationException(ERROR_CODE.INVALID_SESSION);
                }
                userSessionService.checkSession(optionalSession);
            } catch (ValidationException e) {
                generateFailResponse(request, response, HttpStatus.UNAUTHORIZED, ERROR_CODE.valueOf(e.getErrorCode()));
                return;
            }

            //Set security context holder
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = null;
                if (optionalSession.isPresent()) {
                    userDetails = userDetailsServiceImpl.loadUserByUsername(email, optionalSession.get());

                } else {
                    userDetails = userDetailsServiceImpl.loadUserByUsername(email);
                }

                var authentication = jwtUtil.getAuthenticationToken(token, userDetails);
                var webDetails = new ContactmanagerWebAuthenticationDetails(request);
                authentication.setDetails(webDetails);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }


        chain.doFilter(request, response);
    }

    private void generateFailResponse(HttpServletRequest req, HttpServletResponse res, HttpStatus httpStatus,
                                      ERROR_CODE errorCode, Object... params) {
        res.setContentType("application/json");
        res.setStatus(httpStatus.value());
        JsonResponse jsonResponse = new JsonResponse();
        jsonResponse.setStatus(JsonResponse.STATUS.FAILED);
        jsonResponse.setErrorCode(errorCode.name());
        String lang = req.getHeader("Accept-Language");

        try {
//            Locale locale = StringUtils.isNotBlank(lang) ? new Locale(lang) : new Locale("fr");
            Locale locale = new Locale("fr");
            jsonResponse.setErrorMsg(messageSource.getMessage(jsonResponse.getErrorCode(), params, locale));
        } catch (NoSuchMessageException e2) {
//            jsonResponse.setErrorMsg("");
        }
        try {
            res.getWriter().write(Utils.convertObjectToJson(jsonResponse));
            res.getWriter().flush();
        } catch (IOException e) {
            log.warn("generateFailResponse", e.getMessage());
        }
    }

}
