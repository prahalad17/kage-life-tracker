package com.kage.dto.request.day;

import com.kage.entity.DayEntry;
import com.kage.enums.Mood;

/**
 * DTO for {@link DayEntry}
 */
public record DayEntryUpdateRequest(Long dayEntryId, Mood mood) {

}