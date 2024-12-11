package com.audible.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserFileDTO {
    private String userFileId;
    private String name;
    private String type;
    private String filePath;
}
