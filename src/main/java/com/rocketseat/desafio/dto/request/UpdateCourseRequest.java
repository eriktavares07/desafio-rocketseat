package com.rocketseat.desafio.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCourseRequest extends CourseRequest {

    @NotNull(message = "O campo ativo não pode ser nulo")
    private Boolean active;

    public UpdateCourseRequest(String name, String category, Boolean active) {
        super(name, category);
        this.active = active;
    }

}
