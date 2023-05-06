package com.ps.contactmanager.controller;

import com.ps.contactmanager.domain.DTO.ProfileDto;
import com.ps.contactmanager.domain.Profile;
import com.ps.contactmanager.domain.view.ProfileView;
import com.ps.contactmanager.service.ProfileService;
import com.ps.contactmanager.utils.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api/profile")
@Validated
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PreAuthorize("hasAnyAuthority(" +
                  "'user.list'," +
                  "'user.add'," +
                  "'user.update'," +
                  "'user.detail'," +
                  "'user.delete'," +
                  "'profile.list'," +
                  "'profile.add'," +
                  "'profile.update'," +
                  "'profile.detail'," +
                  "'profile.delete')")
    @GetMapping(value = "/find_all")
    public JsonResponse findAll() {
        List<ProfileView> profiles = profileService.findAllProfiles();
        return JsonResponse.builder().data(profiles).status(JsonResponse.STATUS.SUCCESS).build();
    }

    @PreAuthorize("hasAuthority('profile.add')")
    @PostMapping(value = "/add")
    public JsonResponse add(@Valid @RequestBody final ProfileDto profile)  {
        Profile createdProfile = profileService.create(profile);
        return new JsonResponse(new ProfileView(createdProfile));
    }

    @PreAuthorize("hasAnyAuthority(" +
                  "'profile.detail'," +
                  "'profile.update')")
    @GetMapping(value = "/detail/{profileId}")
    public JsonResponse detail(
            @NotNull(message = "Profil {REQUIRED}") @PathVariable Long profileId)  {
        ProfileView profile = profileService.getDetailsProfile(profileId);
        return new JsonResponse(profile);
    }

    @PreAuthorize("hasAuthority('profile.update')")
    @PutMapping(value = "/update/{profileId}")
    public JsonResponse update(
            @NotNull(message = "Profil {REQUIRED}") @PathVariable Long profileId,
            @Valid @RequestBody ProfileDto profile)  {
        Profile updatedProfile = profileService.update(profileId, profile);
        return new JsonResponse(new ProfileView(updatedProfile));
    }


    @PreAuthorize("hasAuthority('profile.delete')")
    @DeleteMapping(value = "/delete/{profileId}")
    public JsonResponse remove(
            @NotNull(message = "Profil {REQUIRED}") @PathVariable Long profileId)  {
        profileService.deleteProfile(profileId);
        return JsonResponse.builder().status(JsonResponse.STATUS.SUCCESS).build();
    }

}
