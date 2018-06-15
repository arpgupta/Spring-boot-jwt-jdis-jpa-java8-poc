package com.notification.ex.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.junit.runner.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.notification.ex.dto.ErrorDTO;
import com.notification.ex.dto.UserDTO;
import com.notification.ex.entity.User;
import com.notification.ex.security.GetTokenUser;
import com.notification.ex.service.UserService;

@RestController
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private GetTokenUser getTokenUser;

	
	
	@PostMapping(path = "/add")
    public Map<String,Object> registerUser(@Valid @RequestBody  UserDTO user, Errors errors) {
		Map<String,Object> map = new HashMap<>(); 
		ErrorDTO result = new ErrorDTO();
		if (errors.hasErrors()) {
            result.setMsg(errors.getAllErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.joining(",")));
            result.setStatus(HttpStatus.BAD_REQUEST.toString());
            map.put("Http Status", result);
            return map;
        }
        return userService.addData(user, errors);
    }
	@GetMapping(path = "/p/all")
	public List<User> getAllUsers() {
		return userService.getAllUsersData();
	}

	@PutMapping(path = "/p/update/")
	public Map<String,Object> updateUser(@Valid @RequestBody UserDTO user, Errors errors,HttpServletRequest httpServletRequest) {
		
		Map<String,Object> map = new HashMap<>(); 
		ErrorDTO result = new ErrorDTO();
		if (errors.hasErrors()) {
            result.setMsg(errors.getAllErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.joining(",")));
            result.setStatus(HttpStatus.BAD_REQUEST.toString());
            map.put("Http Status", result);
            return map;
        }
		Long id = getTokenUser.getUserToken(httpServletRequest).getId();
		return userService.updateUserData(user, id,errors);
	}

	@GetMapping("/p/find/")
	public Map<String,Object> getUserById(HttpServletRequest httpServletRequest) {
		Long id = getTokenUser.getUserToken(httpServletRequest).getId();
		return userService.getUserByIdForAPI(id);
	}

	@GetMapping("/p/findByUserName/{name}")
	public List<User> getByUserName(@PathVariable(value = "name") String userName) {
		return userService.getByUserName(userName);
	}
}
