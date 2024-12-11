package com.audible.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.audible.dto.BookDTO;
import com.audible.dto.LibraryDTO;
import com.audible.entity.Library;
import com.audible.exception.AudibleException;
import com.audible.repository.LibraryRepository;



@Service(value="libraryService")
@Transactional
public class LibraryServiceImpl implements LibraryService {

	@Autowired
	private LibraryRepository libraryRepository;
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Override
	public void addBooksToLibrary(String bookId, String userId) throws AudibleException{
		Optional<Library> notificationOptional=libraryRepository.findByUserIdAndBookId(userId, bookId);
		if(notificationOptional.isPresent()) {
			throw new AudibleException("LibraryService.BOOK_ALREADY_IN_LIBRARY");
		}
		Library library=new Library();
		library.setBookId(bookId);
		library.setUserId(userId);
		libraryRepository.save(library);
	}

	@Override
	public List<LibraryDTO> getAllBooksFromLibrary(String userId) throws AudibleException{
		List<Library> libraryList=libraryRepository.findByUserId(userId);
		if(libraryList.isEmpty()) {
			throw new AudibleException("LibraryService.EMPTY_LIBRARY");
		}
		List<LibraryDTO> libraryDTOList=new ArrayList<>();
		for(Library library: libraryList) {
			LibraryDTO libraryDTO=new LibraryDTO();
			libraryDTO.setLibraryId(library.getLibraryId());
			libraryDTO.setUserId(library.getUserId());
			BookDTO bookDTO=
					restTemplate().getForObject("http://localhost:6200/audible/book-api/book/"+library.getBookId(), BookDTO.class);
			libraryDTO.setBookDTO(bookDTO);
			libraryDTOList.add(libraryDTO);
		}
		return libraryDTOList;
	}
	
	@Override
	public void deleteBookFromLibrary(String userId, String bookId) throws AudibleException{
		Optional<Library> libraryOptional=libraryRepository.findByUserIdAndBookId(userId, bookId);
		if(libraryOptional.isEmpty()) {
			throw new AudibleException("LibraryService.NO_BOOK_FOUND");
		}
		libraryRepository.delete(libraryOptional.get());
	}
	
	
	@Override
	public void deleteAllBooksFromLibrary(String userId) throws AudibleException{
		List<Library> libraryList=libraryRepository.findByUserId(userId);
		if(libraryList.isEmpty()) {
			throw new AudibleException("LibraryService.NO_BOOK_FOUND");
		}
		for(Library library:libraryList) {
			libraryRepository.delete(library);
		}
	}
	
}
