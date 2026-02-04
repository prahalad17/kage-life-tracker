package com.kage.dto.request.pillar;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PillarTemplateUpdateRequest {

    @NotBlank
    private Long id;

    @NotBlank
    @Size(min = 3, max = 100)
    private String name;

    @Size(max = 255)
    private String description;
}
