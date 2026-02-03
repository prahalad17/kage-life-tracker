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
public class ActivityDailyLogResponse {

    private  Long activityDailyLogId;
    private  Long activityId;
    private  String activityName;
    private  Integer actualValue;
    private  Boolean completed;
    private  String notes;
}