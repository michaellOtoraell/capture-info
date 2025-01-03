package com.otorael.Capture_info.Service.Implementation;

import com.otorael.Capture_info.Model.UserModel;
import com.otorael.Capture_info.Repository.UsersRepository;
import com.otorael.Capture_info.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserImplementation implements UserService {

    @Autowired
    private  PasswordEncoder passwordEncoder;

    private static final Logger log = LoggerFactory.getLogger(UserImplementation.class);

    private final UsersRepository usersRepository;

    public UserImplementation( UsersRepository usersRepository)
    {
        this.usersRepository = usersRepository;
    }

    @Override
    public String registerUser(UserModel userModel) {


        UserModel attemptingUser = usersRepository.findByEmail(userModel.getEmail());

        if (attemptingUser == null || attemptingUser.getEmail().isEmpty()){

            userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));

            usersRepository.save(userModel);

            return "success";

        } else {

            log.error("user email exists");

            return "failure";
        }
    }


    @Override
    public String loginUser(UserModel userModel) {

        UserModel loginAttempt = usersRepository.findByEmail(userModel.getEmail());

        if (loginAttempt != null && passwordEncoder.matches(userModel.getPassword(), loginAttempt.getPassword())){

            return "success";
        }

        else  {
            log.error("password used or did not match");

            return "failure";
        }
    }
}
