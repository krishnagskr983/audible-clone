package com.audible.repository;

import com.audible.entity.UserFile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserFileRepository extends MongoRepository<UserFile, String>{

    @Query("{'fileName':?0}")
    Optional<UserFile> findByFilePathName(String fileName);
}