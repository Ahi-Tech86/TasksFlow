package com.ahicode.services.interfaces;

import com.ahicode.dto.ConfirmationRegisterRequest;
import com.ahicode.dto.SignInRequest;
import com.ahicode.dto.SignUpRequest;
import com.ahicode.dto.UserDto;

import java.util.List;

public interface AuthService {
    String register(SignUpRequest signUpRequest);
    UserDto confirm(ConfirmationRegisterRequest confirmRegisterRequest);
    List<Object> login(SignInRequest signInRequest);
}
