package com.bookmyshow.demo.controllerAdvice;

import com.bookmyshow.demo.dtos.ErrorResponseDto;
import com.bookmyshow.demo.exceptions.UserDoesNotExist;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalControllerAdvice {

    @ExceptionHandler(UserDoesNotExist.class)
    public ResponseEntity<ErrorResponseDto> handleUserNotFound(){
        return new ResponseEntity<>(new ErrorResponseDto(), HttpStatus.NOT_FOUND);
    }

}
