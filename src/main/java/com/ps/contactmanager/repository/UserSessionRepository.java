package com.ps.contactmanager.repository;

import com.ps.contactmanager.domain.Profile;
import com.ps.contactmanager.domain.UserSession;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserSessionRepository extends JpaRepository<UserSession, Long> {

    Optional<UserSession> findByToken(String token);

    @Transactional
    @Modifying
    @Query("update UserSession s set s.isActive = false, s.logoutTime = CURRENT_TIMESTAMP " +
            "where s.email = :login and s.isActive = true")
    int closeUserSessions(@Param("login") String login);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update UserSession s set s.isActive = false, s.logoutTime = CURRENT_TIMESTAMP " +
            "where s.user in (select u from User u where u.profile = :profile) and s.isActive = true")
    int closeUserSessionsByProfile(@Param("profile") Profile profile);

}
