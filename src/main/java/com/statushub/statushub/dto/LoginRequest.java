package com.statushub.statushub.dto;

public record LoginRequest(
        String username,
        String password
) {}
