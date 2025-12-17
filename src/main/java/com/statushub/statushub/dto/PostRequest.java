package com.statushub.statushub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PostRequest(
        @NotBlank String title,
        @NotBlank String description,
        @NotBlank String type,
        @NotNull Long environmentId,

        // Responsible / created by
        @NotBlank String createdBy
) {}
