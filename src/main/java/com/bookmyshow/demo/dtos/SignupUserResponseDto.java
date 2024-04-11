package com.bookmyshow.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignupUserResponseDto {

    private Long userId;
    private ResponseStatus responseStatus;
}
