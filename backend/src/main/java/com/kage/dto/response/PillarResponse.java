package com.kage.dto.response;

/**
 * DTO for {@link com.kage.entity.Pillar}
 */

public record PillarResponse(

        Long pillarId,

        Long pillarTemplateId,

        String pillarName,

        String pillarDescription,

        Integer priorityWeight,

        Integer orderIndex,

        String pillarColor

) {

}
