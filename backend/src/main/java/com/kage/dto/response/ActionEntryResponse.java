package com.kage.dto.response;

import com.kage.dto.request.action.ActionEntryAttributesRequest;
import com.kage.enums.ActionEntryStatus;
import com.kage.enums.ActivityNature;
import com.kage.enums.TrackingType;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO for {@link com.kage.entity.ActionEntry}
 */
public record ActionEntryResponse(
        Long actionEntryId,
        Long dayEntryId,
        LocalDate actionEntryDate,
        String actionEntryName,
        ActionEntryStatus actionEntryStatus,
        ActivityNature actionEntryNature,
        TrackingType actionEntryTrackingType,
        Long activityId,
        Long pillarId,
        String actionEntryNotes,
        List<ActionEntryAttributesRequest> actionEntryAttributes) {

}