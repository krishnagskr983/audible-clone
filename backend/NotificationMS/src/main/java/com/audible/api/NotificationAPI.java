package com.audible.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.audible.dto.NotificationDTO;
import com.audible.exception.AudibleException;
import com.audible.service.NotificationService;

@CrossOrigin
@RestController
@Validated
@RequestMapping(value="/notification-api")
public class NotificationAPI {

	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private Environment environment;
	
	static Logger logger=LoggerFactory.getLogger(NotificationAPI.class);
	
	@PostMapping(value="/notification/add-book/{bookId}/user/{userId}")
	public ResponseEntity<String> addBooksToNotification(@PathVariable String bookId, @PathVariable String userId)
	throws AudibleException{
		notificationService.addBooksToNotification(bookId, userId);
		String message=environment.getProperty("NotificationAPI.ADD_TO_NOTIFICATION_SUCCESS");
		return new ResponseEntity<>(message, HttpStatus.CREATED);
	}
	
	@GetMapping(value="/notification/books/user/{userId}")
	public ResponseEntity<List<NotificationDTO>> getAllBooksFromNotification(@PathVariable String userId)
	throws AudibleException{
		List<NotificationDTO> notificationDTOList=notificationService.getAllBooksFromNotification(userId);
		return new ResponseEntity<>(notificationDTOList, HttpStatus.OK);
	}
	
	@DeleteMapping(value="/notification/delete-book/{bookId}/user/{userId}")
	public ResponseEntity<String> deleteBookFromNotification(@PathVariable String userId, 
			@PathVariable String bookId) throws AudibleException{
		notificationService.deleteBookFromNotification(userId, bookId);
		String message=environment.getProperty("NotificationAPI.DELETED_BOOK_SUCCESS");
		return new ResponseEntity<>(message, HttpStatus.OK);
	}
	
	@DeleteMapping(value="/notification/delete-books/user/{userId}")
	public ResponseEntity<String> deleteAllBooksFromNotification(@PathVariable String userId)
	throws AudibleException{
		notificationService.deleteAllBooksFromNotification(userId);
		String message=environment.getProperty("NotificationAPI.DELETED_ALL_BOOKS_SUCCESS");
		return new ResponseEntity<>(message, HttpStatus.OK);
	}

	
}
