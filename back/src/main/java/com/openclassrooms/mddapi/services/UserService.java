package com.openclassrooms.mddapi.services;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
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
     * @param identifier The user's email or name.
     * @return A {@link UserDetails} object containing the user's email and
     *         password.
     * @throws UsernameNotFoundException if no user is found.
     */

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        UserModel user = userRepository.findByEmail(identifier)
                .or(() -> userRepository.findByName(identifier))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }
}
