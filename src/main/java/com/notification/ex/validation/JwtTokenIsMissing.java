package com.notification.ex.validation;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

public class JwtTokenIsMissing  extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public JwtTokenIsMissing(String h) {
		super(h);
	}

	
}
