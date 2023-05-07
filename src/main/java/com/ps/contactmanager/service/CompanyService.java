package com.ps.contactmanager.service;

import com.ps.contactmanager.domain.Company;
import com.ps.contactmanager.domain.DTO.CompanyDto;
import com.ps.contactmanager.domain.view.CompanyView;

import java.util.List;

public interface CompanyService {

    CompanyView addCompany(CompanyDto company);

    CompanyView updateCompany(Long id,CompanyDto company);

    void deleteCompany(Long id);

    List<CompanyView> findAllCompanies();

    CompanyView getCompanyDtails(Long companyId);

    Company findCompany(Long companyId);

    List<Company> getCompaniesFromIdList(List<Long> companies);
}