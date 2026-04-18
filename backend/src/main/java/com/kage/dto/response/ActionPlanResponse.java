package com.kage.dto.response;

import com.kage.dto.request.action.ActionEntryAttributesRequest;
import com.kage.enums.*;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO for {@link com.kage.entity.ActionPlan}
 */
public record ActionPlanResponse(
        Long actionPlanId,
        LocalDate actionPlanDate,
        String actionPlanName,
        ActionPlanStatus actionPlanStatus,
        ActivityNature actionPlanNature,
        TrackingType actionPlanTrackingType,
        Long activityId,
        Long pillarId,
        String actionPlanNotes,
        List<ActionEntryAttributesRequest> actionPlanAttributes) {

}