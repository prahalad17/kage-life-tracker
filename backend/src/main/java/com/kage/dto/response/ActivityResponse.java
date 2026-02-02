package com.kage.dto.response;

import com.kage.entity.Activity;
import com.kage.enums.ActivityNature;
import com.kage.enums.TrackingType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DTO for {@link Activity}
 */
@AllArgsConstructor
@Getter
public class ActivityResponse {
    private final String pillar;
    private  final Long activityId;
    private final String name;
    private final ActivityNature activityNature;
    private final TrackingType defaultTrackingType;
    private final String defaultUnit;
    private final String description;
}