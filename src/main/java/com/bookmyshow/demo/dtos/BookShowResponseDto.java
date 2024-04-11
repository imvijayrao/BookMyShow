package com.bookmyshow.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class BookShowResponseDto {

    private Long BookingId;
    private int amount;
    private ResponseStatus responseStatus;
    private String failureReason;

}

