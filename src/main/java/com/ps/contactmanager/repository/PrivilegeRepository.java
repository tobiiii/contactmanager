package com.ps.contactmanager.repository;

import com.ps.contactmanager.domain.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrivilegeRepository<T extends Privilege> extends JpaRepository<T, Long> {

    List<Privilege> findByIdIn(List<Long> id);

    Optional<T> findByCode(String code);


}
