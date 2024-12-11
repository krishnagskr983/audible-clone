package com.audible.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderedBookDTO {

    private String orderedBookId;
    private Float orderPrice;
    private BookDTO bookDTO;

}
