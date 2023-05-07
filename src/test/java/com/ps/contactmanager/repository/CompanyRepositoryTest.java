package com.ps.contactmanager.repository;

import com.ps.contactmanager.domain.Company;
import com.ps.contactmanager.domain.view.CompanyView;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class CompanyRepositoryTest {

    @Autowired
    private CompanyRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll(); 
    }

    @Test
    void itShouldFindCompanyList() {

        //given
        Company company1 = Company.builder().code("TEST1").address("alger").tva("BE 0123 456 789").contacts(new ArrayList<>()).build();
        Company company2 = Company.builder().code("TEST2").address("alger").tva("BE 0123 456 789").contacts(new ArrayList<>()).build();
        Company company3 = Company.builder().code("TEST3").address("alger").tva("BE 0123 456 789").contacts(new ArrayList<>()).build();
        underTest.save(company1);
        underTest.save(company2);
        underTest.save(company3);

        //when
        List expected = underTest.findAllOrderByIdDesc();

        //then
        assertEquals(3, expected.size());

    }
}