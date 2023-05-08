package com.ps.contactmanager.ServiceImpl;

import com.ps.contactmanager.domain.Company;
import com.ps.contactmanager.domain.Contact;
import com.ps.contactmanager.domain.DTO.ContactDto;
import com.ps.contactmanager.domain.enums.ContactTypeE;
import com.ps.contactmanager.domain.enums.ERROR_CODE;
import com.ps.contactmanager.domain.view.ContactView;
import com.ps.contactmanager.exceptions.ValidationException;
import com.ps.contactmanager.repository.ContactRepository;
import com.ps.contactmanager.service.CompanyService;
import com.ps.contactmanager.service.ContactService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    CompanyService companyService;

    @Override
    public ContactView addContact(ContactDto contact) {
        checkContact(contact);
        Contact newContact = fillContactFromDto(new Contact(),contact);
        return new ContactView((Contact) contactRepository.save(newContact));
    }

    @Override
    public ContactView updateContact(Long id, ContactDto contact) {
        Contact contactToUpdate = findContactById(id);
        fillContactFromDto(contactToUpdate,contact);
        return new ContactView((Contact) contactRepository.save(contactToUpdate));    }

    @Override
    public void deleteContact(Long id) {
        Contact contactToUpdate = findContactById(id);
        if (contactToUpdate.getCompanies() != null && !contactToUpdate.getCompanies().isEmpty()) detachCompanies(contactToUpdate);
        contactRepository.delete(contactToUpdate);
    }

    @Override
    public List<ContactView> findAllContacts() {
        return contactRepository.findAllOrderByIdDesc();
    }

    @SneakyThrows
    public Contact findContactById(Long id) {
        return (Contact) contactRepository.findById(id)
                .orElseThrow(() -> new ValidationException(ERROR_CODE.INEXISTANT_PROFILE));

    }

    public ContactView getContactDtails(Long id) {
        return new ContactView(findContactById(id));
    }


    public Contact fillContactFromDto(Contact newContact ,ContactDto contactDto) {
        List<Company> companies = companyService.getCompaniesFromIdList(contactDto.getCompanies());
        newContact.setCode(contactDto.getCode());
        newContact.setFirstName(contactDto.getFirstName());
        newContact.setLastName(contactDto.getLastName());
        newContact.setAddress(contactDto.getAddress());
        newContact.setTva(contactDto.getTva());
        newContact.setType(ContactTypeE.valueOf(contactDto.getType()));
        newContact.setCompanies(companies != null ? companies : new ArrayList<>());
        return newContact;
    }

    private void checkContact(ContactDto contactDto) throws ValidationException {
        if (contactRepository.existsByCode(contactDto.getCode()))
            throw new ValidationException(ERROR_CODE.CODE_EXISTS);

        if (contactDto.getType().equals(ContactTypeE.FREELANCE.name())
                && (contactDto.getTva() == null || contactDto.getTva().isEmpty()))
            throw new ValidationException(ERROR_CODE.CODE_FREELANCE_TVA);
    }

    public void detachCompanies(Contact contact) {
        for (Company company :contact.getCompanies()) {
            company.getContacts().remove(contact);
        }
        contact.setCompanies(new ArrayList<>());
        contactRepository.save(contact);
    }

    @Override
    public List<Contact> getCompaniesFromIdList(List<Long> contacts) {
        List<Contact> result = contactRepository.findByIdIn(contacts);
        if (result.size() != contacts.size()) throw new ValidationException(ERROR_CODE.INEXISTANT_CONTACT);
        return result;
    }


    @Override
    public ContactView addContactToCompany(Long idContact,Long idCompany){
        Contact contact = findContactById(idContact);
        Company company = companyService.findCompany(idCompany);
        return new ContactView(attachContactToCompany(contact,company));
    }


    public Contact attachContactToCompany(Contact contact , Company company){
        contact.getCompanies().add(company);
        company.getContacts().add(contact);
        return (Contact) contactRepository.save(contact);
    }


}
