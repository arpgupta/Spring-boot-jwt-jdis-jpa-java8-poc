package com.notification.ex.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.notification.ex.dto.ErrorDTO;
import com.notification.ex.dto.LoginDTO;
import com.notification.ex.entity.User;
import com.notification.ex.security.GetTokenUser;
import com.notification.ex.security.JwtGenerator;
import com.notification.ex.service.UserService;
import com.notification.ex.validation.CustomValidation;
import com.notification.ex.validation.UserIsNotFound;

import redis.clients.jedis.Jedis;

@RestController
public class LoginController {
	@Autowired
	private UserService userService;

	@Autowired
	private JwtGenerator jwtGenerator;

	@Autowired
	private GetTokenUser getTokenUser;

	@Autowired
	private CustomValidation customValidation;

	@PostMapping("/login")
	public Map<String, Object> loginController(@Valid @RequestBody LoginDTO loginDTO, Errors errors)
			throws UserIsNotFound {

		Map<String, Object> map = new HashMap<>();
		ErrorDTO result = new ErrorDTO();
		String token = null;
		Jedis Jedis = customValidation.redisStart();
		try {
			if (errors.hasErrors()) {
				result.setMsg(errors.getAllErrors().stream().map(x -> x.getDefaultMessage())
						.collect(Collectors.joining(",")));
				result.setStatus(HttpStatus.BAD_REQUEST.toString());
				map.put("Http Status", result);
				return map;
			}
			User user = userService.getUserData(loginDTO.getUserName(), loginDTO.getPassword());
			if (user != null) {
				String tok = Jedis.get(String.valueOf(user.getId()));
				if (tok == null) {
					token = jwtGenerator.generate(user);
					result.setMsg("login successfully");
					result.setStatus(HttpStatus.ACCEPTED.toString());
					map.put("token", token);
					map.put("Http Status", result);
				} else {
					result.setMsg("already login");
					result.setStatus(HttpStatus.ALREADY_REPORTED.toString());
					map.put("Http Status", result);
				}
			} else {
				result.setMsg("user is not found ");
				result.setStatus(HttpStatus.BAD_REQUEST.toString());
				map.put("Http Status", result);
				throw new ResourceNotFoundException("user not found");
			}
		} catch (Exception e) {
			map.put("message", e.getMessage());
			map.put("httpstatus", HttpStatus.BAD_REQUEST);
			e.printStackTrace();

		} finally {
			Jedis.close();

		}
		return map;

	}

	@PostMapping("/p/logout")
	public Map<String, Object> logOutAction(HttpServletRequest httpServletRequest) {

		Map<String, Object> map = new HashMap<>();
		try {
			Long id = getTokenUser.getUserToken(httpServletRequest).getId();
			Boolean b = userService.delUserFromRadis(id);
			if (b == true) {
				map.put("message", "login successfully");
				map.put("httpstatus", HttpStatus.OK);
			} else {
				map.put("message", "Already logout");
				map.put("httpstatus", HttpStatus.OK);
			}
			return map;
		} catch (Exception e) {
			map.put("message", e.getMessage());
			map.put("httpstatus", HttpStatus.BAD_REQUEST);
			e.printStackTrace();
		}

		return map;
	}
}
