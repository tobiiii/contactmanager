package com.ps.contactmanager.controller;

import com.ps.contactmanager.domain.Company;
import com.ps.contactmanager.domain.DTO.CompanyDto;
import com.ps.contactmanager.domain.view.CompanyView;
import com.ps.contactmanager.service.CompanyService;
import com.ps.contactmanager.utils.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api/company")
@Validated
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PreAuthorize("hasAnyAuthority(" +
            "'company.list'," +
            "'company.add'," +
            "'company.update'," +
            "'company.detail'," +
            "'company.delete')")
    @GetMapping(value = "/find_all")
    public JsonResponse findAll() {
        List<CompanyView> company = companyService.findAllCompanies();
        return JsonResponse.builder().data(company).status(JsonResponse.STATUS.SUCCESS).build();
    }

    @PreAuthorize("hasAuthority('company.add')")
    @PostMapping(value = "/add")
    public JsonResponse add(@Valid @RequestBody final CompanyDto company) {
        CompanyView createdCompany = companyService.addCompany(company);
        return new JsonResponse(createdCompany);
    }

    @PreAuthorize("hasAnyAuthority(" +
            "'company.detail'," +
            "'company.update')")
    @GetMapping(value = "/detail/{companyId}")
    public JsonResponse detail(
            @NotNull  @PathVariable Long companyId)  {
        CompanyView company = companyService.getCompanyDtails(companyId);
        return new JsonResponse(company);
    }

    @PreAuthorize("hasAuthority('company.update')")
    @PutMapping(value = "/update/{companyId}")
    public JsonResponse update(
            @NotNull @PathVariable Long companyId,
            @Valid @RequestBody CompanyDto company)  {
        CompanyView updatedCompany = companyService.updateCompany(companyId, company);
        return JsonResponse.builder().data(updatedCompany).status(JsonResponse.STATUS.SUCCESS).build();
    }


    @PreAuthorize("hasAuthority('company.delete')")
    @DeleteMapping(value = "/delete/{companyId}")
    public JsonResponse remove(
            @NotNull @PathVariable Long companyId)  {
        companyService.deleteCompany(companyId);
        return JsonResponse.builder().status(JsonResponse.STATUS.SUCCESS).build();
    }

}