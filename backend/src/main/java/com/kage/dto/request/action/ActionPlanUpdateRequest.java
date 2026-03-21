package com.kage.dto.request.action;

import com.kage.enums.ActionPlanStatus;
import com.kage.enums.ActivityNature;
import com.kage.enums.TrackingType;

import java.util.List;

/**
 * DTO for {@link com.kage.entity.ActionPlan}
 */
public record ActionPlanUpdateRequest(
        Long actionPlanId,
        String actionName,
        ActionPlanStatus actionStatus,
        ActivityNature nature,
        TrackingType trackingType,
        Long activityId,
        Long pillarId,
        List<ActionEntryAttributesRequest> attributes) {

}