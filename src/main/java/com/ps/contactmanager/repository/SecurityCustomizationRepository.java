package com.ps.contactmanager.repository;

import com.ps.contactmanager.domain.SecurityCustomization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityCustomizationRepository extends JpaRepository<SecurityCustomization, Long> {

}
