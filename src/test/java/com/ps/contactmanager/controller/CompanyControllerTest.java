package com.ps.contactmanager.controller;


import com.ps.contactmanager.domain.Company;
import com.ps.contactmanager.ServiceImpl.CompanyServiceImpl;
import com.ps.contactmanager.domain.DTO.CompanyDto;
import com.ps.contactmanager.domain.view.CompanyView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.when;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.junit.jupiter.api.Assertions.*;

class CompanyControllerTest {

    @Mock
    private CompanyServiceImpl companyService;

    @InjectMocks
    private CompanyController companyResource;

    @BeforeEach
    void init() {
        initMocks(this);
    }

    @Test
    void shouldReturnTheExpectedList() {

        CompanyView company1 = new CompanyView(Company.builder().code("TEST1").address("alger").tva("BE 0123 456 789").contacts(new ArrayList<>()).build());
        CompanyView company2 = new CompanyView(Company.builder().code("TEST2").address("alger").tva("BE 0123 456 789").contacts(new ArrayList<>()).build());
        CompanyView company3 = new CompanyView(Company.builder().code("TEST3").address("alger").tva("BE 0123 456 789").contacts(new ArrayList<>()).build());
        List<CompanyView> companies = Arrays.asList(company1, company2, company3);

        when(companyService.findAllCompanies()).thenReturn(companies);

        List<CompanyView> result = (List<CompanyView>) companyResource.findAll().getData();

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(company3, result.get(2));
    }

    @Test
    void shouldAddCompany() {
        CompanyDto companyDto = CompanyDto.builder().code("TEST").address("alger").tva("BE 0123 456 789").contacts(new ArrayList<>()).build();
        Company company = Company.builder().code("TEST").address("alger").tva("BE 0123 456 789").contacts(new ArrayList<>()).build();

        when(companyService.addCompany(companyDto)).thenReturn(new CompanyView(company));

        CompanyView result = (CompanyView) companyResource.add(companyDto).getData();

        assertNotNull(result);
        assertEquals(new CompanyView(company), result);
    }


    @Test
    void shouldUpdateCompany() {

        long id = new Random().nextLong();
        CompanyDto companyDto = CompanyDto.builder().code("TEST").address("alger").tva("BE 0123 456 789").contacts(new ArrayList<>()).build();
        CompanyView company = new CompanyView(Company.builder().code("TEST").address("Bouzareha").tva("BE 0123 456 789").contacts(new ArrayList<>()).build());

        when(companyService.updateCompany(id, companyDto)).thenReturn(company);

        CompanyDto company1 = companyDto;
        company1.setAddress("Bouzareha");
        CompanyView expected = new CompanyView(Company.builder()
                .code(company1.getCode())
                .address(company1.getAddress())
                .tva(company1.getTva())
                .contacts(new ArrayList<>()).build());

        CompanyView result = (CompanyView) companyResource.update(id, company1).getData();

        assertNotNull(result);
        assertEquals(company, result);
        assertEquals("Bouzareha", result.getAddress());
    }

    @Test
    void shouldDeleteCompany() {

        Long companyId = 1L;

        doNothing().when(companyService).deleteCompany(companyId);

        companyResource.remove(companyId);

        verify(companyService, times(1)).deleteCompany(companyId);
    }

    @Test
    void testDeleteCompany2() throws Exception {
        doNothing().when(this.companyService).deleteCompany((Long) any());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/api/company/delete/{companyId}", new Random().nextLong());
        deleteResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(this.companyResource)
                .build()
                .perform(deleteResult)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetAllCompanies() throws Exception {
        org.mockito.Mockito.when(this.companyService.findAllCompanies()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/company/find_all");
        MockMvcBuilders.standaloneSetup(this.companyResource)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
    }
}