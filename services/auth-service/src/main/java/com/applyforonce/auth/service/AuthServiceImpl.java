package com.applyforonce.auth.service;

import com.applyforonce.auth.dto.AuthResponse;
import com.applyforonce.auth.dto.RegisterRequest;
import com.applyforonce.auth.entity.User;
import com.applyforonce.auth.exception.EmailAlreadyExistsException;
import com.applyforonce.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Locale;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public AuthResponse register(RegisterRequest registerRequest) throws EmailAlreadyExistsException {

        String normalizedEmail = registerRequest.getEmail()
            .trim()
            .toLowerCase(Locale.ROOT);

        log.info("User registration started");

        if(userRepository.existsByEmail(normalizedEmail)){

            log.warn("User registration rejected: email already exists");
            throw new EmailAlreadyExistsException("Email already exists. Please login");
        }

        Instant now = Instant.now();

        User user = new User(
            registerRequest.getFirstName().trim(),
            registerRequest.getLastName().trim(),
            normalizedEmail,
            passwordEncoder.encode(registerRequest.getPassword())
        );

        user.setCreatedAt(now);
        user.setUpdatedAt(now);

        User savedUser = userRepository.save(user);

        log.info(
            "User registration completed successfully. userId={}",
            savedUser.getId()
        );


        return new AuthResponse(
            savedUser.getId(),
            savedUser.getFirstName(),
            savedUser.getLastName(),
            savedUser.getEmail(),
            savedUser.getRole().name(),
            savedUser.getStatus().name()
        );
    }
}
