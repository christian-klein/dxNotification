package com.hcl.dxnotification.service;



import com.hcl.dxnotification.errorhandling.ResourceNotFoundException;
import com.hcl.dxnotification.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private DxNotificationMessageRepository repository;

    @Transactional(readOnly = true)
    public List<DxNotificationMessage> getNotificationsByUserId(String userId) {
        // Assuming userId corresponds to clientId in the notifications
        return repository.findAllNonExpiredByUserId(userId, LocalDateTime.now());
    }
    
    public DxNotificationMessage addNotification(DxNotificationMessage notification) {
    	notification.setNotificationId(null);
        return repository.save(notification);
    }
    
    @Transactional
    public void deleteNotification(Long id) {
    	
    	
    	 Optional<DxNotificationMessage> notification = repository.findById(id);
         if (!notification.isPresent()) {
             throw new ResourceNotFoundException("Notification not found with id " + id);
         }
         repository.deleteById(id);
    }
}
