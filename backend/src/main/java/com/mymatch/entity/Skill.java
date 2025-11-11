package com.mymatch.entity;

import jakarta.persistence.*;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.mymatch.common.AbstractAuditingEntity;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@SQLDelete(sql = "UPDATE skill SET deleted = 1 WHERE id = ?")
@SQLRestriction("deleted = 0")
public class Skill extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, unique = true)
    String name;
}
