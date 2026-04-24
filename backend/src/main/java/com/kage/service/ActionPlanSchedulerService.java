package com.kage.service;


import com.kage.dto.response.ActionPlanSchedulerResponse;

import java.time.LocalDate;

public interface ActionPlanSchedulerService {

    ActionPlanSchedulerResponse schedule(LocalDate request, Long userId);


}

