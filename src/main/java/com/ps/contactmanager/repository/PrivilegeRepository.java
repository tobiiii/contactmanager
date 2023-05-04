package com.ps.contactmanager.repository;

import com.ps.contactmanager.domain.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepository<T extends Privilege> extends JpaRepository<T, Long> {


}
