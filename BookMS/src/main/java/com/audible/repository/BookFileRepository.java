package com.audible.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.audible.entity.BookFile;

@Repository
public interface BookFileRepository extends MongoRepository<BookFile, String>{

	@Query("{'fileName':?0}")
	Optional<BookFile> findByFilePathName(String fileName);
}
