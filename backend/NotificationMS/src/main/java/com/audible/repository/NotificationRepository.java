package com.audible.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.audible.entity.Notification;



@Repository
public interface NotificationRepository extends MongoRepository<Notification, String>{

	@Query("{'userId':?0, 'bookId':?1}")
	Optional<Notification> findByUserIdAndBookId(String userId, String bookId);
	
	@Query("{'userId':?0}")
	List<Notification> findByUserId(String userId);
}
