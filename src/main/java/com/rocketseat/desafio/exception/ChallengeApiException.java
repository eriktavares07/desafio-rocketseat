package com.rocketseat.desafio.exception;

import lombok.Getter;

@Getter
public class ChallengeApiException extends RuntimeException {
    private final int status = 500;
    private final String error = "internal_error";
    private String message;
    private int errorCode;

    public ChallengeApiException(String message, int errorCode) {
        super();
        this.message = message;
        this.errorCode = errorCode;
    }

    public ChallengeApiException() {
        super("internal_error");
    }
}