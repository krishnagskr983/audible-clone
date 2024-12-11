package com.audible.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "OrderedBook")
public class OrderedBook {

    @Id
    private String orderedBookId;
    private Float orderedPrice;
    private String bookId;

}
