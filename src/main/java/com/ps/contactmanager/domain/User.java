package com.ps.contactmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(uniqueConstraints =
        {@UniqueConstraint(columnNames = "emailAddress", name = "uk_user_email_address")})
public class User extends CommonEntity{

    @JsonIgnore
    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE, optional = false)

    private Password password;

    @NotBlank
    @Column(nullable = false)
    private String firstName;

    @NotBlank
    @Column(nullable = false)
    private String lastName;

    @Email
    @Column(nullable = false)
    private String emailAddress;


    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(
            name = "profile_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_user_profile"))
    private Profile profile;


}
