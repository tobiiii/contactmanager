package com.ps.contactmanager.ServiceImpl;

import com.ps.contactmanager.domain.Privilege;
import com.ps.contactmanager.domain.enums.ERROR_CODE;
import com.ps.contactmanager.domain.view.PrivilegeView;
import com.ps.contactmanager.exceptions.ValidationException;
import com.ps.contactmanager.repository.PrivilegeRepository;
import com.ps.contactmanager.service.PrivilegeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PrivilegeServiceImpl implements PrivilegeService {

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Override
    public List<PrivilegeView> getAllPrivileges() {
        return privilegeRepository.findAll();
    }

    @Override
    public Collection<Privilege> getPrivilegesFromIdList(List<Long> privileges)
            throws ValidationException {
        Collection<Privilege> result = privilegeRepository.findByIdIn(privileges);
        if (result.size() != privileges.size()) throw new ValidationException(ERROR_CODE.INEXISTANT_PRIVILEGE);
        return result;
    }
}
