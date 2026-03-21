package com.kage.dto.response;

import com.kage.entity.DayEntry;
import com.kage.enums.DayStatus;
import com.kage.enums.Mood;

import java.time.LocalDate;

/**
 * DTO for {@link DayEntry}
 */
public record DayEntryResponse(Mood mood, LocalDate date, Integer dayScore, DayStatus dayStatus) {

}