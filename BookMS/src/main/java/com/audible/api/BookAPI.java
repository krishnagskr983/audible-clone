package com.audible.api;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.audible.config.CacheConfig;
import com.audible.dto.BookDTO;
import com.audible.entity.ElasticBook;
import com.audible.exception.AudibleException;
import com.audible.repository.ElasticBookRepository;
import com.audible.service.BookService;

import jakarta.validation.Valid;

@CrossOrigin
@RestController
@Validated
@RequestMapping(value="/book-api")
public class BookAPI {

	@Autowired 
	private BookService bookService;
	
	static Logger logger=LoggerFactory.getLogger(BookAPI.class);
	
	@SuppressWarnings("unused")
	@Autowired
	private CacheConfig cacheConfig;
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private ElasticBookRepository elasticBookRepository;

	//add New Book
	@PostMapping(value="/book/add")
	public ResponseEntity<String> addNewBook(@RequestParam("bookAudio") MultipartFile audio,
			@RequestParam("bookPhoto") MultipartFile photo,
			@RequestParam("bookTitle") String bookTitle,
			@RequestParam("bookOverview") String bookOverview,
			@RequestParam("bookPrice") String bookPrice,
			@RequestParam("authorName") String authorName,
			@RequestParam("narratorName") String narratorName,
			@RequestParam("genre") String genre,
			@RequestParam("ratings") String ratings,
			@RequestParam("description") String description,
			@RequestParam("length") String length) 
					throws Exception{
		BookDTO bookDTO=new BookDTO(bookTitle, bookOverview, bookPrice, authorName, narratorName,
				genre, ratings, description, length);
		String mssg=bookService.addNewBook(audio, photo, bookDTO);
		String successMsg=environment.getProperty("BookAPI.BOOK_DETAILS_ADDED")+mssg;
		return new ResponseEntity<>(successMsg, HttpStatus.OK);
	}
	
	@GetMapping(value="/book/{bookId}")
	public ResponseEntity<BookDTO> getBookById(@PathVariable String bookId) throws AudibleException{
		BookDTO bookDTO=bookService.getBookById(bookId);
		return new ResponseEntity<>(bookDTO, HttpStatus.OK);
	}
	
	@GetMapping(value="/books/pageNumber/{pageNumber}/pageSize/{pageSize}")
	public ResponseEntity<List<BookDTO>> getAllBooks(@PathVariable @Valid Integer pageNumber,
			@PathVariable @Valid Integer pageSize) throws AudibleException{
		List<BookDTO> allBooks=bookService.getAllBooks(pageNumber, pageSize);
		return new ResponseEntity<>(allBooks, HttpStatus.OK);
	}
	
	@GetMapping(value="/books/genre/{genreName}")
	public ResponseEntity<List<BookDTO>> getBooksByGenre(@PathVariable @Valid String genreName)
	throws AudibleException, ExecutionException{
//		List<BookDTO> bookDTOList=cacheConfig.getBooksByGenre(genreName);
		List<BookDTO> bookDTOList=bookService.getBooksByGenre(genreName);
		return new ResponseEntity<>(bookDTOList, HttpStatus.OK);
	}
	
	@GetMapping(value="/books/findAll")
	public Iterable<ElasticBook> findAllBooks(){
		return elasticBookRepository.findAll();
	}
	
	@GetMapping(value="/findByBookTitle/{bookTitle}")
	public List<ElasticBook> findByBookTitle(@PathVariable String bookTitle){
		return elasticBookRepository.findByBookTitle(bookTitle);
	}
	
	@GetMapping(value="/findByAuthorName/{authorName}")
	public List<ElasticBook> findByAuthorName(@PathVariable String authorName){
		return elasticBookRepository.findByAuthorName(authorName);
	}
	
	@GetMapping(value="/findByGenre/{genre}")
	public List<ElasticBook> findByGenre(@PathVariable String genre){
		return elasticBookRepository.findByGenre(genre);
	}
	
}
