package com.ps.contactmanager.controller;

import com.ps.contactmanager.domain.DTO.ChangePasswordDto;
import com.ps.contactmanager.domain.DTO.UserDto;
import com.ps.contactmanager.domain.User;
import com.ps.contactmanager.domain.view.UserView;
import com.ps.contactmanager.exceptions.ValidationException;
import com.ps.contactmanager.service.UserService;
import com.ps.contactmanager.utils.JsonResponse;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;


@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;


    @PreAuthorize("hasAuthority('user.list')")
    @GetMapping(value = "/find_all")
    public JsonResponse findAll(@ParameterObject Pageable pageable) {
        Page<UserView> users = userService.getAllUsers(pageable);
        return JsonResponse.builder().data(users)
                .status(JsonResponse.STATUS.SUCCESS).build();
    }

    @PreAuthorize("hasAuthority('user.update')")
    @PatchMapping(value = "/update/{userId}")
    public JsonResponse update(
            @NotNull(message = "User {REQUIRED}") @PathVariable Long userId,
            @Valid @RequestBody UserDto user) {
        User updatedUser = userService.update(userId, user);
        return JsonResponse.builder().data(new UserView(updatedUser))
                .status(JsonResponse.STATUS.SUCCESS).build();
    }


    @PreAuthorize("hasAuthority('user.update')")
    @PostMapping(value = "/create")
    public JsonResponse create(@Valid @RequestBody UserDto user) {
        UserView updatedUser = userService.create(user);
        return JsonResponse.builder().data(updatedUser)
                .status(JsonResponse.STATUS.SUCCESS).build();
    }

    @PreAuthorize("hasAnyAuthority(" +
            "'user.update'," +
            "'user.detail'," +
            "'user.delete')")
    @GetMapping(value = "/detail/{userId}")
    public JsonResponse detail(
            @NotNull(message = "Utilisateur {REQUIRED}") @PathVariable("userId") Long userId) {
        UserView user = userService.findUserViewById(userId);
        return JsonResponse.builder().data(user)
                .status(JsonResponse.STATUS.SUCCESS).build();
    }

    @PreAuthorize("hasAuthority('user.delete')")
    @DeleteMapping(value = "/delete/{userId}")
    public JsonResponse delete(
            @NotNull(message = "Utilisateur {REQUIRED}") @PathVariable Long userId) {
        userService.delete(userId);
        return JsonResponse.builder().data(null)
                .status(JsonResponse.STATUS.SUCCESS).build();
    }

    @PreAuthorize("isFullyAuthenticated()")
    @PostMapping(value = "/change_password")
    public JsonResponse changePassword(@Valid @RequestBody ChangePasswordDto password) {
        userService.changePassword(password);
        return JsonResponse.builder().data(null)
                .status(JsonResponse.STATUS.SUCCESS).build();
    }


}