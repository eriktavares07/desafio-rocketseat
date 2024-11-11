package com.rocketseat.desafio.mapper;

import com.rocketseat.desafio.dto.request.CreateCourseRequest;
import com.rocketseat.desafio.dto.request.UpdateCourseRequest;
import com.rocketseat.desafio.dto.response.CourseResponse;
import com.rocketseat.desafio.model.CourseEntity;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {

    public CourseEntity createRequestToEntity(CreateCourseRequest request){
        return CourseEntity.builder()
                .name(request.getName())
                .category(request.getCategory())
                .active(true)
                .build();
    }

    public CourseResponse entityToResponse(CourseEntity courseEntity){
        return new CourseResponse(
                courseEntity.getId(),
                courseEntity.getName(),
                courseEntity.getCategory(),
                courseEntity.getActive(),
                courseEntity.getCreatedAt(),
                courseEntity.getUpdatedAt());
    }
}
