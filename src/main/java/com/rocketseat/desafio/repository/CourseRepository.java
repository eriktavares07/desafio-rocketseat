package com.rocketseat.desafio.repository;

import com.rocketseat.desafio.model.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Long> {
    List<CourseEntity> findByNameContainingIgnoreCase(String name);
    List<CourseEntity> findByCategoryContainingIgnoreCase(String category);
    List<CourseEntity> findByNameContainingIgnoreCaseAndCategoryContainingIgnoreCase(String name, String category);
    boolean existsByName(String name);
}
