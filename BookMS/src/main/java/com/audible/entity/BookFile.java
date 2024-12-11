package com.audible.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;

@Getter
@Setter
@Builder
@Document(collection="BookFile")
public class BookFile {

	@Id
	private String bookFileId;
	private String name;
	private String type;
	private String filePath;

}
