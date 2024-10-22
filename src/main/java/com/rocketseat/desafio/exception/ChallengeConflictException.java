package com.rocketseat.desafio.exception;

import lombok.Getter;

@Getter
public class ChallengeConflictException extends ChallengeApiException {

    private final int status = 409;
    private final String error = "conflict";
    private final String message;
    private final int errorCode;

    public ChallengeConflictException(String message, int errorCode) {
        super();
        this.message = message;
        this.errorCode = errorCode;
    }

}