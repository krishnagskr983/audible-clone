package com.audible.service;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.audible.dto.BookDTO;
import com.audible.dto.BookFileDTO;
import com.audible.entity.Book;
import com.audible.entity.BookFile;
import com.audible.entity.ElasticBook;
import com.audible.exception.AudibleException;
import com.audible.kafka.KafkaProducer;
import com.audible.repository.BookFileRepository;
import com.audible.repository.BookRepository;
import com.audible.repository.ElasticBookRepository;

@Service(value="bookService")
@Transactional
public class BookServiceImpl implements BookService{

	@Autowired 
	private BookRepository bookRepository;
	
	@Autowired
	private BookFileRepository bookFileRepository;
	
	@Autowired
	private KafkaProducer kafkaProducer;
	
	@Autowired
	private ElasticBookRepository elasticBookRepository;
	
	private static final String PHOTO_PATH="../../assets/Book_Photos/";
	private static final String AUDIO_PATH="../../assets/Book_Audios/";
	private static final String PHOTO_FILE_PATH="C:/MyProjects/Audible_Project/Audible_Frontend/src/assets/Book_Photos/";
	private static final String AUDIO_FILE_PATH="C:/MyProjects/Audible_Project/Audible_Frontend/src/assets/Book_Audios/";

	@Override
	public String addNewBook(MultipartFile audio, MultipartFile photo, BookDTO bookDTO) throws IOException, AudibleException{
		Book bookRegistered = bookRepository.findByBookTitle(bookDTO.getBookTitle());
		if(bookRegistered!=null)
			throw new AudibleException("BookService.BOOK_ALREADY_EXISTS");
		Book book=convertToBook(audio, photo, bookDTO);
		bookRepository.save(book);
		kafkaProducer.sendMessage(book.getBookId());
		
//		ElasticBook elasticBook=new ElasticBook(book.getBookId(), book.getBookTitle(),
//				book.getBookOverview(), book.getBookPrice(), book.getAuthorName(), book.getNarratorName(),
//				book.getGenre(), book.getRatings(), book.getDescription(),
//				book.getLength(), book.getBookPhoto(), book.getBookAudio());
//		elasticBookRepository.save(elasticBook);
		return book.getBookId();
	}
	
	@Override
	public BookDTO getBookById(String bookId) throws AudibleException{
		Optional<Book> bookOptional = bookRepository.findById(bookId);
		Book book=bookOptional.orElseThrow(()-> new AudibleException("BookService.NO_BOOK_FOUND"));
		return BookServiceImpl.convertToBookDTO(book);
	}
	
	@Override
	public List<BookDTO> getAllBooks(Integer pageNumber, Integer pageSize) throws AudibleException{
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<Book> pageBook = bookRepository.findAll(pageable);
		
		if(pageBook.isEmpty()) {
			throw new AudibleException("BookService.NO_BOOK_FOUND");
		}
		List<BookDTO> listOfBookDTO = new ArrayList<>();
		List<Book> bookList = pageBook.getContent();
		for(Book book: bookList) {
			listOfBookDTO.add(BookServiceImpl.convertToBookDTO(book));
		}
		return listOfBookDTO;
	}
	
	@Override
	public List<BookDTO> getBooksByGenre(String genre) throws AudibleException{
		List<Book> bookOptional = bookRepository.findBookByGenre(genre);
		if(bookOptional.isEmpty())
			throw new AudibleException("BookService.NO_BOOK_FOUND");
		List<BookDTO> listOfBookDTO=new ArrayList<>();
		bookOptional.forEach((book)->{
			listOfBookDTO.add(BookServiceImpl.convertToBookDTO(book));
		});
		return listOfBookDTO;
	}
	
	public static BookDTO convertToBookDTO(Book book) {
		BookDTO bookDTO = new BookDTO();
		bookDTO.setBookId(book.getBookId());
		bookDTO.setBookTitle(book.getBookTitle());
		bookDTO.setBookOverview(book.getBookOverview());
		bookDTO.setBookPrice(book.getBookPrice());
		bookDTO.setAuthorName(book.getAuthorName());
		bookDTO.setNarratorName(book.getNarratorName());
		bookDTO.setGenre(book.getGenre());
	 	bookDTO.setRatings(book.getRatings());
		bookDTO.setDescription(book.getDescription());
		bookDTO.setLength(book.getLength());
		
		BookFileDTO bookPhotoDTO = new BookFileDTO();
		bookPhotoDTO.setBookFileId(book.getBookPhoto().getBookFileId());
		bookPhotoDTO.setName(book.getBookPhoto().getName());
		bookPhotoDTO.setType(book.getBookPhoto().getType());
		bookPhotoDTO.setFilePath(book.getBookPhoto().getFilePath());
		bookDTO.setBookPhotoDTO(bookPhotoDTO);
		
		BookFileDTO bookAudioDTO = new BookFileDTO();
		bookAudioDTO.setBookFileId(book.getBookAudio().getBookFileId());
		bookAudioDTO.setName(book.getBookAudio().getName());
		bookAudioDTO.setType(book.getBookAudio().getType());
		bookAudioDTO.setFilePath(book.getBookAudio().getFilePath());
		bookDTO.setBookAudioDTO(bookAudioDTO);
		
		return bookDTO;
		
	}

	public Book convertToBook(MultipartFile audio, MultipartFile photo, BookDTO bookDTO) throws IOException{
		Book book = new Book();
//		book.setBookId(bookDTO.getBookId());
		book.setBookTitle(bookDTO.getBookTitle());
		book.setBookOverview(bookDTO.getBookOverview());
		book.setBookPrice(bookDTO.getBookPrice());
		book.setAuthorName(bookDTO.getAuthorName());
		book.setNarratorName(bookDTO.getNarratorName());
		book.setGenre(bookDTO.getGenre());
		book.setRatings(bookDTO.getRatings());
		book.setDescription(bookDTO.getDescription());
		book.setLength(bookDTO.getLength());
		
		String photoFilePath = PHOTO_PATH+photo.getOriginalFilename();
		BookFile bookPhoto = bookFileRepository.save(BookFile.builder()
				.name(photo.getOriginalFilename())
				.type(photo.getContentType())
				.filePath(photoFilePath).build());
//		photo.transferTo(new File(PHOTO_FILE_PATH+photo.getOriginalFilename()));
		book.setBookPhoto(bookPhoto);
		
		String audioFilePath = AUDIO_PATH+audio.getOriginalFilename();
		BookFile bookAudio = bookFileRepository.save(BookFile.builder()
				.name(audio.getOriginalFilename())
				.type(audio.getContentType())
				.filePath(audioFilePath).build());
//		audio.transferTo(new File(AUDIO_FILE_PATH+audio.getOriginalFilename()));
		book.setBookAudio(bookAudio);
		return book;
	}
	
}
