package com.kage.dto.request.activity;

import com.kage.entity.Activity;
import com.kage.enums.ActivityNature;
import com.kage.enums.ScheduleType;
import com.kage.enums.TrackingType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.DayOfWeek;
import java.util.Set;

/**
 * DTO for {@link Activity}
 */
@AllArgsConstructor
@Getter
public class ActivityCreateRequest {

    private  Long pillarId;
    private  String name;
    private  ActivityNature nature;
    private  TrackingType trackingType;
    private  String unit;
    private  String description;
    private ScheduleType scheduleType;

    private Set<DayOfWeek> days;
}