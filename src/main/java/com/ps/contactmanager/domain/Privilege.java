package com.ps.contactmanager.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
public class Privilege extends CommonEntity {

    @Basic(optional = false)
    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "code", nullable = false, unique = true, length = 50)
    @NotEmpty(message = "Le code ne doit être pas vide")
    @NotBlank(message = "Le code ne doit être pas blanc")
    private String code;

    @Basic(optional = false)
    @NotNull
    @Size(min = 3, max = 255)
    @Column(name = "description", nullable = false)
    @NotEmpty(message = "La description ne doit être pas vide")
    @NotBlank(message = "La description ne doit être pas blanc")
    private String description;

}
