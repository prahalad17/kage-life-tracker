package com.kage.service.impl;

import com.kage.common.dto.request.SearchRequestDto;
import com.kage.dto.request.day.DayEntryCreateRequest;
import com.kage.dto.request.day.DayEntryUpdateRequest;
import com.kage.dto.response.DayEntryResponse;
import com.kage.entity.DayEntry;
import com.kage.entity.User;
import com.kage.enums.DayStatus;
import com.kage.enums.RecordStatus;
import com.kage.exception.BusinessException;
import com.kage.exception.NotFoundException;
import com.kage.mapper.DayEntryMapper;
import com.kage.repository.DayEntryRepository;
import com.kage.service.DayEntryService;
import com.kage.service.UserService;
import com.kage.util.PageableBuilderUtil;
import com.kage.util.SpecificationBuilderUtil;
import com.kage.util.UserSpecificationBuilderUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DayEntryServiceImpl implements DayEntryService {

    private final DayEntryRepository dayEntryRepository;

    private final DayEntryMapper dayEntryMapper;

    private final UserService userService;


    @Override
    public Page<DayEntryResponse> getAll(Long userId, SearchRequestDto request) {

        Pageable pageable = PageableBuilderUtil.build(request);

        Specification<DayEntry> dynamicSpec =
                SpecificationBuilderUtil.build(request);

        Specification<DayEntry> mandatorySpec =
                UserSpecificationBuilderUtil.build(userId);

        Specification<DayEntry> finalSpec =
                mandatorySpec.and(dynamicSpec);

        return dayEntryRepository.findAll(finalSpec, pageable)
                .map(dayEntryMapper::toDto);
    }

    @Override
    public DayEntryResponse create(DayEntryCreateRequest request, Long userId) {

        log.debug("Creating Day Entry for userId={} for date = {}", userId, request.date());

        User user = userService.loadActiveUser(userId);

        if (dayEntryRepository.existsByDateAndUserAndStatus(
                request.date(),
                user,
                RecordStatus.ACTIVE)) {

            throw new BusinessException(
                    "DAY ENTRY ALREADY EXISTS"
            );
        }


        DayEntry dayEntry = DayEntry.create(
                user, request.date(), DayStatus.OPEN
        );

        dayEntryRepository.save(dayEntry);
        return dayEntryMapper.toDto(dayEntry);
    }

    @Override
    @Transactional
    public DayEntryResponse update(DayEntryUpdateRequest request, Long userId) {

        DayEntry dayEntry = loadActiveDayEntry(request.dayEntryId(), userId);

        dayEntry.setMood(request.mood());

        dayEntryRepository.save(dayEntry);

        return dayEntryMapper.toDto(dayEntry);
    }

    @Override
    public DayEntryResponse getByDate(LocalDate date, Long userId) {

        DayEntry dayEntry = dayEntryRepository.findByDateAndUserAndStatus(date, userId, RecordStatus.ACTIVE)
                .orElseThrow(() -> new NotFoundException("Day Entry not found For this Date"));

        return dayEntryMapper.toDto(dayEntry);
    }


    /*
    helper methods
    */

    @Override
    public DayEntry loadActiveDayEntry(Long userId, Long dayEntryId) {
        return dayEntryRepository
                .findByIdAndUserIdAndStatus(
                        dayEntryId,
                        userId,
                        RecordStatus.ACTIVE
                )
                .orElseThrow(() -> new NotFoundException("Day Entry not found"));
    }

    @Override
    public DayEntry loadActiveDayEntry(Long userId, LocalDate date) {
        return dayEntryRepository
                .findByDateAndUserIdAndStatus(
                        date,
                        userId,
                        RecordStatus.ACTIVE
                )
                .orElseThrow(() -> new NotFoundException("Day Entry not found"));
    }
}

