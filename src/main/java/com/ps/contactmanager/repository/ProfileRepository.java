package com.ps.contactmanager.repository;

import com.ps.contactmanager.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository<T extends Profile> extends JpaRepository<T, Long> {

}
