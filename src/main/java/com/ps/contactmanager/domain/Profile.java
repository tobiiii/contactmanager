package com.ps.contactmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = "code", name = "uk_profile_code"),
        @UniqueConstraint(columnNames = "name", name = "uk_profile_name"),
})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Profile extends CommonEntity {

    @NotBlank
    @Column(nullable = false)
    private String code;

    @NotBlank
    @Column(nullable = false)
    private String name;


    @JsonIgnore
    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "profiles_privileges",
            joinColumns = @JoinColumn(
                    name = "profile_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "privilege_id", referencedColumnName = "id"),
            foreignKey = @ForeignKey(name = "fk_profile_privilege"),
            inverseForeignKey = @ForeignKey(name = "fk_privilege_profile"))
    private Collection<Privilege> privileges;
}
