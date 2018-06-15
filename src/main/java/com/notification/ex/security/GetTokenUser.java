package com.notification.ex.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import com.notification.ex.dto.LoginDTO;
import com.notification.ex.validation.JwtTokenIsMissing;
import com.notification.ex.validation.UserIsNotFound;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class GetTokenUser {

	public LoginDTO getUserToken(HttpServletRequest httpServletRequest){
		String header = httpServletRequest.getHeader("Authorization");
		if (header == null || !header.startsWith("Token ")) {
			throw new JwtTokenIsMissing("jwt Token is Missing");
		}
		String token = header.substring(6);
		LoginDTO log = null;
		try {

			Claims body = Jwts.parser().setSigningKey("mytoken").parseClaimsJws(token).getBody();
			if (body.get("id") != null) {
				log = new LoginDTO();
				log.setUserName(body.getSubject());
				log.setId(Long.parseLong((body.get("id") + "")));
				log.setPassword(String.valueOf(body.get("pass")));
			} else {
				throw new UserIsNotFound("user id is not found in token");
			}

		} catch (Exception e) {
			System.out.println(e);
		}

		return log;
	}
}
