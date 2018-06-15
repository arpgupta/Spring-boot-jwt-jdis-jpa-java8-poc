package com.notification.ex.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notification.ex.dto.ErrorDTO;
import com.notification.ex.dto.NotificationDTO;
import com.notification.ex.entity.Notification;
import com.notification.ex.security.GetTokenUser;
import com.notification.ex.service.NotificationService;

@RestController
@RequestMapping(path = "/p/notification/")
public class NotificationController {

	@Autowired
	private GetTokenUser getTokenUser;

	@Autowired
	NotificationService notificationService;

	@PostMapping
	public  Map<String, Object> saveNotification(@Valid @RequestBody NotificationDTO notification,Errors errors,HttpServletRequest httpServletRequest) {
		Map<String,Object> map = new HashMap<>(); 
		ErrorDTO result = new ErrorDTO();
		if (errors.hasErrors()) {
            result.setMsg(errors.getAllErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.joining(",")));
            result.setStatus(HttpStatus.BAD_REQUEST.toString());
            map.put("Http Status", result);
            return map;
        }
		Long id = getTokenUser.getUserToken(httpServletRequest).getId();
		map=notificationService.saveNotification(id, notification);
		return map;
	}

	@PutMapping(path = "/updateNotification/{notificationId}")
	public Map<String, Object> updateUser(@PathVariable(value = "notificationId") Long notificationId,@Valid @RequestBody NotificationDTO notification,
			Errors errors,HttpServletRequest httpServletRequest) throws Exception {
		
		Map<String,Object> map = new HashMap<>(); 
		ErrorDTO result = new ErrorDTO();
		if (errors.hasErrors()) {
            result.setMsg(errors.getAllErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.joining(",")));
            result.setStatus(HttpStatus.BAD_REQUEST.toString());
            map.put("Http Status", result);
            return map;
        }
		Long id = getTokenUser.getUserToken(httpServletRequest).getId();
		if(notificationId!=null){
			map=notificationService.updateNotification(notification, id, notificationId);
		}else{
			 result.setStatus(HttpStatus.BAD_REQUEST.toString());
			 result.setMsg("notificationId is required");
	         map.put("Http Status", result);
		}
		return map;
	}

	@GetMapping
	public Map<String, Object> getNotification(HttpServletRequest httpServletRequest) {
		Long id = getTokenUser.getUserToken(httpServletRequest).getId();
		return notificationService.findByUserId(id);
	}
}
