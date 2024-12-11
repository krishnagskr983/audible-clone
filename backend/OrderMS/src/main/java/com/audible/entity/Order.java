package com.audible.entity;

import com.audible.dto.LibraryDTO;
import com.audible.dto.OrderedBookDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collation = "Order")
public class Order {

    @Id
    private String orderId;
    private Double orderValueBeforeDiscount;
    private Double discountPercent;
    private Double orderValueAfterDiscount;
    private String userId;
    private String orderStatus;
    private LocalDateTime orderDate;
    private OrderedBook orderedBook;
    private String cardId;

}

