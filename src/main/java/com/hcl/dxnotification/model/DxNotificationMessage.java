package com.hcl.dxnotification.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class DxNotificationMessage {

    private Long notificationId;
    private String userId;
    private String title;
    private String message;
    private LocalDateTime expiryDate;
    private String sourceAppId;

    public String getSourceAppId() {
		return sourceAppId;
	}

	public void setSourceAppId(String sourceAppId) {
		this.sourceAppId = sourceAppId;
	}

	public DxNotificationMessage() {
    }

    public DxNotificationMessage(String userId, String title, String message, LocalDateTime expiryDate, String sourceAppId) {
        this.userId = userId;
        this.title = title;
        this.message = message;
        this.expiryDate = expiryDate;
        this.sourceAppId = sourceAppId;
    }

    // Getters and Setters
    @JsonProperty
    public Long getNotificationId() {
        return notificationId;
    }

    
    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return "DxNotificationMessage{" +
                "notificationId=" + notificationId +
                ", userId='" + userId + '\'' +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", expiryDate=" + expiryDate + '\''+
                ", sourceAppId=" + sourceAppId + 
                '}';
    }
}
