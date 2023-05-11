package com.ps.contactmanager.ServiceImpl;

import com.ps.contactmanager.domain.Company;
import com.ps.contactmanager.domain.Contact;
import com.ps.contactmanager.domain.DTO.CompanyDto;
import com.ps.contactmanager.domain.enums.ERROR_CODE;
import com.ps.contactmanager.domain.view.CompanyView;
import com.ps.contactmanager.exceptions.ValidationException;
import com.ps.contactmanager.repository.CompanyRepository;
import com.ps.contactmanager.service.CompanyService;
import com.ps.contactmanager.service.ContactService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    ContactService contactService;

    @Override
    public CompanyView addCompany(CompanyDto company) {
        checkCompany(company);
        Company newCompany = fillCompanyFromDto(new Company(),company);
        return new CompanyView((Company) companyRepository.save(newCompany));
    }

    @Override
    public CompanyView updateCompany(Long id, CompanyDto company) {
        Company companyToUpdate = findCompany(id);
        fillCompanyFromDto(companyToUpdate,company);
        return new CompanyView((Company) companyRepository.save(companyToUpdate));
    }

    @Override
    public void deleteCompany(Long id) {
        Company company = findCompany(id);
        companyRepository.delete(company);
    }

    @Override
    public List<CompanyView> findAllCompanies() {
        return companyRepository.findAllOrderByIdDesc();
    }

    @Override
    public CompanyView getCompanyDtails(Long companyId) {
        return new CompanyView(findCompany(companyId));
    }

    @SneakyThrows
    @Override
    public Company findCompany(Long companyId) {
        return (Company) companyRepository.findById(companyId)
                .orElseThrow(() -> new ValidationException(ERROR_CODE.INEXISTANT_COMPANY));
    }

    @Override
    public List<Company> getCompaniesFromIdList(List<Long> companies) {
        List<Company> result = companyRepository.findByIdIn(companies);
        if (result.size() != companies.size()) throw new ValidationException(ERROR_CODE.INEXISTANT_COMPANY);
        return result;
    }

    private void checkCompany(CompanyDto companyDto) throws ValidationException {
        if (companyRepository.existsByCode(companyDto.getCode()))
            throw new ValidationException(ERROR_CODE.CODE_EXISTS);
    }

    public Company fillCompanyFromDto(Company company,CompanyDto companyDto) {
        List<Contact> contacts = contactService.getCompaniesFromIdList(companyDto.getContacts());
        company.setCode(companyDto.getCode());
        company.setAddress(companyDto.getAddress());
        company.setTva(companyDto.getTva());
        company.setContacts(contacts != null ? contacts : new ArrayList<>());
        return company;
    }
}
