package com.ahicode.services;

import com.ahicode.dto.ConfirmRegisterRequest;
import com.ahicode.dto.SignInRequest;
import com.ahicode.dto.SignUpRequest;
import com.ahicode.dto.UserDto;

import java.util.List;

public class AuthServiceImpl implements AuthService {

    @Override
    public String register(SignUpRequest signUpRequest) {
        return "";
    }

    @Override
    public UserDto confirm(ConfirmRegisterRequest confirmRegisterRequest) {
        return null;
    }

    @Override
    public List<Object> login(SignInRequest signInRequest) {
        return List.of();
    }
}
