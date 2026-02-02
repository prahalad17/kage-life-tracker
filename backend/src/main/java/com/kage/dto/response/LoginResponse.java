package com.kage.dto.response;

import com.kage.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private String accessToken;
    private String name;
    private String email;
    private UserRole userRole;
    private String refreshToken;
}
