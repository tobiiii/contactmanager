package com.ps.contactmanager.controller;


import com.ps.contactmanager.ServiceImpl.ContactServiceImpl;
import com.ps.contactmanager.domain.Contact;
import com.ps.contactmanager.domain.enums.ContactTypeE;
import com.ps.contactmanager.domain.view.ContactView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class ContactControllerTest {

    @Mock
    ContactServiceImpl contactService;

    @InjectMocks
    ContactController contactResource;

    @BeforeEach
    void init() {
        initMocks(this);
    }

    @Test
    void shouldReturnTheExpectedList() {

        ContactView contact1 = new ContactView(Contact.builder().code("test1").firstName("abdelwakil").lastName("rabidi").address("cheraga alger").tva("BE 0123 456 789").type(ContactTypeE.FREELANCE).companies(new ArrayList<>()).build());
        ContactView contact2 = new ContactView(Contact.builder().code("test2").firstName("iyed").lastName("rabidi").address("cheraga alger").tva("BE 0123 456 789").type(ContactTypeE.FREELANCE).companies(new ArrayList<>()).build());
        List<ContactView> contactList = Arrays.asList(contact1, contact2);

        when(contactService.findAllContacts()).thenReturn(contactList);

        List<ContactView> contactListReturn = (List<ContactView>) contactResource.findAll().getData();

        assertNotNull(contactListReturn);
        assertEquals(2, contactListReturn.size());
        assertEquals(contact1, contactListReturn.get(0));
        assertEquals(contact2, contactListReturn.get(1));

    }
    @Test
    void shouldDeleteContact() {

        Long contactId= new Random().nextLong();
        doNothing().when(contactService).deleteContact(contactId);

        contactResource.remove(contactId);

        verify(contactService, times(1)).deleteContact(contactId);
    }
}
