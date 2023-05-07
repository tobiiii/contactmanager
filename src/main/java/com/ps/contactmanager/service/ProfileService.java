package com.ps.contactmanager.service;

import com.ps.contactmanager.domain.DTO.ProfileDto;
import com.ps.contactmanager.domain.Profile;
import com.ps.contactmanager.domain.view.ProfileView;

import java.util.List;

public interface ProfileService {

    Profile create(ProfileDto profile) ;

    Profile update(Long profileId, ProfileDto profile) ;

    List<ProfileView> findAllProfiles();

    ProfileView getDetailsProfile(Long id) ;

    void deleteProfile(Long profile) ;

    Profile findById(Long id) ;

}
