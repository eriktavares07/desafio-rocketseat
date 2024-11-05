package com.rocketseat.desafio.repository;

import com.rocketseat.desafio.model.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Long> {
    boolean existsByName(String name);

    List<CourseEntity> findByNameContainingIgnoreCase(String name);


    List<CourseEntity> findByCategoryContainingIgnoreCase(String category);
}
