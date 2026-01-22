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
public class ActivityResponse {
    private final Long userId;
    private final Long pillarUserId;
    private final Long activityTemplateId;
    private final String name;
    private final ActivityNature nature;
    private final TrackingType trackingType;
    private final String unit;
    private final boolean active;
}