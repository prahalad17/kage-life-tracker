package com.kage.util;

import com.kage.common.dto.request.SearchRequestDto;
import com.kage.common.dto.request.SortCriteria;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;


public final class PageableBuilderUtil {
    private PageableBuilderUtil() {}


    public static Pageable build(SearchRequestDto request) {

        List<Sort.Order> orders = new ArrayList<>();

        for (SortCriteria sort : request.getSort()) {
            Sort.Direction direction =
                    Sort.Direction.fromString(sort.getDirection().name());

            orders.add(new Sort.Order(direction, sort.getField()));
        }

        Sort sort = orders.isEmpty()
                ? Sort.unsorted()
                : Sort.by(orders);

        return PageRequest.of(
                request.getPage(),
                request.getSize(),
                sort
        );
    }
}
