package com.rocketseat.desafio.mapper;

import com.rocketseat.desafio.dto.request.CreateCourseRequest;
import com.rocketseat.desafio.dto.response.CourseResponse;
import com.rocketseat.desafio.model.CourseEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Objects;

@SpringBootTest
public class CourseMapperTest {

    private CourseMapper courseMapper;

    @BeforeEach
    public void setup(){
        courseMapper = new CourseMapper();
    }

    @Test
    public void validateMapRequestToEntity(){
        CreateCourseRequest request = new CreateCourseRequest("Teste", "Teste descricao");

        CourseEntity entity = courseMapper.createRequestToEntity(request);

        assert Objects.equals(request.getName(), entity.getName());
        assert Objects.equals(request.getCategory(), entity.getCategory());
        assert Objects.equals(entity.getActive(), true);
    }

    @Test
    public void validateMapEntityToResponse(){
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now().plusDays(2);
        CourseEntity entity = new CourseEntity(1L, "Teste", "Teste Categoria", true, createdAt, updatedAt);

        CourseResponse response = courseMapper.entityToResponse(entity);

        assert Objects.equals(response.getId(), entity.getId());
        assert Objects.equals(response.getName(), entity.getName());
        assert Objects.equals(response.getCategory(), entity.getCategory());
        assert Objects.equals(response.getActive(), entity.getActive());
        assert Objects.equals(response.getCreatedAt(), entity.getCreatedAt());
        assert Objects.equals(response.getUpdatedAt(), entity.getUpdatedAt());
    }
}
