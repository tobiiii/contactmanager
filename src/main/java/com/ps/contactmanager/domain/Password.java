package com.ps.contactmanager.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;


@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Password extends SecurityCustomization {

    private Boolean isTemporary;
}
