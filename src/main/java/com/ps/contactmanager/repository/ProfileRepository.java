package com.ps.contactmanager.repository;

import com.ps.contactmanager.domain.Profile;
import com.ps.contactmanager.domain.view.ProfileView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository<T extends Profile> extends JpaRepository<T, Long> {

    boolean existsByCode(String code);

    boolean existsByName(String name);

    @Query("select p from Profile p where  p.code <> 'SUPERADMIN' ORDER BY p.id ASC ")
    List<ProfileView> findAllOrderByIdDesc ();

    Optional<T> findByCode(String code);



}
