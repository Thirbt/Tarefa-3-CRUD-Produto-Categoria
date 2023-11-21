package com.example.tarefa1.dto.exception;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;

@Getter
public class ErrorDTO {

    private List<String> errorList;

    public ErrorDTO(String errorMessage){
        errorList = Arrays.asList(errorMessage);
    }
}
