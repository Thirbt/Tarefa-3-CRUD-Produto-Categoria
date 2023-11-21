package com.example.tarefa1.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.tarefa1.dto.exception.ErrorDTO;
import com.example.tarefa1.exception.GeneralApiError;

@RestControllerAdvice
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class ControllerAdvice {
    
    @ExceptionHandler(GeneralApiError.class)
    public ErrorDTO handlerGeneralApiError(GeneralApiError error){
        return new ErrorDTO(error.getMessage());
    }
}
