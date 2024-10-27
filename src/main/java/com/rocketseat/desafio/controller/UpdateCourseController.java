package com.rocketseat.desafio.controller;

import com.rocketseat.desafio.dto.request.UpdateCourseRequest;
import com.rocketseat.desafio.dto.response.CourseResponse;
import com.rocketseat.desafio.service.UpdateCourseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/cursos")
public class UpdateCourseController {

    private final UpdateCourseService updateCourseService;

    public UpdateCourseController(UpdateCourseService updateCourseService) {
        this.updateCourseService = updateCourseService;
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public CourseResponse updateCourse(@PathVariable Long id, @RequestBody @Valid UpdateCourseRequest request) {
        return updateCourseService.updateCourse(id, request);
    }

}
