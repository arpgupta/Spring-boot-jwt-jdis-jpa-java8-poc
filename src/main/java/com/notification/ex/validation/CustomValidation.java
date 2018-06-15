package com.notification.ex.validation;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
@Component
public class CustomValidation {

	public   Boolean isEmailValid(String email) {
		boolean isValid = false;
		String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		CharSequence inputStr = email;
		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isValid = true;
		}
		return isValid;
	}

	public   Boolean isPhoneNumberValid(String phoneNumber) {
		Boolean isValid = false;
		String expression = "^[0-9]{10}$";
		CharSequence inputStr = phoneNumber;
		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isValid = true;
		}
		return isValid;
	}

	public  Boolean isValidStringLessThan100(String name) {
		boolean isValid = false;
		if (name != null && name != "" && name.length() < 100 ) {
			isValid = true;
		}
		return isValid;
	}

	public  Boolean isValidStringLessThan500(String address) {
		boolean isValid = false;
		if (address != null && address != ""&&address.length() < 500 ) {
			isValid = true;
		}
		return isValid;
	}
	public String uuid(){
		UUID u= UUID.randomUUID();
		return u.toString().replaceAll("-", "").substring(0, 12);
	}
	
	public Jedis redisStart(){
		final Jedis jedis = new Jedis("localhost");
		return jedis;
	}
}
