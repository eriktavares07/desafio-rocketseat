package com.rocketseat.desafio.error;

import lombok.Getter;

@Getter
public enum ChallengeErrorType {

    COURSE_ALREADY_EXISTS(10001, "Curso já está cadastrado");

    public final String message;
    public final int code;

    ChallengeErrorType(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
