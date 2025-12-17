package com.statushub.statushub.dto;

public record LoginResponse(
        String username,
        String role,
        String token
) {}
