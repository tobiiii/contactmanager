package com.ps.contactmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ps.contactmanager.domain.enums.ContactTypeE;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = "code", name = "uk_contact_code"),
})
public class Contact extends CommonEntity{


    @Column(name = "code", updatable = false, unique = true)
    private String code;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "tva")
    private String tva; // example: TVA BE 0123 456 789

    @Column(name = "type")
    private ContactTypeE type;

    @JsonIgnore
    @ManyToMany(mappedBy = "contacts")
    private Collection<Company> companies;

}
