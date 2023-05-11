package com.ps.contactmanager.domain.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDto {

    private String code;

    private String address;

    private String tva;

    private List<Long> contacts;

}
