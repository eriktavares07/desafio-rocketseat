package com.rocketseat.desafio.service;

import org.springframework.stereotype.Service;

import com.rocketseat.desafio.dto.request.CreateCourseRequest;
import com.rocketseat.desafio.dto.response.CourseResponse;
import com.rocketseat.desafio.error.ChallengeErrorType;
import com.rocketseat.desafio.handler.ExceptionHandlerService;
import com.rocketseat.desafio.mapper.CourseMapper;
import com.rocketseat.desafio.model.CourseEntity;
import com.rocketseat.desafio.repository.CourseRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreateCourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public CreateCourseService(CourseRepository courseRepository,
                               CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    public CourseResponse createCourse(CreateCourseRequest request) {
        if(courseRepository.existsByName(request.getName())) {
            throw ExceptionHandlerService.createConflictException(
                ChallengeErrorType.COURSE_ALREADY_EXISTS);
        }

        CourseEntity courseToSave = courseMapper.createRequestToEntity(request);

        return courseMapper.entityToResponse(courseRepository.save(courseToSave));
    }

    public List<CourseResponse> getAllCourses(String name, String category) {
        List<CourseEntity> courses;
        if (name != null && !name.isEmpty()) {
            courses = courseRepository.findByNameContainingIgnoreCase(name);
        } else if (category != null && !category.isEmpty()) {
            courses = courseRepository.findByCategoryContainingIgnoreCase(category);
        } else {
            courses = courseRepository.findAll();
        }
        return courses.stream()
                .map(courseMapper::entityToResponse)
                .collect(Collectors.toList());
    }

    public CourseResponse getCourseById(Long id) {
        CourseEntity course = courseRepository.findById(id)
                .orElseThrow(() -> ExceptionHandlerService.createNotFoundException(ChallengeErrorType.COURSE_NOT_FOUND));
        return courseMapper.entityToResponse(course);
    }

    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw ExceptionHandlerService.createNotFoundException(ChallengeErrorType.COURSE_NOT_FOUND);
        }
        courseRepository.deleteById(id);
    }
}
