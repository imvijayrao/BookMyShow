package com.bookmyshow.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ShowSeatType extends BaseModel{

    /*
    Showseat    show
        1         1
        M         1
     */
    @ManyToOne
    private Show show;
    private String seatType;
    private int price;
}
