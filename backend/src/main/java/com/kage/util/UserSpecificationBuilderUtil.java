package com.kage.util;

import com.kage.enums.RecordStatus;
import org.springframework.data.jpa.domain.Specification;


public final class UserSpecificationBuilderUtil {

    private UserSpecificationBuilderUtil() {
    }

    public static <T> Specification<T> build(Long userId) {
        return (root, query, cb) -> cb.and(
                cb.equal(root.get("user").get("id"), userId),
                cb.equal(root.get("status"), RecordStatus.ACTIVE)
        );
    }

}
