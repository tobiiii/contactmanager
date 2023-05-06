package com.ps.contactmanager.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactDto {

    private String code;

    private String firstName;

    private String lastName;

    private String address;

    private String tva;

    private String type;

    private List<Long> companies;
}
