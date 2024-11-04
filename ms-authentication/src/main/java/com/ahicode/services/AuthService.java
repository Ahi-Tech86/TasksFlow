package com.ahicode.services;

import com.ahicode.dto.ConfirmRegisterRequest;
import com.ahicode.dto.SignInRequest;
import com.ahicode.dto.SignUpRequest;
import com.ahicode.dto.UserDto;

import java.util.List;

public interface AuthService {
    String register(SignUpRequest signUpRequest);
    UserDto confirm(ConfirmRegisterRequest confirmRegisterRequest);
    List<Object> login(SignInRequest signInRequest);
}
