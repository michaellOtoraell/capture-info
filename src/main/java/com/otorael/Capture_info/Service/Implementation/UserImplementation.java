package com.otorael.Capture_info.Service.Implementation;

import com.otorael.Capture_info.Model.UserModel;
import com.otorael.Capture_info.Repository.UsersRepository;
import com.otorael.Capture_info.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Implementation of the UserService interface that handles user registration and authentication.
 * This service manages user operations including registration, login, and password encryption.
 *
 * @author otorael
 * @version 1.0
 * @since 2024-01-03
 */
@Service
public class UserImplementation implements UserService {

    /**
     * Password encoder for secure password hashing.
     * Autowired from Spring Security configuration.
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Logger instance for this class.
     * Used for logging service operations and errors.
     */
    private static final Logger log = LoggerFactory.getLogger(UserImplementation.class);

    /**
     * Repository for user data operations.
     * Handles all database interactions for user entities.
     */
    private final UsersRepository usersRepository;

    /**
     * Constructor for UserImplementation.
     * Initializes the user repository and logs the service creation.
     *
     * @param usersRepository repository for user data operations
     */
    public UserImplementation(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
        log.info("UserImplementation service initialized");
    }

    /**
     * Registers a new user in the system.
     * Checks for existing email, encodes password, and saves user data.
     *
     * @param userModel the user data to register
     * @return UserModel the registered user if successful, null if email exists
     */
    @Override
    public UserModel registerUser(UserModel userModel) {
        /* Log the start of registration process */
        log.info("Processing registration request for email: {}", userModel.getEmail());

        /* Check if user already exists in the database */
        UserModel attemptingUser = usersRepository.findByEmail(userModel.getEmail());

        /* Process registration if email is not already taken */
        if (attemptingUser == null || attemptingUser.getEmail().isEmpty()) {
            /* Encode the password before saving to database */
            userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));

            /* Save the new user and get the saved entity */
            UserModel savedUser = usersRepository.save(userModel);
            log.info("User successfully registered - Email: {}", savedUser.getEmail());

            /* Return the newly created user */
            return savedUser;
        } else {
            /* Log warning if email already exists */
            log.warn("Registration failed - Email already exists: {}", userModel.getEmail());
            return null;
        }
    }

    /**
     * Authenticates a user trying to log in.
     * Verifies email exists and password matches.
     *
     * @param userModel the login credentials
     * @return UserModel the authenticated user if successful, null if authentication fails
     */
    @Override
    public UserModel loginUser(UserModel userModel) {
        /* Log the start of login process */
        log.info("Processing login request for email: {}", userModel.getEmail());

        /* Retrieve user from database by email */
        UserModel loginAttempt = usersRepository.findByEmail(userModel.getEmail());

        /* Verify user exists and password matches */
        if (loginAttempt != null && passwordEncoder.matches(userModel.getPassword(), loginAttempt.getPassword())) {
            /* Log successful login */
            log.info("User successfully logged in - Email: {}", loginAttempt.getEmail());
            return loginAttempt;
        } else {
            /* Log failed login attempt */
            log.warn("Login failed - Invalid credentials for email: {}", userModel.getEmail());
            return null;
        }
    }
}