package com.audible.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.audible.dto.LibraryDTO;
import com.audible.exception.AudibleException;
import com.audible.service.LibraryService;

@CrossOrigin
@RestController
@Validated
@RequestMapping(value="/library-api")
public class LibraryAPI {

	@Autowired
	private LibraryService libraryService;
	
	@Autowired
	private Environment environment;
	
	static Logger logger=LoggerFactory.getLogger(LibraryAPI.class);
	
	@PostMapping(value="/library/add-book/{bookId}/user/{userId}")
	public ResponseEntity<LibraryDTO> addBooksToLibrary(@PathVariable String bookId, @PathVariable String userId)
	throws AudibleException{
		libraryService.addBooksToLibrary(bookId, userId);
		String message=environment.getProperty("LibraryAPI.ADD_TO_LIBRARY_SUCCESS");
		LibraryDTO libraryDTO = new LibraryDTO();
		return new ResponseEntity<>(libraryDTO, HttpStatus.CREATED);
	}
	
	@GetMapping(value="/library/get-books/user/{userId}")
	public ResponseEntity<List<LibraryDTO>> getAllBooksFromLibrary(@PathVariable String userId)
	throws AudibleException{
		List<LibraryDTO> libraryDTOList=libraryService.getAllBooksFromLibrary(userId);
		return new ResponseEntity<>(libraryDTOList, HttpStatus.OK);
	}
	
	@DeleteMapping(value="/library/delete-book/{bookId}/user/{userId}")
	public ResponseEntity<String> deleteBookFromLibrary(@PathVariable String userId, 
			@PathVariable String bookId) throws AudibleException{
		libraryService.deleteBookFromLibrary(userId, bookId);
		String message=environment.getProperty("LibraryAPI.DELETED_BOOK_SUCCESS");
		return new ResponseEntity<>(message, HttpStatus.OK);
	}
	
	@DeleteMapping(value="/library/delete-books/user/{userId}")
	public ResponseEntity<String> deleteAllBooksFromLibrary(@PathVariable String userId)
	throws AudibleException{
		libraryService.deleteAllBooksFromLibrary(userId);
		String message=environment.getProperty("LibraryAPI.DELETED_ALL_BOOKS_SUCCESS");
		return new ResponseEntity<>(message, HttpStatus.OK);
	}

}
