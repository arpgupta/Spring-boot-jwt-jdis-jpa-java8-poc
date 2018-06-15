package com.notification.ex.dto;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NotFound;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;



public class UserDTO {
	private Long id;
	
	@Size(min=0,max=100,message="name should be less  then 100 char")
	@NotBlank(message = "name can't empty!")	
	private String name;
	
	private String userName;
	private String password;
	
	@Size(min=0,max=100,message="Email should be less  then 100 char")
	@Email(message="Email should be valid")
	private String email;
	
	@Size(min=0,max=100,message="address should be less  then 500 char")
	@NotBlank(message = "address can't empty!")
	private String address;
	
	@NotBlank(message = "mobileNo can't empty!")
	@Size(min=0,max=10,message="mobileNo should be less  then 10 digit")
	private String mobileNo;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

}
