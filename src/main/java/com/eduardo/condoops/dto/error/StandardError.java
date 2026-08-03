package com.eduardo.condoops.dto.error;

import lombok.Builder;

import java.time.Instant;

@Builder
public record StandardError(
        Instant timestamp,
        int status,
        String error,
        String message,
        String path
) {
}
