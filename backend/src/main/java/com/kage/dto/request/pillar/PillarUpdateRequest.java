package com.kage.dto.request.pillar;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
 * DTO for {@link com.kage.entity.Pillar}
 */
public record PillarUpdateRequest(

        @NotNull(message = "pillar Id Should Not be Null")
        Long pillarId,

        Long pillarTemplateId,

        String pillarDescription,

        @Min(1) @Max(10)
        Integer priorityWeight,

        @Min(1) @Max(10)
        Integer orderIndex,

        @Pattern(
                regexp = "^#([A-Fa-f0-9]{6})$",
                message = "Color must be a valid HEX code like #A1B2C3"
        )
        String pillarColor
) {
}
