package com.rocketseat.desafio.service;

import com.rocketseat.desafio.dto.request.CourseRequest;
import com.rocketseat.desafio.dto.request.UpdateCourseRequest;
import com.rocketseat.desafio.dto.response.CourseResponse;
import com.rocketseat.desafio.error.ChallengeErrorType;
import com.rocketseat.desafio.exception.ResourceNotFoundException;
import com.rocketseat.desafio.handler.ExceptionHandlerService;
import com.rocketseat.desafio.mapper.CourseMapper;
import com.rocketseat.desafio.model.CourseEntity;
import com.rocketseat.desafio.repository.CourseRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public CourseService(CourseRepository courseRepository,
                         CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    public CourseResponse createCourse(CourseRequest request) {
        if (courseRepository.existsByName(request.getName())) {
            throw ExceptionHandlerService.createConflictException(
                    ChallengeErrorType.COURSE_ALREADY_EXISTS);
        }

        CourseEntity courseToSave = courseMapper.createRequestToEntity(request);

        return courseMapper.entityToResponse(courseRepository.save(courseToSave));
    }

    public CourseResponse partialUpdateCourse(Long id, @Valid UpdateCourseRequest request) {
        CourseEntity course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        course.setName(request.getName());
        course.setCategory(request.getCategory());
        course.setActive(request.getActive());

        courseRepository.save(course);
        return courseMapper.entityToResponse(course);
    }

    public CourseResponse updateCourse(Long id, @Valid UpdateCourseRequest request) {
        CourseEntity course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        course.setName(request.getName());
        course.setCategory(request.getCategory());
        course.setActive(request.getActive());

        courseRepository.save(course);
        return courseMapper.entityToResponse(course);
    }
}
