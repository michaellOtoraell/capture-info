/**
 * Controller class responsible for handling user-related HTTP requests in the application.
 * Provides endpoints for user registration and authentication management.
 *
 * @author otorael
 * @version 1.0
 * @since 2024-12-19
 */
package com.otorael.Capture_info.Controller;

import com.otorael.Capture_info.Authentication.JwtUtility;
import com.otorael.Capture_info.Model.UserModel;
import com.otorael.Capture_info.ResponseDTO.MessageDTO;
import com.otorael.Capture_info.ResponseDTO.UserInfoDTO;
import com.otorael.Capture_info.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing user operations.
 * Handles registration and login endpoints under the /api/v1/ base path.
 */
@RestController
@RequestMapping("/api/v1/")
public class UsersController {

    /**
     * Logger instance for this class.
     * Used for tracking controller operations and error handling.
     */
    private static final Logger log = LoggerFactory.getLogger(UsersController.class);

    /**
     * Service layer for handling user business logic.
     */
    private final UserService userService;

    /**
     * Utility for JWT token generation and management.
     */
    private final JwtUtility jwtUtility;

    /**
     * Constructor for UsersController.
     * Initializes required services and utilities.
     *
     * @param userService Service for handling user operations
     * @param jwtUtility Utility for JWT token operations
     */
    public UsersController(UserService userService, JwtUtility jwtUtility) {
        this.userService = userService;
        this.jwtUtility = jwtUtility;
        log.info("UsersController initialized with userService and jwtUtility");
    }

    /**
     * Handles user registration requests.
     * Creates a new user account and generates a JWT token upon successful registration.
     *
     * @param userModel The user data model containing registration information
     * @return ResponseEntity<?> with either:
     *         - UserInfoDTO with status 201 (CREATED) if registration is successful
     *         - MessageDTO with status 409 (CONFLICT) if email is already taken
     *         - MessageDTO with status 500 (INTERNAL_SERVER_ERROR) if an unexpected error occurs
     *
     */
    @RequestMapping(value = "/public/register", method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody UserModel userModel) {
        String userEmail = userModel.getEmail();
        log.info("Starting registration process for user - Email: {}", userEmail);

        try {
            /* Attempt to register the user */
            UserModel registeredUser = userService.registerUser(userModel);

            if (registeredUser != null) {
                /* Generate JWT token for successful registration */
                String token = jwtUtility.TokenGeneration(registeredUser.getEmail());
                log.debug("JWT token generated for new user: {}", userEmail);

                /* Create success response */
                UserInfoDTO responseDto = new UserInfoDTO(
                        "success",
                        registeredUser.getFirstName(),
                        registeredUser.getLastName(),
                        registeredUser.getEmail(),
                        token,
                        "The User was registered successfully"
                );

                log.info("User registration successful - Email: {}, Name: {} {}",
                        userEmail,
                        registeredUser.getFirstName(),
                        registeredUser.getLastName()
                );
                return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);

            } else {
                /* Handle case where email is already taken */
                log.warn("Registration failed - Duplicate email detected: {}", userEmail);

                MessageDTO responseDto = new MessageDTO(
                        "failure",
                        "Email is taken"
                );
                return ResponseEntity.status(HttpStatus.CONFLICT).body(responseDto);
            }

        } catch (Exception e) {
            /* Handle unexpected errors during registration */
            log.error("Registration process failed for email: {} - Error type: {} - Message: {}",
                    userEmail,
                    e.getClass().getSimpleName(),
                    e.getMessage(),
                    e
            );
            throw new RuntimeException(e);
        }
    }

    /**
     * Handles user login requests.
     * Authenticates user credentials and generates a JWT token upon successful login.
     *
     * @param userModel The user credentials for authentication
     * @return ResponseEntity<?> with either:
     *         - UserInfoDTO with status 200 (OK) if login is successful
     *         - MessageDTO with status 409 (CONFLICT) if credentials are invalid
     *         - MessageDTO with status 500 (INTERNAL_SERVER_ERROR) if an unexpected error occurs
     */
    @RequestMapping(value = "/public/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody UserModel userModel) {
        String userEmail = userModel.getEmail();
        log.info("Login attempt initiated for user - Email: {}", userEmail);

        try {
            /* Attempt to authenticate the user */
            UserModel authenticatedUser = userService.loginUser(userModel);

            if (authenticatedUser != null) {
                /* Generate JWT token for successful login */
                String token = jwtUtility.TokenGeneration(authenticatedUser.getEmail());
                log.debug("JWT token generated for login - User: {}", userEmail);

                /* Create success response */
                UserInfoDTO responseDto = new UserInfoDTO(
                        "success",
                        authenticatedUser.getFirstName(),
                        authenticatedUser.getLastName(),
                        authenticatedUser.getEmail(),
                        token,
                        "User logged in successfully"
                );

                log.info("User login successful - Email: {}", userEmail);
                return ResponseEntity.status(HttpStatus.OK).body(responseDto);

            } else {
                /* Handle invalid credentials */
                log.warn("Login failed - Invalid credentials for user: {}", userEmail);

                MessageDTO responseDto = new MessageDTO(
                        "failure",
                        "Invalid credentials, try again"
                );
                return ResponseEntity.status(HttpStatus.CONFLICT).body(responseDto);
            }

        } catch (Exception e) {
            /* Handle unexpected errors during login */
            log.error("Login process failed for email: {} - Error type: {} - Message: {}",
                    userEmail,
                    e.getClass().getSimpleName(),
                    e.getMessage(),
                    e
            );

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageDTO(
                            "failure",
                            "Executed with exception " + e.getMessage()
                    ));
        }
    }
}