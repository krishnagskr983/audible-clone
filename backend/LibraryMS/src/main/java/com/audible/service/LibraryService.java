package com.audible.service;

import java.util.List;

import com.audible.dto.LibraryDTO;
import com.audible.exception.AudibleException;

public interface LibraryService {
	
	void addBooksToLibrary(String bookId, String userId) throws AudibleException;

	List<LibraryDTO> getAllBooksFromLibrary(String userId) throws AudibleException;
	
	void deleteBookFromLibrary(String userId, String bookId) throws AudibleException;
	
	void deleteAllBooksFromLibrary(String userId) throws AudibleException;
}
