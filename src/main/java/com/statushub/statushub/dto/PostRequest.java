package com.statushub.statushub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PostRequest(
        @NotNull Long environmentId,
        @NotBlank String title,
        @NotBlank String description,
        @NotBlank String type
) {}
