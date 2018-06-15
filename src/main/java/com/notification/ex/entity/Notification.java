package com.notification.ex.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

@Entity
public class Notification {
	@Id
	@GeneratedValue
	private Long notificationId;

	@Size(min = 0, max = 100)
	private String title;

	@Size(min = 0, max = 500)
	private String description;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createBy;
	@Temporal(TemporalType.TIMESTAMP)
	private Date UpdatedBy;
	
	
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;

	public Long getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(Long notificationId) {
		this.notificationId = notificationId;
	}

	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
