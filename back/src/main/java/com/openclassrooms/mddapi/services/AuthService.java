package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.configuration.JwtUtil;
import com.openclassrooms.mddapi.dto.TokenResponse;
import com.openclassrooms.mddapi.dto.UserDTO;
import com.openclassrooms.mddapi.models.UserModel;
import com.openclassrooms.mddapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Authenticates a user and generates a JWT token.
     * 
     * <p>
     * This method attempts to authenticate a user by either their email or name and
     * then generates a JWT token for the user.
     * </p>
     * 
     * @param userDTO The user's credentials, including email or name and password.
     * @return A {@link TokenResponse} containing the generated JWT token.
     * @throws RuntimeException if the user is not found or authentication fails.
     */

    public TokenResponse login(UserDTO userDTO) {
        UserModel user = userRepository.findByEmail(userDTO.getEmail())
                .or(() -> userRepository.findByName(userDTO.getName()))
                .orElseThrow(() -> new RuntimeException("User not found"));
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), userDTO.getPassword()));
        String token = jwtUtil.generateToken(user.getEmail());
        return new TokenResponse(token);
    }

    /**
     * Registers a new user and generates a JWT token.
     * 
     * <p>
     * This method checks if the email already exists, and if not, creates a new
     * user,
     * saves it to the repository, and generates a JWT token for the new user.
     * </p>
     * 
     * @param userDTO The user's registration details, including name, email, and
     *                password.
     * @return A {@link TokenResponse} containing the generated JWT token.
     * @throws RuntimeException if the email already exists.
     */

    public TokenResponse register(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        UserModel user = new UserModel();
        user.setName(userDTO.getName());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEmail(userDTO.getEmail());
        userRepository.save(user);
        String token = jwtUtil.generateToken(userDTO.getEmail());
        return new TokenResponse(token);
    }

    /**
     * Updates the user's details and generates a new JWT token.
     * 
     * <p>
     * This method updates the user's name, email, and optionally their password.
     * After updating, it generates a new JWT token for the updated user.
     * </p>
     * 
     * @param userEmail The email of the user to be updated.
     * @param userDTO   The updated user details, including name, email, and
     *                  optionally password.
     * @return A {@link TokenResponse} containing the generated JWT token for the
     *         updated user.
     * @throws RuntimeException if the user with the given email is not found.
     */

    public TokenResponse updateUser(String userEmail, UserDTO userDTO) {
        UserModel user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        userRepository.save(user);
        String token = jwtUtil.generateToken(userDTO.getEmail());
        return new TokenResponse(token);
    }

    /**
     * Retrieves the profile information of a user by email.
     * 
     * <p>
     * This method fetches the user's name and email and returns it in a
     * {@link UserDTO}.
     * </p>
     * 
     * @param email The email of the user whose profile is being retrieved.
     * @return A {@link UserDTO} containing the user's name and email.
     * @throws RuntimeException if the user with the given email is not found.
     */
    public UserDTO getUserProfile(String email) {
        UserModel user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }
}
