package com.kage.service;


import com.kage.common.dto.request.SearchRequestDto;
import com.kage.dto.request.action.ActionEntryCreateRequest;
import com.kage.dto.request.action.ActionEntryUpdateRequest;
import com.kage.dto.response.ActionEntryResponse;
import com.kage.entity.ActionEntry;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

public interface ActionEntryService {

    Page<ActionEntryResponse> getAll(Long id, SearchRequestDto request);

    ActionEntryResponse create(@Valid ActionEntryCreateRequest request, Long userId);

    ActionEntryResponse update(@Valid ActionEntryUpdateRequest request, Long userId);

    ActionEntryResponse getById(Long id, Long userId);

    ActionEntry loadOwnedActionEntry(Long actionEntryId, Long userId);
}

