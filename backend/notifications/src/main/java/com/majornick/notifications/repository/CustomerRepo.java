package com.majornick.notifications.repository;

import com.majornick.notifications.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {
    @Query("SELECT  c.preferredNotificationType as type, COUNT(c) as count FROM Customer c GROUP BY c.preferredNotificationType")
    List<Object[]> countCustomersByNotificationsType();
}
