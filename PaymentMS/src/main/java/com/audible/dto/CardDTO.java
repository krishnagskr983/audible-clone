package com.audible.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CardDTO {

    private String cardId;
    private String nameOnCard;
    private String cvv;
    private LocalDate expiryDate;
    private String cardType;
    private String userId;

}
