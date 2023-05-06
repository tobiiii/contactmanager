package com.ps.contactmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
public class Company extends CommonEntity{

    @Column(name = "code", updatable = false, unique = true)
    private String code;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "tva", nullable = false)
    private String tva; // example: TVA BE 0123 456 789

    @JsonIgnore
    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "company_contacts",
            joinColumns = @JoinColumn(
                    name = "company_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "contacts_id", referencedColumnName = "id"),
            foreignKey = @ForeignKey(name = "fk_Company_Contact"),
            inverseForeignKey = @ForeignKey(name = "fk_Contact_Company"))
    private Collection<Contact> contacts;

}
