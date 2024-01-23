package com.bookmyshow.demo.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class  Payment extends BaseModel{

    private String Refno;
    private PaymentStatus status;
    private int amount;
    private PaymentGatewayProvider provider;
    private PaymentMode mode;
}
