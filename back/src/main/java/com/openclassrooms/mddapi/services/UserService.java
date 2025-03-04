package com.openclassrooms.mddapi.services;

import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.models.UserModel;
import com.openclassrooms.mddapi.repositories.UserRepository;

/**
 * Service class for handling user authentication and loading user details.
 */
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Loads a user by their email or name.
     *
     * @param identifier The user's email or username.
     * @return UserDetails containing the user's authentication information.
     * @throws UsernameNotFoundException if the user is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        
        // Attempt to find the user by email, if not found, try by username
        UserModel user = userRepository.findByEmail(identifier)
                .orElseGet(() -> userRepository.findByName(identifier)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found.")));
        
        // Return a Spring Security UserDetails object with email, password, and an empty list of authorities
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.emptyList()
        );
    }
}
