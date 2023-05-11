package com.ps.contactmanager.controller;

import com.ps.contactmanager.domain.DTO.ContactDto;
import com.ps.contactmanager.domain.view.ContactView;
import com.ps.contactmanager.service.ContactService;
import com.ps.contactmanager.utils.JsonResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contact")
@Validated
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PreAuthorize("hasAnyAuthority(" +
            "'contact.list'," +
            "'contact.add'," +
            "'contact.update'," +
            "'contact.detail'," +
            "'contact.delete')")
    @GetMapping(value = "/find_all")
    public JsonResponse findAll() {
        List<ContactView> contact = contactService.findAllContacts();
        return JsonResponse.builder().data(contact).status(JsonResponse.STATUS.SUCCESS).build();
    }

    @PreAuthorize("hasAuthority('contact.add')")
    @PostMapping(value = "/add")
    public JsonResponse add(@Valid @RequestBody final ContactDto contact) {
        ContactView createdContact = contactService.addContact(contact);
        return new JsonResponse(createdContact);
    }

    @PreAuthorize("hasAnyAuthority(" +
            "'contact.detail'," +
            "'contact.update')")
    @GetMapping(value = "/detail/{contactId}")
    public JsonResponse detail(
            @NotNull  @PathVariable Long contactId)  {
        ContactView contact = contactService.getContactDtails(contactId);
        return new JsonResponse(contact);
    }

    @PreAuthorize("hasAuthority('contact.update')")
    @PutMapping(value = "/update/{contactId}")
    public JsonResponse update(
            @NotNull @PathVariable Long contactId,
            @Valid @RequestBody ContactDto contact)  {
        ContactView updatedContact = contactService.updateContact(contactId, contact);
        return new JsonResponse(updatedContact);
    }

    @PreAuthorize("hasAuthority('contact.add_to_company')")
    @PutMapping(value = "/add_to_company/{contactId}/{companyId}")
    public JsonResponse addToCompany(
            @NotNull @PathVariable Long contactId,
            @NotNull @PathVariable Long companyId)  {
        ContactView updatedContact = contactService.addContactToCompany(contactId, companyId);
        return new JsonResponse(updatedContact);
    }


    @PreAuthorize("hasAuthority('contact.delete')")
    @DeleteMapping(value = "/delete/{contactId}")
    public JsonResponse remove(
            @NotNull @PathVariable Long contactId)  {
        contactService.deleteContact(contactId);
        return JsonResponse.builder().status(JsonResponse.STATUS.SUCCESS).build();
    }



}