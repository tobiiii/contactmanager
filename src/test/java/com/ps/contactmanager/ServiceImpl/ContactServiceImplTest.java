package com.ps.contactmanager.ServiceImpl;

import com.ps.contactmanager.domain.Contact;
import com.ps.contactmanager.domain.DTO.ContactDto;
import com.ps.contactmanager.domain.enums.ContactTypeE;
import com.ps.contactmanager.domain.view.ContactView;
import com.ps.contactmanager.repository.ContactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class ContactServiceImplTest {

    @Mock
    private ContactRepository contactRepository;

    @Mock
    CompanyServiceImpl companyService;

    @InjectMocks
    ContactServiceImpl contactService;

    @BeforeEach
    void init() {
        initMocks(this);
    }

    @Test
    void canAddContact() {

        Contact contact = Contact.builder().code("test").firstName("abdelwakil").lastName("rabidi").address("cheraga alger").tva("BE 0123 456 789").type(ContactTypeE.FREELANCE).companies(new ArrayList<>()).build();
        ContactDto contactDto = ContactDto.builder().code("test").firstName("abdelwakil").lastName("rabidi").address("cheraga alger").tva("BE 0123 456 789").type("FREELANCE").companies(new ArrayList<>()).build();
        when(contactRepository.save(contact)).thenReturn(contact);
        given(contactRepository.existsByCode(contactDto.getCode())).willReturn(false);
        when(companyService.getCompaniesFromIdList(contactDto.getCompanies())).thenReturn(null);
        ContactView result = contactService.addContact(contactDto);

        assertNotNull(result);
        assertEquals(new ContactView(contact), result);
    }

    @Test
    void shouldReturnTheExpectedList() {
        ContactView contact1 = new ContactView(Contact.builder().code("test1").firstName("abdelwakil").lastName("rabidi").address("cheraga alger").tva("BE 0123 456 789").type(ContactTypeE.FREELANCE).companies(new ArrayList<>()).build());
        ContactView contact2 = new ContactView(Contact.builder().code("test2").firstName("iyed").lastName("rabidi").address("cheraga alger").tva("BE 0123 456 789").type(ContactTypeE.FREELANCE).companies(new ArrayList<>()).build());
        List<ContactView> contactList = Arrays.asList(contact1, contact2);

        when(contactRepository.findAllOrderByIdDesc()).thenReturn(contactList);

        List<ContactView> contactListReturn = contactService.findAllContacts();

        assertNotNull(contactListReturn);
        assertEquals(2, contactListReturn.size());
        assertEquals(contact1, contactListReturn.get(0));
        assertEquals(contact2, contactListReturn.get(1));

    }
}