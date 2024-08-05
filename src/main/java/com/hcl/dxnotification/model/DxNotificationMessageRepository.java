package com.hcl.dxnotification.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DxNotificationMessageRepository extends JpaRepository<DxNotificationMessage, Long> {
    
    @Query("SELECT n FROM DxNotificationMessage n WHERE n.expiryDate > :currentDate AND n.userId = :userId")
    List<DxNotificationMessage> findAllNonExpiredByUserId(@Param("userId") String userId, @Param("currentDate") LocalDateTime currentDate);
}
