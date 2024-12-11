package com.audible.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PaymentDTO {

    private String paymentId;
    private LocalDateTime paymentTime;
    private Float amount;
    private String userId;
    private String cardId;

}
