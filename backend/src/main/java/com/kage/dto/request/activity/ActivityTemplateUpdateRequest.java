package com.kage.dto.request.activity;

import com.kage.enums.ActivityNature;
import com.kage.enums.TrackingType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DTO for {@link com.kage.entity.ActivityTemplate}
 */
@AllArgsConstructor
@Getter
public class ActivityTemplateUpdateRequest {
    private String pillar;
    private Long pillarTemplateId;
    private Long activityId;
    private String name;
    private ActivityNature activityNature;
    private TrackingType defaultTrackingType;
    private String defaultUnit;
    private String description;
}