package com.rocketseat.desafio.controller;

import com.rocketseat.desafio.dto.request.CreateCourseRequest;
import com.rocketseat.desafio.dto.request.UpdateCourseRequest;
import com.rocketseat.desafio.dto.response.CourseResponse;
import com.rocketseat.desafio.service.CreateCourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(CreateCourseController.class)
public class CreateCourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateCourseService createCourseService;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testCreateCourse() throws Exception {
        CreateCourseRequest request = new CreateCourseRequest("Curso de Java", "Tecnologia");

        CourseResponse response = new CourseResponse(
                1L,
                "Curso de Java",
                "Tecnologia",
                true,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        when(createCourseService.createCourse(request)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/cursos")
                        .contentType("application/json")
                        .content("{\"name\":\"Curso de Java\",\"category\":\"Tecnologia\"}"))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    public void testGetAllCursos() throws Exception {
        List<CourseResponse> courses = new ArrayList<>();
        courses.add(new CourseResponse(1L, "Curso de Java", "Tecnologia", true, LocalDateTime.now(), LocalDateTime.now()));
        courses.add(new CourseResponse(2L, "Curso de Python", "Tecnologia", true, LocalDateTime.now(), LocalDateTime.now()));

        when(createCourseService.getAllCourses(null, null)).thenReturn(courses);

        mockMvc.perform(MockMvcRequestBuilders.get("/cursos"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Curso de Java"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Curso de Python"));
    }

    @Test
    public void testGetCursoById() throws Exception {
        CourseResponse course = new CourseResponse(1L, "Curso de Java", "Tecnologia", true, LocalDateTime.now(), LocalDateTime.now());

        when(createCourseService.getCourseById(1L)).thenReturn(course);

        mockMvc.perform(MockMvcRequestBuilders.get("/cursos/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Curso de Java"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category").value("Tecnologia"));
    }

    @WithMockUser(roles = "USER")
    @Test
    public void testPartialUpdateCourse() throws Exception {
        Long courseId = 1L;

        CourseResponse courseResponse = new CourseResponse(
                courseId,
                "Curso de Java Atualizado",
                "Programação",
                true,
                LocalDateTime.now().minusDays(10),
                LocalDateTime.now()
        );

        when(createCourseService.partialUpdateCourse(eq(courseId), any(UpdateCourseRequest.class))).thenReturn(courseResponse);

        mockMvc.perform(patch("/cursos/{id}", courseId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"Curso de Java Atualizado\", \"category\": \"Programação\", \"active\": true }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(courseResponse.getId()))
                .andExpect(jsonPath("$.name").value(courseResponse.getName()))
                .andExpect(jsonPath("$.category").value(courseResponse.getCategory()))
                .andExpect(jsonPath("$.active").value(courseResponse.getActive()))
                .andExpect(jsonPath("$.creation_date").exists())
                .andExpect(jsonPath("$.last_updated_date").exists());

        verify(createCourseService).partialUpdateCourse(eq(courseId), any(UpdateCourseRequest.class));
    }

    @WithMockUser(roles = "USER")
    @Test
    public void testUpdateCourse() throws Exception {
        Long courseId = 1L;

        CourseResponse courseResponse = new CourseResponse(
                courseId,
                "Curso de Java Completo",
                "Programação",
                true,
                LocalDateTime.now().minusDays(10),
                LocalDateTime.now()
        );

        when(createCourseService.updateCourse(eq(courseId), any(UpdateCourseRequest.class))).thenReturn(courseResponse);

        mockMvc.perform(put("/cursos/{id}", courseId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"Curso de Java Completo\", \"category\": \"Programação\", \"active\": true }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(courseResponse.getId()))
                .andExpect(jsonPath("$.name").value(courseResponse.getName()))
                .andExpect(jsonPath("$.category").value(courseResponse.getCategory()))
                .andExpect(jsonPath("$.active").value(courseResponse.getActive()))
                .andExpect(jsonPath("$.creation_date").exists())
                .andExpect(jsonPath("$.last_updated_date").exists());

        verify(createCourseService).updateCourse(eq(courseId), any(UpdateCourseRequest.class));
    }



    @Test
    public void testDeleteCurso() throws Exception {
        doNothing().when(createCourseService).deleteCourse(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/cursos/1"))
                .andExpect(status().isNoContent());

        verify(createCourseService).deleteCourse(1L);
    }

}
