package com.audible.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.audible.dto.BookDTO;
import com.audible.exception.AudibleException;

public interface BookService {

	String addNewBook(MultipartFile audio, MultipartFile photo, BookDTO bookDTO) throws Exception;
	
	BookDTO getBookById(String bookId) throws AudibleException;
	
	List<BookDTO> getAllBooks(Integer pageNumber, Integer pageSize) throws AudibleException;
	
	List<BookDTO> getBooksByGenre(String category) throws AudibleException;
	
}
