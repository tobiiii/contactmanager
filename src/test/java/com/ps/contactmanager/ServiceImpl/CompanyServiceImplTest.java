package com.ps.contactmanager.ServiceImpl;

import com.ps.contactmanager.domain.Company;
import com.ps.contactmanager.domain.DTO.CompanyDto;
import com.ps.contactmanager.domain.view.CompanyView;
import com.ps.contactmanager.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class CompanyServiceImplTest {

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    ContactServiceImpl contactService;


    @InjectMocks
    CompanyServiceImpl companyService;


    @BeforeEach
    void init() {
        initMocks(this);
    }

    @Test
    void itShouldAddCompany() {
        //given
        CompanyDto companyDto = CompanyDto.builder().code("TEST").address("alger").tva("BE 0123 456 789").contacts(new ArrayList<>()).build();
        Company company = Company.builder().code("TEST").address("alger").tva("BE 0123 456 789").contacts(new ArrayList<>()).build();

        //when
        when(companyRepository.save(company)).thenReturn(company);
        given(companyRepository.existsByCode(companyDto.getCode())).willReturn(false);
        when(contactService.getCompaniesFromIdList(companyDto.getContacts())).thenReturn(null);
        CompanyView result = companyService.addCompany(companyDto);

        //then
        assertNotNull(result);
        assertEquals(new CompanyView(company), result);
    }


    @Test
    void itShouldReturnTheCorrectList() {
        //given
        CompanyView company1 = new CompanyView(Company.builder().code("TEST1").address("alger").tva("BE 0123 456 789").contacts(new ArrayList<>()).build());
        CompanyView company2 = new CompanyView(Company.builder().code("TEST2").address("alger").tva("BE 0123 456 789").contacts(new ArrayList<>()).build());
        CompanyView company3 = new CompanyView(Company.builder().code("TEST3").address("alger").tva("BE 0123 456 789").contacts(new ArrayList<>()).build());
        List<CompanyView> companies = Arrays.asList(company1, company2,company3);

        //when
        when(companyRepository.findAllOrderByIdDesc()).thenReturn(companies);
        List<CompanyView> allCompanies = companyService.findAllCompanies();

        //then
        assertNotNull(allCompanies);
        assertEquals(3, allCompanies.size());
        assertEquals(company2, allCompanies.get(1));
    }

    @Test
    void willNotDeleteCompanyNotFound() {
        // given
        long id = 1;
        given(companyRepository.findById(id)).willReturn(null);

        // then
        verify(companyRepository, never()).deleteById(any());    }
}