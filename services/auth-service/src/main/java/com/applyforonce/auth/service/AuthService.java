package com.applyforonce.auth.service;

import com.applyforonce.auth.dto.AuthResponse;
import com.applyforonce.auth.dto.RegisterRequest;
import com.applyforonce.auth.exception.EmailAlreadyExistsException;

public interface AuthService {

    AuthResponse register(RegisterRequest registerRequest) throws EmailAlreadyExistsException;
}
