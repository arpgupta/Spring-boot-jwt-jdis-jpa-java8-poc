package com.notification.ex.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.coyote.http2.Http2Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import com.mysql.fabric.Response;
import com.notification.ex.dto.ErrorDTO;
import com.notification.ex.dto.UserDTO;
import com.notification.ex.entity.User;
import com.notification.ex.repository.UserRepository;
import com.notification.ex.validation.CustomValidation;

import redis.clients.jedis.Jedis;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CustomValidation customValidation;

	public Map<String, Object> addData(UserDTO user, Errors errors) {

		Map<String, Object> map = new HashMap<>();
		String uName = customValidation.uuid();
		String pass = customValidation.uuid();
		User n = new User();
		ErrorDTO result = new ErrorDTO();
		try {
			n.setName(user.getName());
			n.setUserName(uName.toString());
			n.setPassword(pass.toString());
			n.setAddress(user.getAddress());

			if (customValidation.isPhoneNumberValid(user.getMobileNo()) == true) {
				result.setMsg("mobile No should be valid");
				result.setStatus(HttpStatus.BAD_REQUEST.toString());
				map.put("Http Status", result);
			} else {
				n.setMobileNo(user.getMobileNo());
			}
			if (userRepository.findByEmail(user.getEmail()) == null) {
				n.setEmail(user.getEmail());
				userRepository.save(n);
				result.setMsg("user has successfully registered");
				result.setStatus(HttpStatus.CREATED.toString());
				map.put("Http Status", result);
				map.put("UserName", uName);
				map.put("password", pass);
				return map;
			} else {
				result.setMsg("Email should be unique");
				result.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.toString());
				map.put("Http Status", result);
				return map;
			}
		} catch (RuntimeException e) {
			result.setStatus(e.toString());
			map.put("httpstatus", result);
			e.printStackTrace();
		}
		return map;

	}

	public List<User> getAllUsersData() {
		return (List<User>) userRepository.findAll();
	}

	public Map<String, Object> updateUserData(UserDTO userDTO, Long id, Errors errors) {
		Map<String, Object> map = new HashMap<>();
		ErrorDTO result = new ErrorDTO();
		User n = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User is not found"));
		try {

			n.setName(userDTO.getName());
			n.setAddress(userDTO.getAddress());
			if (customValidation.isPhoneNumberValid(userDTO.getMobileNo()) == true) {
				n.setMobileNo(userDTO.getMobileNo());
				result.setMsg("user details has been successfully updated");
				result.setStatus(HttpStatus.CREATED.toString());
				map.put("Http Status", result);
				return map;
			} else {
				result.setMsg("mobile No should be valid");
				result.setStatus(HttpStatus.BAD_REQUEST.toString());
				map.put("Http Status", result);
				return map;
			}
		} catch (RuntimeException e) {
			result.setStatus(e.toString());
			map.put("httpstatus", result);
			e.printStackTrace();
		}
		return map;
	}

	public Map<String, Object> getUserByIdForAPI(Long noteId) {

		User user = userRepository.findById(noteId).orElseThrow(() -> new ResourceNotFoundException("error"));
		Map<String, Object> map = new HashMap<>();
		try {
			map.put("name", user.getName());
			map.put("address", user.getAddress());
			map.put("mobileNo", user.getMobileNo());
			map.put("Email", user.getEmail());
			map.put("userName", user.getUserName());
			map.put("Status", HttpStatus.OK.toString());
			return map;
		} catch (Exception e) {
			map.put("message", e.getMessage());
			map.put("httpstatus", HttpStatus.BAD_REQUEST);
			e.printStackTrace();
		}
		return map;
	}

	public User getUserById(Long noteId) {
		return userRepository.findById(noteId).orElseThrow(() -> new ResourceNotFoundException("error"));
	}

	public List<User> getByUserName(String username) {

		return userRepository.findByUserName(username);
	}

	public User getUserData(String userName, String password) {

		User user = userRepository.findByUserNameAndPassword(userName, password);
		return user;
	}

	public boolean delUserFromRadis(Long id) {
		Jedis jedis = customValidation.redisStart();
		String key = jedis.get(String.valueOf(id));
		System.out.println("============" + key + "===============");
		try {
			if (key != null) {
				jedis.del(String.valueOf(id));
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			throw new UsernameNotFoundException("key is not deleted in redis");
		} finally {
			jedis.close();
		}

	}
}
