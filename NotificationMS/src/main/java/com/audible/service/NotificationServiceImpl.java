package com.audible.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.audible.dto.BookDTO;
import com.audible.dto.NotificationDTO;
import com.audible.entity.Notification;
import com.audible.exception.AudibleException;
import com.audible.repository.NotificationRepository;


@Service(value="notificationService")
@Transactional
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	private NotificationRepository notificationRepository;
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Override
	public void addBooksToNotification(String bookId, String userId) throws AudibleException{
		Optional<Notification> notificationOptional=notificationRepository.findByUserIdAndBookId(userId, bookId);
		if(notificationOptional.isPresent()) {
			throw new AudibleException("NotificationService.BOOK_ALREADY_IN_NOTIFICATION");
		}
		Notification notification=new Notification();
		notification.setBookId(bookId);
		notification.setUserId(userId);
		notificationRepository.save(notification);
	}
	
	@Override
	public List<NotificationDTO> getAllBooksFromNotification(String userId) throws AudibleException{
		List<Notification> notificationList=notificationRepository.findByUserId(userId);
		if(notificationList.isEmpty()) {
			throw new AudibleException("NotificationService.EMPTY_NOTIFICATION");
		}
		List<NotificationDTO> notificationDTOList=new ArrayList<>();
		for(Notification notification: notificationList) {
			NotificationDTO notificationDTO=new NotificationDTO();
			notificationDTO.setNotificationId(notification.getNotificationId());
			notificationDTO.setUserId(notification.getUserId());
			BookDTO bookDTO=
					restTemplate().getForObject("http://localhost:6200/audible/book-api/book/"+notification.getBookId(), BookDTO.class);
			notificationDTO.setBookDTO(bookDTO);
			notificationDTOList.add(notificationDTO);
		}
		return notificationDTOList;
	}
	
	@Override
	public void deleteBookFromNotification(String userId, String bookId) throws AudibleException{
		Optional<Notification> notificationOptional=notificationRepository.findByUserIdAndBookId(userId, bookId);
		if(notificationOptional.isEmpty()) {
			throw new AudibleException("NotificationService.NO_BOOK_FOUND");
		}
		notificationRepository.delete(notificationOptional.get());
	}
	
	
	@Override
	public void deleteAllBooksFromNotification(String userId) throws AudibleException{
		List<Notification> notificationList=notificationRepository.findByUserId(userId);
		if(notificationList.isEmpty()) {
			throw new AudibleException("NotificationService.NO_BOOK_FOUND");
		}
		for(Notification notification:notificationList) {
			notificationRepository.delete(notification);
		}
	}
	
}
