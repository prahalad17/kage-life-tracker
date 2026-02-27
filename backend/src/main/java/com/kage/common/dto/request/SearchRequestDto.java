package com.kage.common.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchRequestDto {

    private Integer page = 0;
    private Integer size = 10;
    private List<SortCriteria> sort;
    private List<FilterCriteria> filters;

    public Integer getPage() {
        return page == null ? 0 : page;
    }

    public Integer getSize() {
        return size == null ? 10 : size;
    }

    public List<SortCriteria> getSort() {
        return sort == null ? new ArrayList<>() : sort;
    }

    public List<FilterCriteria> getFilters() {
        return filters == null ? new ArrayList<>() : filters;
    }
}
