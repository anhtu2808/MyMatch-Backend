package com.mymatch.specification;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.mymatch.dto.request.user.UserFilterRequest;
import com.mymatch.entity.User;

public class UserSpecification {

    public static Specification<User> withFilter(UserFilterRequest filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (filter == null) return cb.and(predicates.toArray(new Predicate[0]));

            // Filter by role
            if (filter.getRole() != null) {
                predicates.add(cb.equal(root.get("role").get("name"), filter.getRole()));
            }

            // Filter by deleted status
            if (filter.getDeleted() != null) {
                predicates.add(cb.equal(root.get("deleted"), filter.getDeleted()));
            }

            // Filter by isActive
            if (filter.getIsActive() != null) {
                predicates.add(cb.equal(root.get("isActive"), filter.getIsActive()));
            }

            // Filter by username (exact match)
            if (filter.getUsername() != null && !filter.getUsername().isBlank()) {
                predicates.add(cb.equal(
                        cb.lower(root.get("username")), filter.getUsername().toLowerCase()));
            }

            // Filter by email (exact match)
            if (filter.getEmail() != null && !filter.getEmail().isBlank()) {
                predicates.add(
                        cb.equal(cb.lower(root.get("email")), filter.getEmail().toLowerCase()));
            }

            // Search by username, email, firstName, lastName (like search)
            if (filter.getSearch() != null && !filter.getSearch().isBlank()) {
                String searchPattern = "%" + filter.getSearch().toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("username")), searchPattern),
                        cb.like(cb.lower(root.get("email")), searchPattern),
                        cb.like(cb.lower(root.get("firstName")), searchPattern),
                        cb.like(cb.lower(root.get("lastName")), searchPattern)));
            }

            // Filter by university (through student -> campus -> university)
            if (filter.getUniversityId() != null) {
                predicates.add(cb.equal(
                        root.join("student").join("campus").get("university").get("id"), filter.getUniversityId()));
            }

            // Filter by campus (through student -> campus)
            if (filter.getCampusId() != null) {
                predicates.add(cb.equal(root.join("student").get("campus").get("id"), filter.getCampusId()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
