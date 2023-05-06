package com.ps.contactmanager.domain.view;

import com.ps.contactmanager.domain.Company;

import java.util.Date;


public class CompanyView {

    private Long id;

    private Date created;

    private Date updated;

    private String code;

    private String address;

    private String tva;


    public CompanyView(Company company) {
        this.id = company.getId();
        this.created = company.getCreated();
        this.updated = company.getUpdated();
        this.code = company.getCode();
        this.address = company.getAddress();
        this.tva = company.getTva();
    }

}
