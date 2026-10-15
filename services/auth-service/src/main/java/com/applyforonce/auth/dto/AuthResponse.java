package com.applyforonce.auth.dto;

import java.util.UUID;

public record AuthResponse(
    UUID id,
    String firstName,
    String lastName,
    String email,
    String role,
    String status
) {}
