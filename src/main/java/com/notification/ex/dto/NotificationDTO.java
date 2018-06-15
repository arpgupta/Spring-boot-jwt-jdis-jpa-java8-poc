package com.notification.ex.dto;

import java.util.Date;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.notification.ex.entity.User;

public class NotificationDTO {
	private Long id;
	private User user_Id;
	
	@Size(min=0,max=100,message="title should be less  then 100 char")
	@NotBlank(message = "title can't empty!")
	private String title;
	
	@Size(min=0,max=500,message="description should be less  then 500 char")
	@NotBlank(message = "description can't empty!")
	@Size(min = 0, max = 500)
	private String description;

	private Date createBy;

	private Date updateBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser_Id() {
		return user_Id;
	}

	public void setUser_Id(User user_Id) {
		this.user_Id = user_Id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Date createBy) {
		Date d = new Date();
		this.createBy = d;
	}

	public Date getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(Date updateBy) {
		Date d = new Date();
		this.updateBy = d;
	}

}
