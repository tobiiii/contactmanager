package com.ps.contactmanager.domain.view;

import com.ps.contactmanager.domain.Contact;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactView {

    private Long id;

    private Date created;

    private Date updated;

    private String code;

    private String firstName;

    private String lastName;

    private String address;

    private String tva;

    private String type;

    private List<CompanyView> companies;

    public ContactView(Contact contact) {
        this.id = contact.getId();
        this.created = contact.getCreated();
        this.updated = contact.getUpdated();
        this.code = contact.getCode();
        this.firstName = contact.getFirstName();
        this.lastName = contact.getLastName();
        this.address = contact.getAddress();
        this.tva = contact.getTva();
        this.type = contact.getType().name();
        this.companies = contact.getCompanies().stream()
                .map(CompanyView::new)
                .collect(Collectors.toList());
    }

}
