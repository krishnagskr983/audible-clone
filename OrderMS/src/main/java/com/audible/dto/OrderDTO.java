package com.audible.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrderDTO {

    private String orderId;
    private Double orderValueBeforeDiscount;
    private Double discountPercent;
    private Double orderValueAfterDiscount;
    private UserDTO userDTO;
    private String orderStatus;
    private LocalDateTime orderDate;
    private OrderedBookDTO orderedBookDTO;
    private CardDTO cardDTO;

}
