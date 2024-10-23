package com.rocketseat.desafio.handler;

import com.rocketseat.desafio.dto.response.error.CauseErrorResponse;
import com.rocketseat.desafio.dto.response.error.ErrorResponse;
import com.rocketseat.desafio.exception.ChallengeApiException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex, HttpServletRequest request) {
        ErrorResponse error = generateErrorResponse(ex);
        return new ResponseEntity<>(error, HttpStatus.valueOf(error.status()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        StringBuilder bodyOfResponse = new StringBuilder();

        ex.getBindingResult().getFieldErrors().forEach(item -> {
            bodyOfResponse.append(item.getDefaultMessage()).append(";");
        });

        CauseErrorResponse causeErrorResponse = new CauseErrorResponse(0, bodyOfResponse.toString());
        ErrorResponse  errorResponse = new ErrorResponse(400, "bad_request", causeErrorResponse);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    private ErrorResponse generateErrorResponse(Exception requestException){
        if(requestException instanceof ChallengeApiException ex){
            CauseErrorResponse causeErrorResponse = new CauseErrorResponse(ex.getErrorCode(), ex.getMessage());
            return new ErrorResponse(ex.getStatus(), ex.getError(), causeErrorResponse);
        }

        ChallengeApiException exception = new ChallengeApiException(requestException.getMessage(), 0);
        CauseErrorResponse causeErrorResponse = new CauseErrorResponse(exception.getErrorCode(), requestException.toString());
        return new ErrorResponse(exception.getStatus(), exception.getError(), causeErrorResponse);
    }

}
