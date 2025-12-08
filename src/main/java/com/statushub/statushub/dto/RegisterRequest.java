package com.statushub.statushub.dto;

public record RegisterRequest(
        String username,
        String password,
        String role
) {}
