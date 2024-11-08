package com.rocketseat.desafio.error;

import lombok.Getter;

@Getter
public enum ChallengeErrorType {

    COURSE_ALREADY_EXISTS(10001, "Curso já está cadastrado"),
    COURSE_NOT_FOUND(10002, "Curso não encontrado");

    public final String message;
    public final int code;

    ChallengeErrorType(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
