package com.notification.ex.dto;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class LoginDTO {

	long id;
	@NotBlank(message = "username can't empty!")
	@Size(min=0,max=12,message="username should  not more than the 12 char")
	private String userName;
	@NotBlank(message = "password can't empty!")
	@Size(min=0,max=12,message="passord should  not more than the 12 char")
	private String password;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
