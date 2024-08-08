package com.hcl.dxnotification.service;

import com.hcl.dxnotification.errorhandling.ResourceNotFoundException;
import com.hcl.dxnotification.model.DxNotificationMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

@Service
public class NotificationService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String INSERT_NOTIFICATION_SQL = "INSERT INTO notifications (user_id, title, message, expiry_date) VALUES (:userId, :title, :message, :expiryDate)";
    private static final String DELETE_NOTIFICATION_SQL = "DELETE FROM notifications WHERE notification_id = ?";
    private static final String SELECT_BY_USER_ID_SQL = "SELECT * FROM notifications WHERE user_id = ? AND expiry_date > ?";
    private static final String COUNT_BY_NOTIFICATION_ID = "SELECT COUNT(*) FROM notifications WHERE notification_id = ?";
   

    @Transactional(readOnly = true)
    public List<DxNotificationMessage> getNotificationsByUserId(String userId) {
        // Query for non-expired notifications by userId
        return jdbcTemplate.query(
                SELECT_BY_USER_ID_SQL,
                new Object[]{userId, LocalDateTime.now()},
                new DxNotificationMessageRowMapper()
        );
    }

    public DxNotificationMessage addNotification(DxNotificationMessage notification) {
       
        
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", notification.getUserId())
                .addValue("title", notification.getTitle())
                .addValue("message", notification.getMessage())
                .addValue("expiryDate", notification.getExpiryDate());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(INSERT_NOTIFICATION_SQL, params, keyHolder, new String[] { "notification_id" });

        Number key = keyHolder.getKey();
        notification.setNotificationId(key != null ? key.longValue() : null);

        return notification;
    }

    @Transactional
    public void deleteNotification(Long id) {
    	 
         Integer count = jdbcTemplate.queryForObject(COUNT_BY_NOTIFICATION_ID, new Object[]{id}, Integer.class);

         if (count == null || count == 0) {
             throw new ResourceNotFoundException("Notification not found with id " + id);
         }
        
         jdbcTemplate.update(DELETE_NOTIFICATION_SQL, id);
    }

    // RowMapper implementation to map ResultSet to DxNotificationMessage
    private static class DxNotificationMessageRowMapper implements RowMapper<DxNotificationMessage> {
        @Override
        public DxNotificationMessage mapRow(ResultSet rs, int rowNum) throws SQLException {
            DxNotificationMessage notification = new DxNotificationMessage();
            notification.setNotificationId(rs.getLong("notification_id"));
            notification.setUserId(rs.getString("user_id"));
            notification.setTitle(rs.getString("title"));
            notification.setMessage(rs.getString("message"));
            notification.setExpiryDate(rs.getObject("expiry_date", LocalDateTime.class));
            return notification;
        }
    }
}
