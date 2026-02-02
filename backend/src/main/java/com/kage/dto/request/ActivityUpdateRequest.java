package com.kage.dto.request;

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
public class ActivityUpdateRequest {
    private  String pillar;
    private  Long pillarId;
    private Long activityId;
    private  String name;
    private  ActivityNature activityNature;
    private  TrackingType defaultTrackingType;
    private  String defaultUnit;
    private  String description;
}