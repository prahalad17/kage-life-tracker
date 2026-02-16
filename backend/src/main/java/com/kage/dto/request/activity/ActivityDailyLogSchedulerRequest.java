package com.kage.dto.request.activity;

import com.kage.entity.Activity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

/**
 * DTO for {@link Activity}
 */
@AllArgsConstructor
@Getter
public class ActivityDailyLogSchedulerRequest {

    private LocalDate date;
}