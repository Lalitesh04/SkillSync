package com.klef.jfsd.userservice.service;

import com.klef.jfsd.userservice.dto.LoginRequest;
import com.klef.jfsd.userservice.dto.ProfileRequest;
import com.klef.jfsd.userservice.dto.UserRegister;

import java.util.Map;

public interface UserService {

    public String checkLogin(LoginRequest loginRequest);
    public boolean isEmailExist(String email);
    public String userRegister(UserRegister userRegister);
    public String profileCreation(ProfileRequest profileRequest, String email);
    public String changePassword(String email, String password);
    public boolean isLogin(String email);
    public String sendVerificationLink(String email);

}
