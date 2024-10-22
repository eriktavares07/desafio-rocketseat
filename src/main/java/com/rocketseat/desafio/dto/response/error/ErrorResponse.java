package com.rocketseat.desafio.dto.response.error;

public record ErrorResponse(Integer status, String error, CauseErrorResponse cause) {}
