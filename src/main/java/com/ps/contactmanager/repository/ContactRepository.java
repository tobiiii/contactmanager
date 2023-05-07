package com.ps.contactmanager.repository;

import com.ps.contactmanager.domain.Contact;
import com.ps.contactmanager.domain.view.ContactView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ContactRepository<T extends Contact> extends JpaRepository<T, Long> {

    List<Contact> findByIdIn(List<Long> id);

    boolean existsByCode(String code);

    @Query("select c from Contact c ORDER BY c.id ASC ")
    List<ContactView> findAllOrderByIdDesc ();


}
