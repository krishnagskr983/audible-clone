package com.audible.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection="Book")
public class Book {
	
	@Id 
	private String bookId;
	private String bookTitle;
	private String bookOverview;
	private String bookPrice;
	private String authorName;
	private String narratorName;
	private String genre;
	private String ratings;
	private String description;
	private String length;
	private BookFile bookPhoto;
	private BookFile bookAudio;

}
