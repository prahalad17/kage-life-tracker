package com.kage.dto.request.activity;

import com.kage.entity.Activity;
import com.kage.enums.ActivityNature;
import com.kage.enums.ActivityType;
import com.kage.enums.ScheduleType;
import com.kage.enums.TrackingType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO for {@link Activity}
 */
public record ActivityUpdateRequest(

        @NotNull Long activityId,
        @NotBlank String activityName,
        @NotNull ActivityType activityType,
        @NotNull ActivityNature activityNature,
        @NotNull TrackingType activityTrackingType,
        String activityDescription,
        ScheduleType activityScheduleType,
        Long pillarId) {
}