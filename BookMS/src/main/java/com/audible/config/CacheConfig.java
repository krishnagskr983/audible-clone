package com.audible.config;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.audible.dto.BookDTO;
import com.audible.service.BookServiceImpl;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;

@Configuration
public class CacheConfig {

	@Autowired
	private BookServiceImpl bookServiceImpl;
	
	private CacheLoader<String, List<BookDTO>> cache=new CacheLoader<String, List<BookDTO>>(){
		
		@Override
		public List<BookDTO> load(String genre) throws Exception{
			return bookServiceImpl.getBooksByGenre(genre);
		}
	};
	
	private com.google.common.cache.LoadingCache<String, List<BookDTO>> cacheBook=CacheBuilder
			.newBuilder()
			.expireAfterAccess(20, TimeUnit.SECONDS)
			.build(cache);
	
	public List<BookDTO> getBooksByGenre(String genre) throws ExecutionException{
		return cacheBook.get(genre);
	}
}
