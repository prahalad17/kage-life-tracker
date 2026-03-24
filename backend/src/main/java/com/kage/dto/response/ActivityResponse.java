package com.kage.dto.response;

import com.kage.entity.Activity;
import com.kage.enums.ActivityNature;
import com.kage.enums.ActivityType;
import com.kage.enums.ScheduleType;
import com.kage.enums.TrackingType;

/**
 * DTO for {@link Activity}
 */
public record ActivityResponse(
        Long activityId,
        String activityName,
        ActivityType activityType,
        ActivityNature activityNature,
        TrackingType activityTrackingType,
        String activityDescription,
        ScheduleType activityScheduleType,
        Long pillarId) {
}