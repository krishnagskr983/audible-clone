package com.audible.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.audible.entity.ElasticBook;

@Repository
public interface ElasticBookRepository extends ElasticsearchRepository<ElasticBook, String> {
	
	List<ElasticBook> findByBookTitle(String bookTitle);
	List<ElasticBook> findByAuthorName(String authorName);
	List<ElasticBook> findByGenre(String genre);
}
