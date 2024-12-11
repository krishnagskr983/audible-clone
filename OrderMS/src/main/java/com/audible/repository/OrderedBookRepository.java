package com.audible.repository;

import com.audible.dto.OrderedBookDTO;
import com.audible.entity.OrderedBook;
import org.apache.catalina.Manager;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderedBookRepository extends MongoRepository<OrderedBook, String> {
}
