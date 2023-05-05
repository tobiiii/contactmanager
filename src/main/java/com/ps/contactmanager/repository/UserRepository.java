package com.ps.contactmanager.repository;

import com.ps.contactmanager.domain.Profile;
import com.ps.contactmanager.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    boolean existsByEmailAddressIgnoreCase(String emailAddress);

    boolean existsByProfile(Profile profile);

}
