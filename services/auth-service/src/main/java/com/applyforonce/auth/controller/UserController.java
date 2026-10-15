package com.applyforonce.auth.controller;

import com.applyforonce.auth.dto.AuthResponse;
import com.applyforonce.auth.dto.RegisterRequest;

import com.applyforonce.auth.exception.EmailAlreadyExistsException;
import com.applyforonce.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser(@Valid @RequestBody RegisterRequest request) throws EmailAlreadyExistsException {

        AuthResponse authResponse = authService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
    }

}
