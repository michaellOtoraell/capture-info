package com.otorael.Capture_info.Service;

import com.otorael.Capture_info.Model.UserModel;

public interface UserService {

    String registerUser(UserModel userModel);
    String loginUser(UserModel userModel);
}
