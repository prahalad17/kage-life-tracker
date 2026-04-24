package com.kage.service;


import com.kage.common.dto.request.SearchRequestDto;
import com.kage.dto.request.action.ActionPlanCompleteRequest;
import com.kage.dto.request.action.ActionPlanCreateRequest;
import com.kage.dto.request.action.ActionPlanUpdateRequest;
import com.kage.dto.response.ActionPlanResponse;
import com.kage.entity.ActionPlan;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

public interface ActionPlanService {

    Page<ActionPlanResponse> getAll(Long id, SearchRequestDto request);

    ActionPlanResponse create(@Valid ActionPlanCreateRequest request, Long userId);

    ActionPlanResponse update(@Valid ActionPlanUpdateRequest request, Long userId);

    ActionPlanResponse completeActionPlan(ActionPlanCompleteRequest request, Long userId);

    ActionPlanResponse getById(Long id, Long userId);

    ActionPlan loadOwnedActionPlan(Long actionPlanId, Long userId);
}

