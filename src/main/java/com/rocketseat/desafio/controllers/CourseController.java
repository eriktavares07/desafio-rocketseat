package com.rocketseat.desafio.controllers;

import com.rocketseat.desafio.dto.request.CourseRequest;
import com.rocketseat.desafio.dto.request.UpdateCourseRequest;
import com.rocketseat.desafio.dto.response.CourseResponse;
import com.rocketseat.desafio.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/cursos")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseResponse createCourse(@RequestBody @Valid CourseRequest request) {
        return courseService.createCourse(request);
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public CourseResponse partialUpdateCourse(@PathVariable Long id, @RequestBody @Valid UpdateCourseRequest request) {
        return courseService.partialUpdateCourse(id, request);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public CourseResponse updateCourse(@PathVariable Long id, @RequestBody @Valid UpdateCourseRequest request) {
        return courseService.updateCourse(id, request);
    }
}
