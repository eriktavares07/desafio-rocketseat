package com.rocketseat.desafio.mapper;

import com.rocketseat.desafio.dto.request.CourseRequest;
import com.rocketseat.desafio.dto.response.CourseResponse;
import com.rocketseat.desafio.model.CourseEntity;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {

    public CourseEntity createRequestToEntity(CourseRequest request) {
        return CourseEntity.builder()
                .name(request.getName())
                .category(request.getCategory())
                .active(true)
                .build();
    }

    public CourseResponse entityToResponse(CourseEntity courseEntity) {
        return new CourseResponse(
                courseEntity.getId(),
                courseEntity.getName(),
                courseEntity.getCategory(),
                courseEntity.getActive(),
                courseEntity.getCreatedAt(),
                courseEntity.getUpdatedAt(),
                true,
                true
        );
    }
}
