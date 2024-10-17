package com.bookmyshow.demo.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponseDto {

    private int errorStatus;
    private String errorMessage;

}
