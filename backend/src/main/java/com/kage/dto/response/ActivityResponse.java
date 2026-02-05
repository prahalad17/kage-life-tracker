package com.kage.dto.response;

import com.kage.entity.Activity;
import com.kage.enums.ActivityNature;
import com.kage.enums.ScheduleType;
import com.kage.enums.TrackingType;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO for {@link Activity}
 */
@AllArgsConstructor
@Data
public class ActivityResponse {

    private  final Long activityId;
    private final String activityName;

    private  final Long pillarId;
    private final String pillarName;

    private final ActivityNature nature;
    private final TrackingType trackingType;
    private final String unit;
    private final String description;

    private final ScheduleType scheduleType;

}