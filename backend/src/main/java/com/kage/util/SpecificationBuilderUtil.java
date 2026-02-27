package com.kage.util;

import com.kage.common.dto.request.FilterCriteria;
import com.kage.common.dto.request.SearchRequestDto;
import com.kage.enums.FilterOperator;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;


public final class SpecificationBuilderUtil {

    private SpecificationBuilderUtil() {}

    public static <T> Specification<T> build(SearchRequestDto request) {

        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            for (FilterCriteria filter : request.getFilters()) {

                String field = filter.getField();
                Object value = filter.getValue();
                FilterOperator operator = filter.getOperator();

                Path<?> path = root.get(field);

                switch (operator) {

                    case EQUALS ->
                            predicates.add(criteriaBuilder.equal(path, value));

                    case NOT_EQUALS ->
                            predicates.add(criteriaBuilder.notEqual(path, value));

                    case GREATER_THAN ->
                            predicates.add(criteriaBuilder.greaterThan(
                                    path.as(Comparable.class),
                                    (Comparable) value
                            ));

                    case LESS_THAN ->
                            predicates.add(criteriaBuilder.lessThan(
                                    path.as(Comparable.class),
                                    (Comparable) value
                            ));

                    case LIKE ->
                            predicates.add(criteriaBuilder.like(
                                    criteriaBuilder.lower(path.as(String.class)),
                                    "%" + value.toString().toLowerCase() + "%"
                            ));

                    case IN ->
                            predicates.add(path.in((List<?>) value));

                    case BETWEEN -> {
                        if (!(value instanceof List<?> values) || values.size() != 2) {
                            throw new IllegalArgumentException("BETWEEN requires exactly 2 values");
                        }

                        predicates.add(criteriaBuilder.between(
                                path.as(Comparable.class),
                                (Comparable) values.get(0),
                                (Comparable) values.get(1)
                        ));
                    }

                    default ->
                            throw new IllegalArgumentException("Unsupported operator");
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
