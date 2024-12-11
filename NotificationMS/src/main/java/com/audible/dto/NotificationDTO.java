package com.audible.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationDTO {

	private String notificationId;
	private String userId;
	private BookDTO bookDTO;
	
}
