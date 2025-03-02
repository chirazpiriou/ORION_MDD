package com.openclassrooms.mddapi.services;

import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.models.UserModel;
import com.openclassrooms.mddapi.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {

        UserModel user = userRepository.findByEmail(identifier)
                .orElseGet(() -> userRepository.findByName(identifier)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found.")));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.emptyList());
    }

}
