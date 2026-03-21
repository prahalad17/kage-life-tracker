package com.kage.dto.request.action;

import java.time.LocalDate;

/**
 * DTO for {@link com.kage.entity.ActionEntryAttribute}
 */
public record ActionEntryAttributesRequest(
        LocalDate date ) {

}