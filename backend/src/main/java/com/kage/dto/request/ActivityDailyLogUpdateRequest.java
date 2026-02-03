package com.kage.dto.request;

import com.kage.entity.Activity;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DTO for {@link Activity}
 */
@AllArgsConstructor
@Getter
public class ActivityDailyLogUpdateRequest {

    private  Long activityId;
    private  Long actualValue;
    private  Boolean completed;
    private  String notes;
}