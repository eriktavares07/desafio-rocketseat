package com.rocketseat.desafio.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "courses")
@Getter
@AllArgsConstructor
@Builder
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long id;

    @Column(nullable = false, name = "course_name")
    private String name;

    @Column(nullable = false,  name = "course_category")
    private String category;

    @Column(nullable = false,  name = "course_active")
    private Boolean active;

    @CreationTimestamp
    @Column(nullable = false,  name = "course_created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false,  name = "course_updated_at")
    private LocalDateTime updatedAt;

}
