package com.audible.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.audible.entity.Library;

@Repository
public interface LibraryRepository extends MongoRepository<Library, String>{

	@Query("{'userId':?0, 'bookId':?1}")
	Optional<Library> findByUserIdAndBookId(String userId, String bookId);
	
	@Query("{'userId':?0}")
	List<Library> findByUserId(String userId);
}
