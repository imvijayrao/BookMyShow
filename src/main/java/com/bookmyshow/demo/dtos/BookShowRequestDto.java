package com.bookmyshow.demo.dtos;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookShowRequestDto {

    private long UserId;
    private List<Long> ShowSeatId;
    private Long showId;

}
