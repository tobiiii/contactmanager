package com.ps.contactmanager.domain.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class ContactDataView {

    private String code;

    private String firstName;

    private String lastName;

    private String address;

    private String tva;

    private String type;

    public ContactDataView(Contact contact) {
        this.code = contact.getCode();
        this.firstName = contact.getFirstName();
        this.lastName = contact.getLastName();
        this.address = contact.getAddress();
        this.tva = contact.getTva();
        this.type = contact.getType().name();
    }

}
