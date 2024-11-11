package com.rocketseat.desafio.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCourseRequest {

    @NotNull(message = "O campo ativo não pode ser nulo")
    private Boolean active;

    private String name;
    private String category;

    public UpdateCourseRequest(String name, String category, Boolean active) {
        this.name = name;
        this.category = category;
        this.active = active;
    }
}
