package com.notification.ex.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.notification.ex.entity.Notification;
import com.notification.ex.entity.User;

@Repository
public interface NotificationRepository extends CrudRepository<Notification, Long> {
	Notification findByUserAndNotificationId(User userId, Long notificationId);

	List<Notification> findByUser(User user);
}
