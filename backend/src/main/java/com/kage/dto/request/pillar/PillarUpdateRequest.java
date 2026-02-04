package com.kage.dto.request.pillar;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PillarUpdateRequest {

    private Long id;

    private Long pillarTemplateId;

    @Size(max = 255)
    private String description;
}
