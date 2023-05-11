package com.ps.contactmanager.security;

import com.ps.contactmanager.domain.Privilege;
import com.ps.contactmanager.domain.User;
import com.ps.contactmanager.domain.enums.ERROR_CODE;
import com.ps.contactmanager.exceptions.ValidationException;
import com.ps.contactmanager.utils.ParamsProvider;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import jakarta.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class TokenProvider implements Serializable {

    private static final long serialVersionUID = 1L;

    private final SignatureAlgorithm signatureAlgorithm;

    private final Key signingKey;

    @Autowired
    private ParamsProvider paramsProvider;

    private final JwtConfig jwtConfig;

    public TokenProvider(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;

        this.signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] secret = Base64.getEncoder().encode(this.jwtConfig.getHmacSecret().getBytes());
        this.signingKey = new SecretKeySpec(secret, signatureAlgorithm.getJcaName());
    }

    public String getIdFromToken(String token) {
        return getClaimFromToken(token, Claims::getId);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(Base64.getEncoder().encode(jwtConfig.getHmacSecret().getBytes()))
                .parseClaimsJws(token).getBody();
    }
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }


    public String generateToken(User user, Map<String, String> claims) throws ValidationException {
        String authorities = user.getProfile().getPrivileges().stream()
                .map(Privilege::getCode)
                .collect(Collectors.joining(","));
        long tokenValidity = paramsProvider.getTokenDuration();
        return createToken(user.getEmailAddress(), authorities, tokenValidity, claims);
    }

    private String createToken(String username, String authorities, long tokenValidity, Map<String, String> claims) {
        var jwtBuilder = Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(username)
                .claim(paramsProvider.getAuthoritiesKey(), authorities)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenValidity * 60 * 1000))
                .signWith(signatureAlgorithm, signingKey);
        if (claims != null && !claims.isEmpty()) {
            for (Map.Entry<String, String> entry : claims.entrySet()) {
                jwtBuilder.claim(entry.getKey(), entry.getValue());
            }
        }

        return jwtBuilder.compact();

    }

    public String getIdFromExpiredToken(String expiredToken) throws ValidationException {
        String id;
        try {
            getIdFromToken(expiredToken);
            //If token not expired (valid) throw exception TOKEN_ALREADY_VALID
            throw new ValidationException(ERROR_CODE.TOKEN_ALREADY_VALID);
        } catch (ExpiredJwtException e) {
            id = e.getClaims().getId();
        }
        return id;
    }


    public String getTokenFromRequest(HttpServletRequest req) throws ValidationException {
        String auth = req.getHeader(paramsProvider.getAuthorizationHeader());
        if (StringUtils.isEmpty(auth)) {
            throw new ValidationException(ERROR_CODE.TOKEN_NOT_FOUND);
        }
        return auth.replace(paramsProvider.getTokenPrefix(), "").trim();
    }

    public void validateToken(String token) throws ValidationException {
        try {
            getUsernameFromToken(token);
        } catch (IllegalArgumentException e) {
            log.error("An error occurred while fetching Username from Token");
            throw new ValidationException(ERROR_CODE.UNAUTHORIZED);
        } catch (ExpiredJwtException e) {
            log.error("Expired token");
            throw new ValidationException(ERROR_CODE.EXPIRED_TOKEN);
        } catch (SignatureException e) {
            log.error("Token signature error");
            throw new ValidationException(ERROR_CODE.UNAUTHORIZED);
        } catch (MalformedJwtException e) {
            log.error("Malformed Jwt Token error");
            throw new ValidationException(ERROR_CODE.UNAUTHORIZED);
        }
    }
    public UsernamePasswordAuthenticationToken getAuthenticationToken(final String token, final UserDetails userDetails) {
        final var jwtParser = Jwts.parser().setSigningKey(Base64.getEncoder().encode(jwtConfig.getHmacSecret().getBytes()));
        final Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);
        final var claims = claimsJws.getBody();

        final Collection<? extends GrantedAuthority> authorities = Arrays
                .stream(claims.get(paramsProvider.getAuthoritiesKey()).toString().split(",")).map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }


}
