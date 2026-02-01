package com.kage.dto.response;

import com.kage.enums.ActivityNature;
import com.kage.enums.TrackingType;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO for {@link com.kage.entity.ActivityTemplate}
 */
@Data
public class ActivityTemplateResponse {

    private final String pillar;

    private final String name;
    private final ActivityNature activityNature;
    private final TrackingType defaultTrackingType;
    private final String defaultUnit;
    private final String description;
}