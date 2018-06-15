package com.notification.ex.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.notification.ex.dto.CustomNotificationDTO;
import com.notification.ex.dto.ErrorDTO;
import com.notification.ex.dto.NotificationDTO;
import com.notification.ex.entity.Notification;
import com.notification.ex.entity.User;
import com.notification.ex.repository.NotificationRepository;
import com.notification.ex.repository.UserRepository;

@Service
public class NotificationService {
	@Autowired
	private NotificationRepository notificationRepository;
	@Autowired
	private UserRepository userRepository;

	public Map<String, Object> saveNotification(Long userId, NotificationDTO notification) {
		Map<String, Object> map = new HashMap<>();
		ErrorDTO result = new ErrorDTO();
		Notification n = new Notification();
		try {
			User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User"));
			n.setUser(user);
			n.setDescription(notification.getDescription());
			n.setTitle(notification.getTitle());
			n.setCreateBy(new Date());
			notificationRepository.save(n);
			result.setMsg("user details has been successfully updated");
			result.setStatus(HttpStatus.CREATED.toString());
			map.put("Http Status", result);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(e.toString());
			map.put("httpstatus", result);
			e.printStackTrace();
			return map;
		}
		return map;
	}

	public Map<String, Object> updateNotification(NotificationDTO notification, Long userId, Long notificationId)
			throws Exception {
		Map<String, Object> map = new HashMap<>();
		ErrorDTO result = new ErrorDTO();
		try {
			User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User"));
			Notification notificationData = notificationRepository.findByUserAndNotificationId(user, notificationId);
			notificationData.setDescription(notification.getDescription());
			notificationData.setTitle(notification.getTitle());
			notificationData.setUpdatedBy(new Date());
			notificationRepository.save(notificationData);
			result.setMsg("Notification has been successfully updated");
			result.setStatus(HttpStatus.CREATED.toString());
			map.put("Http Status", result);

		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(e.toString());
			map.put("httpstatus", result);
			e.printStackTrace();
			return map;
		}
		return map;
	}

	public Map<String, Object> findByUserId(Long userId) {
		Map<String, Object> map = new HashMap<>();
		List<CustomNotificationDTO> cn = new ArrayList<CustomNotificationDTO>();
		try {
			User user = userRepository.findById(userId)
					.orElseThrow(() -> new ResourceNotFoundException("User id is Not valid"));
			List<Notification> n = notificationRepository.findByUser(user);
			if (n != null) {
				for(Notification s: n){
					CustomNotificationDTO cndto = new CustomNotificationDTO();
					cndto.setNotificationId(s.getNotificationId());
					cndto.setTitle(s.getTitle());
					cndto.setDescription(s.getDescription());
					cndto.setCreateBy(s.getCreateBy());
					cndto.setUpdatedBy(s.getUpdatedBy());
					cn.add(cndto);
				}
				map.put("Results", cn);
				map.put("Status", HttpStatus.OK.toString());
				return map;
			} else {
				map.put("message", "No Notification unavailable for this user");
				map.put("Status", HttpStatus.BAD_REQUEST);
				return map;
			}
		} catch (Exception e) {
			map.put("error", e.toString());
			map.put("httpstatus", HttpStatus.BAD_REQUEST.toString());
			e.printStackTrace();
			return map;
		}
	}

}
