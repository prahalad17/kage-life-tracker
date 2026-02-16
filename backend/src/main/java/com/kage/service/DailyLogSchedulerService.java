package com.kage.service;


import com.kage.dto.request.activity.ActivityDailyLogCreateRequest;
import com.kage.dto.request.activity.ActivityDailyLogUpdateRequest;
import com.kage.dto.response.ActivityDailyLogResponse;
import com.kage.dto.response.ActivityDailyLogSchedulerResponse;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface DailyLogSchedulerService {

    ActivityDailyLogSchedulerResponse schedule(LocalDate request, Long userId);


}

