package com.rocketseat.desafio.service;

import com.rocketseat.desafio.dto.request.CreateCourseRequest;
import com.rocketseat.desafio.dto.response.CourseResponse;
import com.rocketseat.desafio.error.ChallengeErrorType;
import com.rocketseat.desafio.handler.ExceptionHandlerService;
import com.rocketseat.desafio.mapper.CourseMapper;
import com.rocketseat.desafio.model.CourseEntity;
import com.rocketseat.desafio.repository.CourseRepository;
import org.springframework.stereotype.Service;

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
            throw ExceptionHandlerService.createConflictException(ChallengeErrorType.COURSE_ALREADY_EXISTS);
        }

        CourseEntity courseToSave = courseMapper.createRequestToEntity(request);

        return courseMapper.entityToResponse(courseRepository.save(courseToSave));
    }

}
