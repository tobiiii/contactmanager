package com.ps.contactmanager.service;

import com.ps.contactmanager.domain.Company;
import com.ps.contactmanager.domain.Contact;
import com.ps.contactmanager.domain.DTO.ContactDto;
import com.ps.contactmanager.domain.view.ContactView;

import java.util.List;

public interface ContactService {

    ContactView addContact(ContactDto contact);

    ContactView updateContact(Long id,ContactDto contact);

    void deleteContact(Long id);

    List<ContactView> findAllContacts();

    ContactView getContactDtails(Long id);

    ContactView addContactToCompany(Long idContact, Long idCompany);

    List<Contact> getCompaniesFromIdList(List<Long> contacts);

}