package com.hcl.dxnotification.model;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

@Entity
public class DxNotificationMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    private Long notificationId;

    private String userId;
    private String title;
    private String message;
    private LocalDateTime expiryDate;

    public DxNotificationMessage() {
    }

    public DxNotificationMessage(String clientId, String title, String message, LocalDateTime expiryDate) {
        this.userId = clientId;
        this.title = title;
        this.message = message;
        this.expiryDate = expiryDate;
    }

    // Getters and Setters
    @JsonProperty
    public Long getNotificationId() {
        return notificationId;
    }
    
    @JsonIgnore
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

    public void setExpiryDate(LocalDateTime timestamp) {
        this.expiryDate = timestamp;
    }

    @Override
    public String toString() {
        return "DxNotificationMessage{" +
                "notificationId=" + notificationId +
                ", userId='" + userId + '\'' +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", expiryDate=" + expiryDate +
                '}';
    }
}
