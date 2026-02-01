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
public class ActivityCreateRequest {

    private  Long userId;

    private  Long pillarUserId;
    private  Long activityTemplateId;
    private  String name;
    private  ActivityNature nature;
    private  TrackingType trackingType;
    private  String unit;
    private  boolean active;
}