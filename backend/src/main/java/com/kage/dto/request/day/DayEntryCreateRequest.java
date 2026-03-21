package com.kage.dto.request.day;

import com.kage.entity.DayEntry;

import java.time.LocalDate;

/**
 * DTO for {@link DayEntry}
 */
public record DayEntryCreateRequest(
        LocalDate date) {

}