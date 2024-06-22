package com.userlogin.Characters;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMaxSizeException(MaxUploadSizeExceededException ex) 
    {
    	System.out.println("***File size exceeded. Please upload a file less than 5MB.***");
        return new ErrorResponse("File size exceeded. Please upload a file less than 5MB.");
    }
    
    

    // Other exception handlers or global error handling logic can be added here if needed
}

class ErrorResponse {
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}