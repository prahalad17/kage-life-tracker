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
public class ActivityDailyLogCreateRequest {

    private  Long activityId;
    private  Long actualValue;
    private  Boolean completed;
    private  String notes;
}