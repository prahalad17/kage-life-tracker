package com.kage.dto.response;

import com.kage.entity.Activity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

/**
 * DTO for {@link Activity}
 */
@AllArgsConstructor
@Getter
public class ActivityDailyLogResponse {

    private Long activityDailyLogId;
    private Long activityId;
    private String activityName;
    private Integer actualValue;
    private Boolean completed;
    private String notes;
    private LocalDate logDate;
}