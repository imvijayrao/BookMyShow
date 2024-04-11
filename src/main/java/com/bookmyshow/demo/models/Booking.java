package com.bookmyshow.demo.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Booking extends BaseModel{

    @Enumerated(EnumType.ORDINAL)
    private BookingStatus bookingStatus;

    @ManyToOne
    private User user;

    // to handle cancelation then it will be m to m
    @OneToMany
    private List<ShowSeat> showSeatList;
    private int amount;

    @OneToMany
    private List<Payment> payments;
}
