package com.rocketseat.desafio.services;

import com.rocketseat.desafio.dto.request.CourseRequest;
import com.rocketseat.desafio.dto.request.UpdateCourseRequest;
import com.rocketseat.desafio.dto.response.CourseResponse;
import com.rocketseat.desafio.error.ChallengeErrorType;
import com.rocketseat.desafio.exception.ChallengeConflictException;
import com.rocketseat.desafio.exception.ResourceNotFoundException;
import com.rocketseat.desafio.handler.ExceptionHandlerService;
import com.rocketseat.desafio.mapper.CourseMapper;
import com.rocketseat.desafio.model.CourseEntity;
import com.rocketseat.desafio.repository.CourseRepository;
import com.rocketseat.desafio.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseMapper courseMapper;

    @InjectMocks
    private CourseService courseService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCourse_ShouldReturnCreatedCourseResponse() {
        CourseRequest request = new CourseRequest("Curso de Java", "Programação");
        CourseEntity courseEntity = new CourseEntity();
        CourseResponse courseResponse = new CourseResponse(1L, "Curso de Java", "Programação", true, null, null, false, false);

        when(courseRepository.existsByName(request.getName())).thenReturn(false);
        when(courseMapper.createRequestToEntity(request)).thenReturn(courseEntity);
        when(courseRepository.save(any(CourseEntity.class))).thenReturn(courseEntity);
        when(courseMapper.entityToResponse(courseEntity)).thenReturn(courseResponse);

        CourseResponse response = courseService.createCourse(request);

        assertNotNull(response);
        assertEquals("Curso de Java", response.getName());
        assertEquals("Programação", response.getCategory());
        verify(courseRepository).save(any(CourseEntity.class));
    }

    @Test
    void createCourse_ShouldThrowChallengeConflictException_WhenCourseAlreadyExists() {
        CourseRequest request = new CourseRequest("Curso de Java", "Programação");

        when(courseRepository.existsByName(request.getName())).thenReturn(true);

        ChallengeConflictException exception = assertThrows(ChallengeConflictException.class, () -> {
            courseService.createCourse(request);
        });

        assertEquals(ChallengeErrorType.COURSE_ALREADY_EXISTS.getMessage(), exception.getMessage());
        assertEquals(ChallengeErrorType.COURSE_ALREADY_EXISTS.getCode(), exception.getErrorCode());
        verify(courseRepository, never()).save(any(CourseEntity.class));
    }

    @Test
    void partialUpdateCourse_ShouldReturnUpdatedCourseResponse() {
        Long courseId = 1L;
        UpdateCourseRequest updateRequest = new UpdateCourseRequest("Curso de Java Avançado", "Programação Avançada", true);
        CourseEntity existingCourse = new CourseEntity();
        CourseResponse updatedCourseResponse = new CourseResponse(courseId, "Curso de Java Avançado", "Programação Avançada", true, null, null, true, true);

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(existingCourse));
        when(courseRepository.save(existingCourse)).thenReturn(existingCourse);
        when(courseMapper.entityToResponse(existingCourse)).thenReturn(updatedCourseResponse);

        CourseResponse response = courseService.partialUpdateCourse(courseId, updateRequest);

        assertNotNull(response);
        assertEquals("Curso de Java Avançado", response.getName());
        assertEquals("Programação Avançada", response.getCategory());
        assertTrue(response.getActive());
        verify(courseRepository).save(existingCourse);
    }

    @Test
    void partialUpdateCourse_ShouldThrowResourceNotFoundException_WhenCourseNotFound() {
        Long courseId = 1L;
        UpdateCourseRequest updateRequest = new UpdateCourseRequest("Curso de Java Avançado", "Programação Avançada", true);

        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            courseService.partialUpdateCourse(courseId, updateRequest);
        });

        assertEquals("Course not found", exception.getMessage());
        verify(courseRepository, never()).save(any(CourseEntity.class));
    }

    @Test
    void updateCourse_ShouldReturnUpdatedCourseResponse() {
        Long courseId = 1L;
        UpdateCourseRequest updateRequest = new UpdateCourseRequest("Curso de Java Completo", "Programação Completa", true);
        CourseEntity existingCourse = new CourseEntity();
        CourseResponse updatedCourseResponse = new CourseResponse(courseId, "Curso de Java Completo", "Programação Completa", true, null, null, true, false);

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(existingCourse));
        when(courseRepository.save(existingCourse)).thenReturn(existingCourse);
        when(courseMapper.entityToResponse(existingCourse)).thenReturn(updatedCourseResponse);

        CourseResponse response = courseService.updateCourse(courseId, updateRequest);

        assertNotNull(response);
        assertEquals("Curso de Java Completo", response.getName());
        assertEquals("Programação Completa", response.getCategory());
        assertTrue(response.getActive());
        verify(courseRepository).save(existingCourse);
    }

    @Test
    void updateCourse_ShouldThrowResourceNotFoundException_WhenCourseNotFound() {
        Long courseId = 1L;
        UpdateCourseRequest updateRequest = new UpdateCourseRequest("Curso de Java Completo", "Programação Completa", true);

        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            courseService.updateCourse(courseId, updateRequest);
        });

        assertEquals("Course not found", exception.getMessage());
        verify(courseRepository, never()).save(any(CourseEntity.class));
    }
}
