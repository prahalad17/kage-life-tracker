package com.kage.security;

import java.time.Instant;

public record GeneratedRefreshToken(
        String token,
        Instant expiresAt
) {}
