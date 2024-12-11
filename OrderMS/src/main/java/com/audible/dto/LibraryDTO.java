package com.audible.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LibraryDTO {

    private String libraryId;
    private String userId;
    private BookDTO bookDTO;

}
