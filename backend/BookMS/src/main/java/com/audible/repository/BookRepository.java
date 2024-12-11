package com.audible.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.audible.entity.Book;

@Repository
public interface BookRepository extends MongoRepository<Book, String>, PagingAndSortingRepository<Book, String>{
	
	@Query("{'bookTitle':?0}")
	Book findByBookTitle(String bookTitle);
	
	@Query("{'genre':?0}")
	List<Book> findBookByGenre(String genre);
}
