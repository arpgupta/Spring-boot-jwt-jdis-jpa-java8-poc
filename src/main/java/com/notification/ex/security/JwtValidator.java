package com.notification.ex.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.notification.ex.dto.LoginDTO;
import com.notification.ex.entity.User;
import com.notification.ex.service.UserService;
import com.notification.ex.validation.CustomValidation;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import redis.clients.jedis.Jedis;

@Component
public class JwtValidator {
	private String secret = "mytoken";

	@Autowired
	private UserService userService;
	
	@Autowired 
	private CustomValidation customValidation;
		
	
	public User validate(String token) {
		Jedis jedis = customValidation.redisStart();
		User login = null;
		try {
			Claims body = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
			List<String> tokenInredis = jedis.mget(String.valueOf(body.get("id")));
			for (String t : tokenInredis) {
				if (t == null) {
					System.out.println("=======Tokenvalidator======" + t);
					throw new UsernameNotFoundException("toking is not valid");

				}
				System.out.println("=======Tokenvalidator======" + t);
			}

			if (String.valueOf(body.get("id")) != null) {
				login = userService.getUserById(Long.parseLong(String.valueOf(body.get("id"))));
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		
		jedis.close();
		return login;
	}
}
