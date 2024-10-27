package com.rocketseat.desafio.controller;

import com.rocketseat.desafio.dto.request.CreateCourseRequest;
import com.rocketseat.desafio.dto.request.UpdateCourseRequest;
import com.rocketseat.desafio.dto.response.CourseResponse;
import com.rocketseat.desafio.service.CreateCourseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

}
