package com.ps.contactmanager.repository;

import com.ps.contactmanager.domain.Company;
import com.ps.contactmanager.domain.Contact;
import com.ps.contactmanager.domain.enums.ContactTypeE;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ContactRepositoryTest {

    @Autowired
    private ContactRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldFindCompanyList() {

        //given
        Contact contact = Contact.builder().code("test").firstName("abdelwakil").lastName("rabidi").address("cheraga alger").tva("BE 0123 456 789").type(ContactTypeE.FREELANCE).companies(new ArrayList<>()).build();
        underTest.save(contact);

        //when
        List expected = underTest.findAllOrderByIdDesc();

        //then
        assertEquals(1, expected.size());

    }
}