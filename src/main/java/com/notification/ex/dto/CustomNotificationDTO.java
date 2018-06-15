package com.notification.ex.dto;

import java.util.Date;

public class CustomNotificationDTO {
	private Long notificationId;
	private String title;
	private String description;
	private Date createBy;
	private Date UpdatedBy;
	public Long getNotificationId() {
		return notificationId;
	}
	public void setNotificationId(Long notificationId) {
		this.notificationId = notificationId;
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
		this.createBy = createBy;
	}
	public Date getUpdatedBy() {
		return UpdatedBy;
	}
	public void setUpdatedBy(Date updatedBy) {
		UpdatedBy = updatedBy;
	}
}
