package com.ps.contactmanager.ServiceImpl;

import com.ps.contactmanager.domain.CommonEntity;
import com.ps.contactmanager.domain.DTO.ProfileDto;
import com.ps.contactmanager.domain.Privilege;
import com.ps.contactmanager.domain.Profile;
import com.ps.contactmanager.domain.enums.ERROR_CODE;
import com.ps.contactmanager.domain.view.ProfileView;
import com.ps.contactmanager.exceptions.ValidationException;
import com.ps.contactmanager.repository.ProfileRepository;
import com.ps.contactmanager.service.PrivilegeService;
import com.ps.contactmanager.service.ProfileService;
import com.ps.contactmanager.service.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    protected ProfileRepository profileRepository;

    @Autowired
    protected UserService userService;

    @Autowired
    protected PrivilegeService privilegeService;


    @Override
    public  void deleteProfile(Long id)  {
        Profile profileToDelete = findById(id);
        if (userService.isProfileAttributed(profileToDelete))
            throw new ValidationException(ERROR_CODE.ATTRIBUTED_PROFILE);
        profileRepository.delete(profileToDelete);
    }

    @SneakyThrows
    @Override
    public  Profile findById(Long id)  {
        return (Profile) profileRepository.findById(id)
                .orElseThrow(() -> new ValidationException(ERROR_CODE.INEXISTANT_PROFILE));
    }


    @Override
    public  List<ProfileView> findAllProfiles() {
        return profileRepository.findAllOrderByIdDesc();
    }

    @Override
    public ProfileView getDetailsProfile(Long id)  {
        Profile profile = findById(id);
        return new ProfileView(profile);
    }

    public  Profile create(ProfileDto profile)  {
        checkProfile(profile);
        Profile createdProfile = new Profile();
        createdProfile.setCode(profile.getCode());
        createdProfile.setName(profile.getName());
        createdProfile.setPrivileges(privilegeService.getPrivilegesFromIdList(profile.getPrivileges()));
        profileRepository.save(createdProfile);
        return createdProfile;
    }

    public  Profile update(Long profileId, ProfileDto profile)  {
        Profile updatedProfile = findById(profileId);
        checkProfile(profile);
        updatedProfile.setCode(profile.getCode());
        updatedProfile.setName(profile.getName());
        updatedProfile.setPrivileges(privilegeService.getPrivilegesFromIdList(profile.getPrivileges()));
        profileRepository.save(updatedProfile);
        return updatedProfile;
    }


    private void checkProfile(ProfileDto profile) throws ValidationException {
        if (profileRepository.existsByName(profile.getName()))
            throw new ValidationException(ERROR_CODE.NAME_EXISTS);

        if (profileRepository.existsByCode(profile.getCode()))
            throw new ValidationException(ERROR_CODE.CODE_EXISTS);

        if (profile.getPrivileges() == null || profile.getPrivileges().isEmpty())
            throw new ValidationException(ERROR_CODE.EMPTY_PRIVILEGES_LIST);
    }


    private boolean isPrivilegesListChanged(List<Long> newList, Collection<Privilege> oldList) {
        List<Long> oldPrivileges = oldList.stream().map(CommonEntity::getId).collect(Collectors.toList());
        return !(new HashSet<>(oldPrivileges).equals(new HashSet<>(newList)));
    }
}
