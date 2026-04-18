package com.kage.service;


import com.kage.common.dto.request.SearchRequestDto;
import com.kage.dto.request.day.DayEntryCreateRequest;
import com.kage.dto.request.day.DayEntryUpdateRequest;
import com.kage.dto.response.DayEntryResponse;
import com.kage.entity.DayEntry;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface DayEntryService {

    Page<DayEntryResponse> getAll(Long id, SearchRequestDto request);

    DayEntryResponse create(@Valid DayEntryCreateRequest request, Long userId);

    DayEntryResponse update(@Valid DayEntryUpdateRequest request, Long userId);

    DayEntryResponse getByDate(LocalDate date, Long id);

    DayEntry loadActiveDayEntry(Long userId, Long dayEntryId);

    DayEntry loadActiveDayEntry(Long userId, LocalDate date);
}

