package com.kage.common.dto.request;

import com.kage.enums.SortDirection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SortCriteria {

    private String field;
    private SortDirection direction = SortDirection.ASC;

    public SortDirection getDirection() {
        return direction == null ? SortDirection.ASC : direction;
    }
}
