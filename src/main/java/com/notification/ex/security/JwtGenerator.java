package com.notification.ex.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.notification.ex.entity.User;
import com.notification.ex.validation.CustomValidation;
import com.notification.ex.validation.UserIsNotFound;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import redis.clients.jedis.Jedis;

@Component
public class JwtGenerator {

	@Autowired 
	private CustomValidation customValidation;
	public String generate(User login) throws UserIsNotFound {
		Claims claims = null;
		Jedis jedis = customValidation.redisStart();
		try {

			claims = Jwts.claims().setSubject(login.getUserName());
			claims.put("id", login.getId());
			claims.put("role", login.getRole());
			claims.put("uuid", new Date().getTime());

		} catch (Exception e) {
			throw new UserIsNotFound("Token Is Not Genrated" + e);
		}

		String token = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, "mytoken")
				// .setExpiration(new Date(System.currentTimeMillis() +
				// 60*10000))
				.compact();
		if (jedis.get(String.valueOf(login.getId())) == null) {
			jedis.set(String.valueOf(login.getId()), token);
		}
		jedis.close();

		return token;
	}
}
