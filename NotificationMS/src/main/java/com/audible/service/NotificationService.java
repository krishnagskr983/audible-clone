package com.audible.service;

import java.util.List;

import com.audible.dto.NotificationDTO;
import com.audible.exception.AudibleException;

public interface NotificationService {
	
	void addBooksToNotification(String bookId, String userId) throws AudibleException;
	
	List<NotificationDTO> getAllBooksFromNotification(String userId) throws AudibleException;
	
	void deleteBookFromNotification(String userId, String bookId) throws AudibleException;
	
	void deleteAllBooksFromNotification(String userId) throws AudibleException;
}
