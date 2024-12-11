package com.audible.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDTO {

	private String bookId;
	@NotNull(message="{book.bookTitle.notPresent}")
	private String bookTitle;
	private String bookOverview;
	@NotNull(message="{book.bookPrice.notPresent}")
	private String bookPrice;
	@NotNull(message="{book.authorName.notPresent}")
	@Pattern(regexp="[A-Z][a-z]+( [A-Z][a-z]+)?", message="{book.authorName.invalid}")
	private String authorName;
	@NotNull(message="{book.narratorName.notPresent}")
	@Pattern(regexp="[A-Z][a-z]+( [A-Z][a-z]+)?", message="{book.narratorName.invalid}")
	private String narratorName;
	@NotNull(message="{book.genre.notPresent}")
	private String genre;
	private String ratings;
	@NotNull(message="{book.description.notPresent}")
	private String description;
	@NotNull(message="{book.length.notPresent}")
	private String length;
	@NotNull(message="{book.bookPhoto.notPresent}")
	private BookFileDTO bookPhotoDTO;
	@NotNull(message="{book.bookAudio.notPresent}")
	private BookFileDTO bookAudioDTO;

	public BookDTO() {}

	public BookDTO(@NotNull(message = "{book.bookTitle.notPresent}") String bookTitle,
				   String bookOverview,
				   @NotNull(message = "{book.bookPrice.notPresent}") String bookPrice,
				   @NotNull(message = "{book.authorName.notPresent}") @Pattern(regexp = "[A-Z][a-z]+( [A-Z][a-z]+)?", message = "{book.authorName.invalid}") String authorName,
				   @NotNull(message = "{book.narratorName.notPresent}") @Pattern(regexp = "[A-Z][a-z]+( [A-Z][a-z]+)?", message = "{book.narratorName.invalid}") String narratorName,
				   @NotNull(message = "{book.genre.notPresent}") String genre, String ratings,
				   @NotNull(message = "{book.description.notPresent}") String description,
				   @NotNull(message = "{book.length.notPresent}") String length){
		super();
		this.bookTitle = bookTitle;
		this.bookOverview = bookOverview;
		this.bookPrice = bookPrice;
		this.authorName = authorName;
		this.narratorName = narratorName;
		this.genre = genre;
		this.ratings = ratings;
		this.description = description;
		this.length = length;
	}

}
