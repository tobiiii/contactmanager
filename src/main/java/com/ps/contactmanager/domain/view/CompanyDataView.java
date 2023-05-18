package com.ps.contactmanager.domain.view;

import com.ps.contactmanager.domain.Company;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDataView {

    private String code;

    private String address;

    private String tva;


    public CompanyDataView(Company company) {
        this.code = company.getCode();
        this.address = company.getAddress();
        this.tva = company.getTva();
    }

}
