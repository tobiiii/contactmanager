package com.ps.contactmanager.ServiceImpl;

import com.ps.contactmanager.domain.UserSession;
import com.ps.contactmanager.domain.User;
import com.ps.contactmanager.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    //    @Transactional
    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        final Optional<User> userOptional = userRepository.findByEmailAddressIgnoreCase(email);
        // check if email is valid
        userOptional.orElseThrow(() -> new UsernameNotFoundException(""));

        return UserDetailsImpl.buildFromUser(userOptional.get());

    }

    //    @Transactional
    public UserDetails loadUserByUsername(final String email, UserSession userSession)
            throws UsernameNotFoundException {
        final Optional<User> userOptional = userRepository.findByEmailAddressIgnoreCase(email);
        // check if username is valid
        userOptional.orElseThrow(() -> new UsernameNotFoundException(""));

        return UserDetailsImpl.buildFromUser(userOptional.get(), userSession);

    }
}
