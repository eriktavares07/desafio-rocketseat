package com.rocketseat.desafio.handler;

import com.rocketseat.desafio.error.ChallengeErrorType;
import com.rocketseat.desafio.exception.ChallengeConflictException;
import org.springframework.stereotype.Service;

@Service
public class ExceptionHandlerService {

    public static ChallengeConflictException createConflictException(ChallengeErrorType error) {
        return new ChallengeConflictException(error.getMessage(), error.getCode());
    }

    public static ChallengeConflictException createNotFoundException(ChallengeErrorType error) {
        return new ChallengeConflictException(error.getMessage(), error.getCode());
    }

}
