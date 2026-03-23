package com.kage.dto.request.action;

import com.kage.enums.ActionPlanStatus;
import com.kage.enums.ActivityNature;
import com.kage.enums.TrackingType;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO for {@link com.kage.entity.ActionPlan}
 */
public record ActionPlanCreateRequest(
        LocalDate actionPlanDate,
        String actionPlanName,
        ActionPlanStatus actionPlanStatus,
        ActivityNature actionPlanNature,
        TrackingType actionPlanTrackingType,
        String actionPlanNotes,
        Long activityId,
        Long pillarId,
        List<ActionEntryAttributesRequest> actionPlanAttributes) {
}