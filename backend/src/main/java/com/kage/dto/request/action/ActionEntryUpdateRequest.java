package com.kage.dto.request.action;

import com.kage.enums.ActionStatus;
import com.kage.enums.ActivityNature;
import com.kage.enums.TrackingType;

import java.util.List;

/**
 * DTO for {@link com.kage.entity.ActionEntry}
 */
public record ActionEntryUpdateRequest(
        Long actionEntryId,
        Long dayEntryId,
        String actionName,
        ActionStatus actionStatus,
        ActivityNature nature,
        TrackingType trackingType,
        Long activityId,
        Long pillarId,
        List<ActionEntryAttributesRequest> attributes) {

}