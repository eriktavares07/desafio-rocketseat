package com.rocketseat.desafio.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CourseRequest {

    @NotNull(message = "O campo nome não pode ser nulo")
    @NotEmpty(message = "O campo nome não pode ser vazio")
    @NotBlank(message = "O campo nome não pode estar em branco")
    private final String name;

    @NotNull(message = "O campo categoria não pode ser nulo")
    @NotEmpty(message = "O campo categoria não pode ser vazio")
    @NotBlank(message = "O campo categoria não pode estar em branco")
    private final String category;

}
