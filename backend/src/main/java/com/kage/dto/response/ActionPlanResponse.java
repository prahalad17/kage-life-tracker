package com.kage.dto.response;

import com.kage.enums.DayStatus;
import com.kage.enums.Mood;

import java.time.LocalDate;

/**
 * DTO for {@link com.kage.entity.ActionPlan}
 */
public record ActionPlanResponse(Mood mood, LocalDate date, Integer dayScore, DayStatus dayStatus) {

}