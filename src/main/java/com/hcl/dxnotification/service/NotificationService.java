package com.hcl.dxnotification.service;

import com.hcl.dxnotification.errorhandling.ResourceNotFoundException;
import com.hcl.dxnotification.model.DxNotificationMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.caffeine.CaffeineCache;
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
    private CacheManager cacheManager;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String INSERT_NOTIFICATION_SQL = "INSERT INTO notifications (user_id, title, message, expiry_date, source_app_id) VALUES (:userId, :title, :message, :expiryDate, :sourceAppId)";
    private static final String DELETE_NOTIFICATION_SQL = "DELETE FROM notifications WHERE notification_id = ?";
    private static final String SELECT_BY_USER_ID_SQL = "SELECT * FROM notifications WHERE user_id = ? AND expiry_date > ?";
    private static final String COUNT_BY_NOTIFICATION_ID = "SELECT COUNT(*) FROM notifications WHERE notification_id = ?";
   
    @SuppressWarnings("deprecation")
	@Transactional(readOnly = true)
    @Cacheable(value = "notifications", key = "#userId.concat('-').concat(#limit.toString())", condition = "#invalidateCache == false")
    public List<DxNotificationMessage> getNotificationsByUserId(String userId, Integer limit, Boolean invalidateCache) {
    	   if (invalidateCache != null && invalidateCache) {
               invalidateCacheForUser(userId);
           }
        // Prepare the SQL query with an optional LIMIT clause
        String sql = "SELECT * FROM notifications WHERE user_id = ? AND expiry_date > ? " +
                     (limit != null && limit > 0 ? "LIMIT ?" : "");
        
        // Execute the query with the appropriate parameters
        if (limit != null && limit > 0) {
            return jdbcTemplate.query(
                    sql,
                    new Object[]{userId, LocalDateTime.now(), limit},
                    new DxNotificationMessageRowMapper()
            );
        } else {
            return jdbcTemplate.query(
                    sql,
                    new Object[]{userId, LocalDateTime.now()},
                    new DxNotificationMessageRowMapper()
            );
        }
    }


    public DxNotificationMessage addNotification(DxNotificationMessage notification) {
           

        // Create parameters including the new sourceAppId
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", notification.getUserId())
                .addValue("title", notification.getTitle())
                .addValue("message", notification.getMessage())
                .addValue("expiryDate", notification.getExpiryDate())
                .addValue("sourceAppId", notification.getSourceAppId()); // Added new parameter

        KeyHolder keyHolder = new GeneratedKeyHolder();

        // Execute the insert statement
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
    
    @CacheEvict(value = "notifications", allEntries = true)
    public void invalidateCache() {
        // Method to force cache invalidation
    }
    
    private void invalidateCacheForUser(String userId) {
        Cache cache = cacheManager.getCache("notifications");
        if (cache instanceof CaffeineCache) {
            CaffeineCache caffeineCache = (CaffeineCache) cache;
            // Remove cache entries for the specific user
            caffeineCache.getNativeCache().invalidateAll(); // or use a custom method to invalidate specific keys if needed
        }
    }
}
