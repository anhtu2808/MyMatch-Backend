package com.mymatch.entity;

import java.util.Objects;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
// @SQLDelete(sql = "UPDATE student_request_skill SET deleted = 1 WHERE id = ?")
// @SQLRestriction("deleted = 0")
public class StudentRequestSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id", nullable = false)
    StudentRequest request;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id", nullable = false)
    Skill skill;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentRequestSkill that = (StudentRequestSkill) o;
        return Objects.equals(request, that.request) && Objects.equals(skill, that.skill);
    }

    @Override
    public int hashCode() {
        return Objects.hash(request, skill);
    }
}
