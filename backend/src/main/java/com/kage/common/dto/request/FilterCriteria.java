package com.kage.common.dto.request;

import com.kage.enums.FilterOperator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilterCriteria {

    private String field;
    private FilterOperator operator;
    private Object value;
}
