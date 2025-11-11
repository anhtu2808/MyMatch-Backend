package com.mymatch.specification;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.mymatch.dto.request.material.MaterialFilter;
import com.mymatch.entity.Material;
import com.mymatch.utils.SecurityUtil;

public class MaterialSpecification {

    public static Specification<Material> withFilter(MaterialFilter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(filter.getName())) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")),
                        "%" + filter.getName().toLowerCase() + "%"));
            }

            // Filter theo description (like)
            if (StringUtils.hasText(filter.getDescription())) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("description")),
                        "%" + filter.getDescription().toLowerCase() + "%"));
            }

            // Filter theo lecturerId
            if (filter.getLecturerId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("lecturer").get("id"), filter.getLecturerId()));
            }

            // Filter theo ownerId
            if (filter.getOwnerId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("owner").get("id"), filter.getOwnerId()));
            }

            // Filter theo courseId
            if (filter.getCourseId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("course").get("id"), filter.getCourseId()));
            }
            Long currentUserId = SecurityUtil.getCurrentUserId();

            if (filter.getIsPurchased() != null) {
                // Join với bảng MaterialPurchase
                Join<Object, Object> purchases = root.join("purchases", JoinType.LEFT);
                Predicate purchasedPredicate =
                        criteriaBuilder.equal(purchases.get("buyer").get("id"), currentUserId);

                if (filter.getIsPurchased()) {
                    predicates.add(purchasedPredicate);
                } else {
                    // not purchased: material mà người dùng chưa mua
                    predicates.add(criteriaBuilder.not(purchasedPredicate));
                }
                // tránh trùng dữ liệu khi join
                query.distinct(true);
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
