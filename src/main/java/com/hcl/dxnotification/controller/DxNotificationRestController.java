package com.hcl.dxnotification.controller;


import com.hcl.dxnotification.errorhandling.ResourceNotFoundException;
import com.hcl.dxnotification.model.DxNotificationMessage;
import com.hcl.dxnotification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class DxNotificationRestController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/user/{userId}")
    public List<DxNotificationMessage> getNotificationsByUserId(@PathVariable String userId) {
        return notificationService.getNotificationsByUserId(userId);
    }
    @PostMapping("/add")
    public ResponseEntity<DxNotificationMessage> addNotification(@RequestBody DxNotificationMessage notification) {
        DxNotificationMessage savedNotification = notificationService.addNotification(notification);
        return new ResponseEntity<>(savedNotification, HttpStatus.CREATED);
    }
    @DeleteMapping("/{notificationId}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long notificationId) {
    	notificationService.deleteNotification(notificationId);
    	return ResponseEntity.noContent().build();
    }
    
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        // Customize the error response as needed
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The notification with the provided Id is not found");
    }
}
