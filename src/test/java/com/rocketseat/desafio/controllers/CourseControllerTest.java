package com.rocketseat.desafio.controllers;

import com.rocketseat.desafio.config.SecurityConfig;
import com.rocketseat.desafio.dto.request.CourseRequest;
import com.rocketseat.desafio.dto.request.UpdateCourseRequest;
import com.rocketseat.desafio.dto.response.CourseResponse;
import com.rocketseat.desafio.service.CourseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CourseController.class)
@Import(SecurityConfig.class)
public class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @WithMockUser(roles = "USER")
    @Test
    public void createCourse_ShouldReturnCreatedCourse() throws Exception {
        CourseRequest courseRequest = new CourseRequest("Curso de Java", "Programação");

        CourseResponse courseResponse = new CourseResponse(
                1L,
                "Curso de Java",
                "Programação",
                true,
                LocalDateTime.now(),
                LocalDateTime.now(),
                true,
                false
        );

        when(courseService.createCourse(any(CourseRequest.class))).thenReturn(courseResponse);

        mockMvc.perform(post("/cursos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"Curso de Java\", \"category\": \"Programação\" }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(courseResponse.getId()))
                .andExpect(jsonPath("$.name").value(courseResponse.getName()))
                .andExpect(jsonPath("$.category").value(courseResponse.getCategory()))
                .andExpect(jsonPath("$.active").value(courseResponse.getActive()))
                .andExpect(jsonPath("$.creation_date").exists())
                .andExpect(jsonPath("$.last_updated_date").exists())
                .andExpect(jsonPath("$.updated").value(courseResponse.getUpdated()))
                .andExpect(jsonPath("$.partial_updated").value(courseResponse.getPartialUpdated()));

        verify(courseService).createCourse(any(CourseRequest.class));
    }

    @WithMockUser(roles = "USER")
    @Test
    public void partialUpdateCourse_ShouldReturnUpdatedCourse() throws Exception {
        Long courseId = 1L;

        CourseResponse courseResponse = new CourseResponse(
                courseId,
                "Curso de Java Atualizado",
                "Programação",
                true,
                LocalDateTime.now().minusDays(10),
                LocalDateTime.now(),
                true,
                true
        );

        when(courseService.partialUpdateCourse(eq(courseId), any(UpdateCourseRequest.class))).thenReturn(courseResponse);

        mockMvc.perform(patch("/cursos/{id}", courseId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"Curso de Java Atualizado\", \"category\": \"Programação\", \"active\": true }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(courseResponse.getId()))
                .andExpect(jsonPath("$.name").value(courseResponse.getName()))
                .andExpect(jsonPath("$.category").value(courseResponse.getCategory()))
                .andExpect(jsonPath("$.active").value(courseResponse.getActive()))
                .andExpect(jsonPath("$.creation_date").exists())
                .andExpect(jsonPath("$.last_updated_date").exists())
                .andExpect(jsonPath("$.updated").value(courseResponse.getUpdated()))
                .andExpect(jsonPath("$.partial_updated").value(courseResponse.getPartialUpdated()));

        verify(courseService).partialUpdateCourse(eq(courseId), any(UpdateCourseRequest.class));
    }

    @WithMockUser(roles = "USER")
    @Test
    public void updateCourse_ShouldReturnUpdatedCourse() throws Exception {
        Long courseId = 1L;

        CourseResponse courseResponse = new CourseResponse(
                courseId,
                "Curso de Java Completo",
                "Programação",
                true,
                LocalDateTime.now().minusDays(10),
                LocalDateTime.now(),
                true,
                false
        );

        when(courseService.updateCourse(eq(courseId), any(UpdateCourseRequest.class))).thenReturn(courseResponse);

        mockMvc.perform(put("/cursos/{id}", courseId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"Curso de Java Completo\", \"category\": \"Programação\", \"active\": true }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(courseResponse.getId()))
                .andExpect(jsonPath("$.name").value(courseResponse.getName()))
                .andExpect(jsonPath("$.category").value(courseResponse.getCategory()))
                .andExpect(jsonPath("$.active").value(courseResponse.getActive()))
                .andExpect(jsonPath("$.creation_date").exists())
                .andExpect(jsonPath("$.last_updated_date").exists())
                .andExpect(jsonPath("$.updated").value(courseResponse.getUpdated()))
                .andExpect(jsonPath("$.partial_updated").value(courseResponse.getPartialUpdated()));

        verify(courseService).updateCourse(eq(courseId), any(UpdateCourseRequest.class));
    }
}
