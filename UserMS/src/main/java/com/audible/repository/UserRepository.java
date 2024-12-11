package com.audible.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.audible.entity.User;

@Repository
public interface UserRepository extends MongoRepository<User, String>{

	@Query("{'emailId':?0}")
	Optional<User> findByEmailId(String emailId);
}
