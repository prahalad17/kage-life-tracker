package com.kage.dto.response;

import com.kage.enums.UserRole;
import lombok.Data;

@Data

public class RefreshTokenResponse {

    private String accessToken;
    private String name;
    private String email;
    private UserRole userRole;
    private String refreshToken;
}
