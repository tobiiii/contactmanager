package com.ps.contactmanager.ServiceImpl;

import com.ps.contactmanager.domain.DTO.AuthenticationRequest;
import com.ps.contactmanager.domain.DTO.AuthenticationResponse;
import com.ps.contactmanager.domain.DTO.UserDto;
import com.ps.contactmanager.domain.Password;
import com.ps.contactmanager.domain.Profile;
import com.ps.contactmanager.domain.User;
import com.ps.contactmanager.domain.enums.ERROR_CODE;
import com.ps.contactmanager.domain.view.UserView;
import com.ps.contactmanager.exceptions.ValidationException;
import com.ps.contactmanager.repository.SecurityCustomizationRepository;
import com.ps.contactmanager.repository.UserRepository;
import com.ps.contactmanager.security.WebSecurityConfig;
import com.ps.contactmanager.service.ProfileService;
import com.ps.contactmanager.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserSrviceImpl implements UserService {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityCustomizationRepository securityCustomizationRepository;

    @Autowired
    private WebSecurityConfig webSecurityConfig;



    @Override
    public Page<UserView> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(UserView::new);
    }

    @Transactional
    @Override
    public UserView create(UserDto user)  {
        User newUser = new User();
        if (userRepository.existsByEmailAddressIgnoreCase(user.getEmailAddress().toLowerCase())) {
            throw new ValidationException(ERROR_CODE.EMAIL_EXISTS);
        }
        String generatedPassword = RandomStringUtils.random(20, true, true);;
        Profile profile = profileService.findById(user.getProfile());
        newUser.setProfile(profile);
        newUser.setLastName(user.getLastName());
        newUser.setFirstName(user.getFirstName());
        newUser.setEmailAddress(user.getEmailAddress());

        newUser =  userRepository.save(newUser);

        Password password = new Password();
        password.setUser(newUser);
        password.setCredential(webSecurityConfig.passwordEncoder().encode(generatedPassword));
        password.setIsTemporary(true);
        password = securityCustomizationRepository.save(password);
        newUser.setPassword(password);

        UserView result = new UserView(userRepository.save(newUser));
        result.setDefaultPass(generatedPassword);
        return result;
    }



    @Override
    public User update(Long userId, UserDto user) throws ValidationException {
        User userToUpdate = findUserById(userId);
        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());
        return userRepository.save(userToUpdate);
    }

    public User findUserById(Long userId) throws ValidationException {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ValidationException(ERROR_CODE.INEXISTANT_USER));
    }

    @Override
    public UserView findUserViewById(Long userId) throws ValidationException {
        return new UserView(findUserById(userId));
    }


    @Override
    public void delete(Long userId) throws ValidationException {
        User userToDelete = findUserById(userId);
        try {
            userRepository.delete(userToDelete);
        } catch (Exception e) {
            throw new ValidationException(ERROR_CODE.REFERENCED_USER);
        }
    }

    @Override
    public AuthenticationResponse authentication(AuthenticationRequest authenticationRequest,
                                                 HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    public void checkPassword(String pwd , User user) throws ValidationException {

        if (!webSecurityConfig.passwordEncoder().matches(pwd, user.getPassword().getCredential())) {
            throw new ValidationException(ERROR_CODE.INCORRECT_PASSWORD);
        } else {
            userRepository.save(user);
        }
    }

    @Override
    public boolean isProfileAttributed(Profile profile) {
        return userRepository.existsByProfile(profile);
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public <E extends User> void savePassword(String password, E user) {
        user.getPassword().setCredential(webSecurityConfig.passwordEncoder().encode(password));
        user.getPassword().setIsTemporary(false);
        securityCustomizationRepository.save(user.getPassword());
    }


}
