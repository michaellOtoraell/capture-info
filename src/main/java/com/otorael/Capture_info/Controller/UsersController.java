/**
 * Controller class responsible for handling user-related HTTP requests in the application.
 * Provides endpoints for user registration and management.
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

@RestController
@RequestMapping("/api/v1/")
public class UsersController {

    private static final Logger log = LoggerFactory.getLogger(UsersController.class);

    private final UserService userService;
    private final JwtUtility jwtUtility;

    /**
     * Constructor for UsersController.
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
     * @return ResponseEntity<?> containing either:
     *         - UserInfoDTO with status 201 (CREATED) if registration is successful
     *         - MessageDTO with status 409 (CONFLICT) if email is already taken
     * @throws RuntimeException if an unexpected error occurs during registration
     */
    @RequestMapping(value = "/public/register", method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody UserModel userModel) {
        String userEmail = userModel.getEmail();
        log.info("Starting registration process for user - Email: {}", userEmail);

        try {
            String response = userService.registerUser(userModel);
            log.debug("Registration service response for email {}: {}", userEmail, response);

            if ("success".equals(response)) {
                String token = jwtUtility.TokenGeneration(userEmail);
                log.debug("JWT token generated for user: {}", userEmail);

                UserInfoDTO responseDto = new UserInfoDTO(
                        "success",
                        userModel.getFirstName(),
                        userModel.getLastName(),
                        userEmail,
                        token,
                        "The User was registered successfully"
                );

                log.info("User registration successful - Email: {}, Name: {} {}",
                        userEmail,
                        userModel.getFirstName(),
                        userModel.getLastName()
                );
                return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);

            } else if ("failure".equals(response)) {
                log.warn("Registration failed - Duplicate email detected: {}", userEmail);

                MessageDTO responseDto = new MessageDTO(
                        "failure",
                        "Email is taken"
                );
                return ResponseEntity.status(HttpStatus.CONFLICT).body(responseDto);
            }

        } catch (Exception e) {
            log.error("Registration process failed for email: {} - Error type: {} - Message: {}",
                    userEmail,
                    e.getClass().getSimpleName(),
                    e.getMessage(),
                    e
            );
            throw new RuntimeException(e);
        }

        log.error("Registration failed - Unexpected service response for email: {}", userEmail);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new MessageDTO("error", "An unexpected error occurred"));
    }

    /**
     * Handles user login requests.
     * Authenticates user credentials and generates a JWT token upon successful login.
     *
     * @param userModel inputs the whole user attempt payload to test for its validity for login service
     * @return ResponseEntity<?> containing either:
     *         - UserInfoDTO with status 200 (OK) if login is successful
     *         - MessageDTO with status 409 (CONFLICT) if invalid credentials were provided
     * @throws RuntimeException if an unexpected error occurs during login
     */
    @RequestMapping(value = "/public/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody UserModel userModel) {
        String userEmail = userModel.getEmail();
        log.info("Login attempt initiated for user - Email: {}", userEmail);

        try {
            String response = userService.loginUser(userModel);
            log.debug("Login service response for email {}: {}", userEmail, response);

            if ("success".equals(response)) {
                String token = jwtUtility.TokenGeneration(userEmail);
                log.debug("JWT token generated for login - User: {}", userEmail);

                UserInfoDTO responseDto = new UserInfoDTO(
                        "success",
                        userModel.getFirstName(),
                        userModel.getLastName(),
                        userEmail,
                        token,
                        "User logged in successfully"
                );

                log.info("User login successful - Email: {}", userEmail);
                return ResponseEntity.status(HttpStatus.OK).body(responseDto);

            } else if ("failure".equals(response)) {
                log.warn("Login failed - Invalid credentials for user: {}", userEmail);

                MessageDTO responseDto = new MessageDTO(
                        "failure",
                        "Invalid credentials, try again"
                );
                return ResponseEntity.status(HttpStatus.CONFLICT).body(responseDto);
            }

        } catch (Exception e) {
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

        log.error("Login failed - Unexpected service response for email: {}", userEmail);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new MessageDTO("error", "An unexpected error occurred"));
    }
}