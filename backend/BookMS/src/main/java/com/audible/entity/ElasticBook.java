package com.audible.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;


@Getter
@Setter
@Document(indexName="audible")
public class ElasticBook {

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
	
	
	public ElasticBook(String bookId, String bookTitle, String bookPrice, String authorName, String narratorName,
			String genre, String ratings, String description, String length, BookFile bookPhoto,
			BookFile bookAudio) {
		super();
		this.bookId = bookId;
		this.bookTitle = bookTitle;
		this.bookPrice = bookPrice;
		this.authorName = authorName;
		this.narratorName = narratorName;
		this.genre = genre;
		this.ratings = ratings;
		this.description = description;
		this.length = length;
		this.bookPhoto = bookPhoto;
		this.bookAudio = bookAudio;
	}
	
}
