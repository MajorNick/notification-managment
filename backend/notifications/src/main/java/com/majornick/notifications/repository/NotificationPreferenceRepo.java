package com.majornick.notifications.repository;

import com.majornick.notifications.domain.NotificationPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationPreferenceRepo extends JpaRepository<NotificationPreference, Long> {
}
