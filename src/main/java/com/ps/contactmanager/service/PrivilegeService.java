package com.ps.contactmanager.service;

import com.ps.contactmanager.domain.Privilege;
import com.ps.contactmanager.domain.view.PrivilegeView;
import com.ps.contactmanager.exceptions.ValidationException;

import java.util.Collection;
import java.util.List;

public interface PrivilegeService {

    List<PrivilegeView> getAllPrivileges();

    Collection<Privilege> getPrivilegesFromIdList(List<Long> privileges);
}
