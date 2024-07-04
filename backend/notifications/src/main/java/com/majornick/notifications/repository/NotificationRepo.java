package com.majornick.notifications.repository;

import com.majornick.notifications.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepo extends JpaRepository<Notification, Long> {
    @Query("SELECT  n.status as status, COUNT(n) as count FROM Notification n GROUP BY n.status")
    List<Object[]> countNotificationsByStatus();
}
