package com.kage.util;

import com.kage.common.dto.request.FilterCriteria;
import com.kage.common.dto.request.SearchRequestDto;
import com.kage.enums.FilterOperator;
import com.kage.enums.RecordStatus;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


public final class UserSpecificationBuilderUtil {

    private UserSpecificationBuilderUtil() {}

    public static <T> Specification<T> build(Long userId) {
        return (root, query, cb) -> cb.and(
                cb.equal(root.get("user").get("id"), userId),
                cb.equal(root.get("status"), RecordStatus.ACTIVE)
        );
    };

}
