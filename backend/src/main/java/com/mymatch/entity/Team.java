package com.mymatch.entity;

import java.util.List;

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
@SQLDelete(sql = "UPDATE team SET deleted = 1 WHERE id = ?")
@SQLRestriction("deleted = 0")
public class Team extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String name;

    Integer memberMax;

    @Column(length = 1000)
    String description;

    @Column(name = "image", columnDefinition = "TEXT")
    String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semester_id")
    Semester semester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campus_id")
    Campus campus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    Course course;
    // Người tạo team
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    Student createdBy;
    // Vị trí đang tuyển
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    List<TeamRequest> requests;
    // Thành viên trong team
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    List<TeamMember> teamMembers;
}
