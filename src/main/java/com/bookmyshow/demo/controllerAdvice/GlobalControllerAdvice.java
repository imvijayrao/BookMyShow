package com.bookmyshow.demo.controllerAdvice;

import com.bookmyshow.demo.dtos.ErrorResponseDto;
import com.bookmyshow.demo.exceptions.UserAlreadyExisting;
import com.bookmyshow.demo.exceptions.UserDoesNotExist;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

public class GlobalControllerAdvice {

    //Using "WebRequest" helps provide more detailed and useful error information like headers, parameter and so on,
    // to clients, making it easier for them to understand and resolve issues.

    @ExceptionHandler(UserDoesNotExist.class)
    public ResponseEntity<ErrorResponseDto> handleUserNotFound(UserDoesNotExist ex, WebRequest webRequest){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setErrorStatus(HttpStatus.NOT_FOUND.value());
        errorResponseDto.setErrorMessage(ex.getMessage());
        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }

    //This is normal usecase
    @ExceptionHandler(UserAlreadyExisting.class)
    public ResponseEntity<ErrorResponseDto> handleExistingUser(){
        return new ResponseEntity<>(new ErrorResponseDto(), HttpStatus.CONFLICT);
    }



}
