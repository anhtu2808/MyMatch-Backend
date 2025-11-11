package com.mymatch.specification;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.mymatch.dto.request.dashboard.DashboardFilterRequest;
import com.mymatch.entity.*;

public class DashboardSpecification {

    public static Specification<User> userFilter(DashboardFilterRequest filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (filter == null) return cb.and(predicates.toArray(new Predicate[0]));

            if (filter.getStartDate() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("createAt"), filter.getStartDate()));
            }
            if (filter.getEndDate() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("createAt"), filter.getEndDate()));
            }
            if (filter.getUniversityId() != null) {
                predicates.add(cb.equal(
                        root.join("student").join("campus").get("university").get("id"), filter.getUniversityId()));
            }
            if (filter.getCampusId() != null) {
                predicates.add(cb.equal(root.join("student").get("campus").get("id"), filter.getCampusId()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Student> studentFilter(DashboardFilterRequest filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (filter == null) return cb.and(predicates.toArray(new Predicate[0]));

            if (filter.getStartDate() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("createAt"), filter.getStartDate()));
            }
            if (filter.getEndDate() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("createAt"), filter.getEndDate()));
            }
            if (filter.getUniversityId() != null) {
                predicates.add(cb.equal(root.join("campus").get("university").get("id"), filter.getUniversityId()));
            }
            if (filter.getCampusId() != null) {
                predicates.add(cb.equal(root.get("campus").get("id"), filter.getCampusId()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Swap> swapFilter(DashboardFilterRequest filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (filter == null) return cb.and(predicates.toArray(new Predicate[0]));

            if (filter.getStartDate() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("createAt"), filter.getStartDate()));
            }
            if (filter.getEndDate() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("createAt"), filter.getEndDate()));
            }
            if (filter.getUniversityId() != null) {
                predicates.add(cb.or(
                        cb.equal(
                                root.join("studentFrom")
                                        .join("campus")
                                        .get("university")
                                        .get("id"),
                                filter.getUniversityId()),
                        cb.equal(
                                root.join("studentTo")
                                        .join("campus")
                                        .get("university")
                                        .get("id"),
                                filter.getUniversityId())));
            }
            if (filter.getCampusId() != null) {
                predicates.add(cb.or(
                        cb.equal(root.join("studentFrom").get("campus").get("id"), filter.getCampusId()),
                        cb.equal(root.join("studentTo").get("campus").get("id"), filter.getCampusId())));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Transaction> transactionFilter(DashboardFilterRequest filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (filter == null) return cb.and(predicates.toArray(new Predicate[0]));

            if (filter.getStartDate() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("createAt"), filter.getStartDate()));
            }
            if (filter.getEndDate() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("createAt"), filter.getEndDate()));
            }
            if (filter.getUniversityId() != null) {
                predicates.add(cb.equal(
                        root.join("wallet")
                                .join("user")
                                .join("student")
                                .join("campus")
                                .get("university")
                                .get("id"),
                        filter.getUniversityId()));
            }
            if (filter.getCampusId() != null) {
                predicates.add(cb.equal(
                        root.join("wallet")
                                .join("user")
                                .join("student")
                                .get("campus")
                                .get("id"),
                        filter.getCampusId()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Payment> paymentFilter(DashboardFilterRequest filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (filter == null) return cb.and(predicates.toArray(new Predicate[0]));

            if (filter.getStartDate() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("createAt"), filter.getStartDate()));
            }
            if (filter.getEndDate() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("createAt"), filter.getEndDate()));
            }
            if (filter.getUniversityId() != null) {
                predicates.add(cb.equal(
                        root.join("student").join("campus").get("university").get("id"), filter.getUniversityId()));
            }
            if (filter.getCampusId() != null) {
                predicates.add(cb.equal(root.join("student").get("campus").get("id"), filter.getCampusId()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
