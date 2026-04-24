package com.kage.dto.request.action;

import com.kage.entity.ActionPlan;
import com.kage.enums.ActionEntryStatus;
import com.kage.enums.ActivityNature;
import com.kage.enums.TrackingType;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO for {@link com.kage.entity.ActionEntry}
 */
public record ActionEntryCreateRequest(
        LocalDate actionEntryDate,
        String actionEntryName,
        ActionEntryStatus actionEntryStatus,
        ActivityNature actionEntryNature,
        TrackingType actionEntryTrackingType,
        String actionEntryNotes,
        Long activityId,
        Long pillarId,
        ActionPlan actionPlan,
        List<ActionEntryAttributesRequest> actionEntryAttributes) {
}