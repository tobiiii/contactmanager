package com.ps.contactmanager.repository;

import com.ps.contactmanager.domain.Company;
import com.ps.contactmanager.domain.view.CompanyView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CompanyRepository<T extends Company> extends JpaRepository<T, Long> {

    boolean existsByCode(String code);

    List<Company> findByIdIn(List<Long> id);

    @Query("select c from Company c ORDER BY c.id ASC ")
    List<CompanyView> findAllOrderByIdDesc ();

}
