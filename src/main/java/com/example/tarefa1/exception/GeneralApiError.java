package com.example.tarefa1.exception;

public class GeneralApiError extends RuntimeException {
    
    public GeneralApiError(String error){
        super(error);
    }
}
