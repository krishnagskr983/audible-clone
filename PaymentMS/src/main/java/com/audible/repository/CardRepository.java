package com.audible.repository;

import com.audible.entity.Card;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends MongoRepository<Card, String> {

    @Query("{'userId:?0'}")
    List<Card> findAllById(String userId);
}
