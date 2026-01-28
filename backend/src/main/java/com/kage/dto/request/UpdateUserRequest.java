package com.kage.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {

    @NotBlank(message = "Name is required")
    private String name;

//    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

}
