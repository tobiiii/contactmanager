package com.ps.contactmanager.repository;

import com.ps.contactmanager.domain.Profile;
import com.ps.contactmanager.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByEmailAddressIgnoreCase(String email);

    boolean existsByEmailAddressIgnoreCase(String emailAddress);

    boolean existsByProfile(Profile profile);

}
