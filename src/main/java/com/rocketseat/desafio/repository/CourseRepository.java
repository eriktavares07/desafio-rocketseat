package com.rocketseat.desafio.repository;

import com.rocketseat.desafio.model.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Long> {
    boolean existsByName(String name);
}
