package com.statushub.statushub.dto;

public record ChangePasswordRequest(
        String username,
        String oldPassword,
        String newPassword
) {}