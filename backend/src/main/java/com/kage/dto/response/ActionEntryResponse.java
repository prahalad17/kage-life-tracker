package com.kage.dto.response;

import com.kage.enums.DayStatus;
import com.kage.enums.Mood;

import java.time.LocalDate;

/**
 * DTO for {@link com.kage.entity.ActionEntry}
 */
public record ActionEntryResponse(Mood mood, LocalDate date, Integer dayScore, DayStatus dayStatus) {

}