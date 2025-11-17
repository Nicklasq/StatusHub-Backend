package com.statushub.statushub.dto;

import jakarta.validation.constraints.NotBlank;

public record EnvironmentRequest(
        @NotBlank String name,
        @NotBlank String status
) {}
