package com.kage.dto.request.activity;

import com.kage.entity.Activity;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DTO for {@link Activity}
 */
@AllArgsConstructor
@Getter
public class ActivityDailyLogCreateRequest {

    private  Long activityId;
    private  Integer actualValue;
    private  Boolean completed;
    private  String notes;
}