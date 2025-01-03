package com.otorael.Capture_info.Service;

import com.otorael.Capture_info.Model.UserModel;

public interface UserService {
    UserModel registerUser(UserModel userModel);
    UserModel loginUser(UserModel userModel);
}