package com.rocketseat.desafio.controller;

import com.rocketseat.desafio.dto.request.CreateCourseRequest;
import com.rocketseat.desafio.dto.request.UpdateCourseRequest;
import com.rocketseat.desafio.dto.response.CourseResponse;
import com.rocketseat.desafio.service.CreateCourseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/cursos")
public class CreateCourseController {

    private final CreateCourseService createCourseService;

    public CreateCourseController(CreateCourseService createCourseService) {
        this.createCourseService = createCourseService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseResponse createCourse(@RequestBody @Valid CreateCourseRequest request) {
        return createCourseService.createCourse(request);
    }

    @GetMapping
    public ResponseEntity<List<CourseResponse>> getAllCursos(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category) {
        List<CourseResponse> courses = createCourseService.getAllCourses(name, category);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponse> getCursoById(@PathVariable Long id) {
        CourseResponse courseResponse = createCourseService.getCourseById(id);
        return ResponseEntity.ok(courseResponse);
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public CourseResponse partialUpdateCourse(@PathVariable Long id, @RequestBody UpdateCourseRequest updateRequest) {
        CourseResponse updatedCourse = createCourseService.partialUpdateCourse(id, updateRequest);
        System.out.println("Updated Course: " + updatedCourse);
        return updatedCourse;
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public CourseResponse updateCourse(@PathVariable Long id, @RequestBody UpdateCourseRequest updateRequest) {
        CourseResponse updatedCourse = createCourseService.updateCourse(id, updateRequest);
        System.out.println("Updated Course: " + updatedCourse);
        return updatedCourse;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCurso(@PathVariable Long id) {
        createCourseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}
