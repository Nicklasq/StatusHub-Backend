package com.statushub.statushub.dto;

import jakarta.validation.constraints.NotBlank;

public record CommentRequest(
        @NotBlank String text,
        @NotBlank String author
) {}
