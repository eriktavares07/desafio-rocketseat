package com.rocketseat.desafio.service;

import com.rocketseat.desafio.dto.request.CreateCourseRequest;
import com.rocketseat.desafio.dto.request.UpdateCourseRequest;
import com.rocketseat.desafio.dto.response.CourseResponse;
import com.rocketseat.desafio.exception.ResourceNotFoundException;
import com.rocketseat.desafio.exception.ChallengeConflictException;
import com.rocketseat.desafio.mapper.CourseMapper;
import com.rocketseat.desafio.model.CourseEntity;
import com.rocketseat.desafio.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CreateCourseServiceTest {

    @InjectMocks
    private CreateCourseService createCourseService;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseMapper courseMapper;

    private CreateCourseRequest validRequest;
    private CourseEntity mockCourseEntity;
    private CourseResponse mockCourseResponse;

    @BeforeEach
    public void setup() {
        validRequest = new CreateCourseRequest("Curso de Teste", "Descrição do Curso");
        mockCourseEntity = new CourseEntity(1L, "Curso de Teste", "Descrição do Curso", true, null, null);
        mockCourseResponse = new CourseResponse(1L, "Curso de Teste", "Descrição do Curso", true, null, null);
    }

    @Test
    public void shouldThrowConflictExceptionWhenCourseAlreadyExists() {
        Mockito.when(courseRepository.existsByName(validRequest.getName())).thenReturn(true);

        assertThrows(ChallengeConflictException.class, () -> {
            createCourseService.createCourse(validRequest);
        });
    }

    @Test
    public void shouldReturnCourseWhenValidRequest() {
        Mockito.when(courseRepository.existsByName(validRequest.getName())).thenReturn(false);
        Mockito.when(courseMapper.createRequestToEntity(validRequest)).thenReturn(mockCourseEntity);
        Mockito.when(courseRepository.save(mockCourseEntity)).thenReturn(mockCourseEntity);
        Mockito.when(courseMapper.entityToResponse(mockCourseEntity)).thenReturn(mockCourseResponse);

        CourseResponse response = createCourseService.createCourse(validRequest);

        assertEquals(mockCourseResponse.getName(), response.getName());
        assertEquals(mockCourseResponse.getCategory(), response.getCategory());
        assertTrue(response.getActive());
    }

    @Test
    public void shouldThrowResourceNotFoundExceptionWhenCourseNotFound() {
        Long invalidId = 99L;

        Mockito.when(courseRepository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            createCourseService.getCourseById(invalidId);
        });
    }

    @Test
    public void shouldNotThrowExceptionWhenCourseExistsById() {
        Long validId = 1L;
        Mockito.when(courseRepository.findById(validId)).thenReturn(Optional.of(mockCourseEntity));
        Mockito.when(courseMapper.entityToResponse(mockCourseEntity)).thenReturn(mockCourseResponse);

        CourseResponse response = createCourseService.getCourseById(validId);

        assertNotNull(response);
        assertEquals(mockCourseResponse.getName(), response.getName());
        assertEquals(mockCourseResponse.getCategory(), response.getCategory());
    }

    @Test
    public void shouldReturnAllCoursesWhenNoFiltersProvided() {
        List<CourseEntity> mockCourses = Arrays.asList(mockCourseEntity);
        Mockito.when(courseRepository.findAll()).thenReturn(mockCourses);
        Mockito.when(courseMapper.entityToResponse(mockCourseEntity)).thenReturn(mockCourseResponse);

        List<CourseResponse> response = createCourseService.getAllCourses(null, null);

        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(1, response.size());
        assertEquals(mockCourseResponse.getName(), response.get(0).getName());
    }

    @Test
    public void shouldReturnCoursesFilteredByName() {
        List<CourseEntity> mockCourses = Arrays.asList(mockCourseEntity);
        Mockito.when(courseRepository.findByNameContainingIgnoreCase("Curso de Teste")).thenReturn(mockCourses);
        Mockito.when(courseMapper.entityToResponse(mockCourseEntity)).thenReturn(mockCourseResponse);

        List<CourseResponse> response = createCourseService.getAllCourses("Curso de Teste", null);

        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(1, response.size());
        assertEquals(mockCourseResponse.getName(), response.get(0).getName());
    }

    @Test
    public void shouldReturnCoursesFilteredByCategory() {
        List<CourseEntity> mockCourses = Arrays.asList(mockCourseEntity);
        Mockito.when(courseRepository.findByCategoryContainingIgnoreCase("Descrição do Curso")).thenReturn(mockCourses);
        Mockito.when(courseMapper.entityToResponse(mockCourseEntity)).thenReturn(mockCourseResponse);

        List<CourseResponse> response = createCourseService.getAllCourses(null, "Descrição do Curso");

        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(1, response.size());
        assertEquals(mockCourseResponse.getCategory(), response.get(0).getCategory());
    }

    @Test
    public void shouldReturnCoursesFilteredByNameAndCategory() {
        List<CourseEntity> mockCourses = Arrays.asList(mockCourseEntity);
        Mockito.when(courseRepository.findByNameContainingIgnoreCaseAndCategoryContainingIgnoreCase("Curso de Teste", "Descrição do Curso")).thenReturn(mockCourses);
        Mockito.when(courseMapper.entityToResponse(mockCourseEntity)).thenReturn(mockCourseResponse);

        List<CourseResponse> response = createCourseService.getAllCourses("Curso de Teste", "Descrição do Curso");

        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(1, response.size());
        assertEquals(mockCourseResponse.getName(), response.get(0).getName());
        assertEquals(mockCourseResponse.getCategory(), response.get(0).getCategory());
    }

    @Test
    public void shouldPartialUpdateCourseWhenValidRequest() {
        Long validId = 1L;
        CourseEntity mockEntity = new CourseEntity(validId, "Curso de Teste", "Descrição do Curso", true, null, null);
        CourseResponse mockResponse = new CourseResponse(validId, "Curso Parcialmente Atualizado", "Descrição do Curso", true, null, null);

        UpdateCourseRequest updateRequest = new UpdateCourseRequest("Curso Parcialmente Atualizado", null, null);

        Mockito.when(courseRepository.findById(validId)).thenReturn(java.util.Optional.of(mockEntity));
        Mockito.when(courseRepository.save(Mockito.any(CourseEntity.class))).thenReturn(mockEntity);
        Mockito.when(courseMapper.entityToResponse(mockEntity)).thenReturn(mockResponse);

        CourseResponse response = createCourseService.partialUpdateCourse(validId, updateRequest);

        assertEquals("Curso Parcialmente Atualizado", response.getName());
        assertEquals("Descrição do Curso", response.getCategory());
    }


    @Test
    public void shouldThrowResourceNotFoundExceptionWhenCourseNotFoundForPartialUpdate() {
        UpdateCourseRequest request = new UpdateCourseRequest("Curso Atualizado", "Categoria Atualizada", true);
        Mockito.when(courseRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            createCourseService.partialUpdateCourse(99L, request);
        });
    }

    @Test
    public void shouldUpdateCourseWhenValidRequest() {
        Long validId = 1L;
        CourseEntity mockEntity = new CourseEntity(validId, "Curso de Teste", "Descrição do Curso", true, null, null);
        CourseResponse mockResponse = new CourseResponse(validId, "Curso Atualizado", "Nova Descrição", false, null, null);

        UpdateCourseRequest updateRequest = new UpdateCourseRequest("Curso Atualizado", "Nova Descrição", false);

        Mockito.when(courseRepository.findById(validId)).thenReturn(java.util.Optional.of(mockEntity));
        Mockito.when(courseRepository.save(Mockito.any(CourseEntity.class))).thenReturn(mockEntity);
        Mockito.when(courseMapper.entityToResponse(mockEntity)).thenReturn(mockResponse);

        CourseResponse response = createCourseService.updateCourse(validId, updateRequest);

        assertEquals("Curso Atualizado", response.getName());
        assertEquals("Nova Descrição", response.getCategory());
        assertFalse(response.getActive());
    }


    @Test
    public void shouldThrowResourceNotFoundExceptionWhenCourseNotFoundForUpdate() {
        UpdateCourseRequest request = new UpdateCourseRequest("Curso Atualizado", "Categoria Atualizada", true);
        Mockito.when(courseRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            createCourseService.updateCourse(99L, request);
        });
    }

    @Test
    public void shouldDeleteCourseWhenValidId() {
        Mockito.when(courseRepository.existsById(1L)).thenReturn(true);

        createCourseService.deleteCourse(1L);

        Mockito.verify(courseRepository).deleteById(1L);
    }

    @Test
    public void shouldThrowResourceNotFoundExceptionWhenCourseNotFoundForDelete() {
        Long invalidId = 99L;
        Mockito.when(courseRepository.existsById(invalidId)).thenReturn(false);

        assertThrows(ChallengeConflictException.class, () -> {
            createCourseService.deleteCourse(invalidId);
        });
    }
}
