package com.majornick.notifications.service;

import com.majornick.notifications.domain.Customer;
import com.majornick.notifications.dto.CustomerDTO;
import com.majornick.notifications.dto.SearchDTO;
import com.majornick.notifications.mapper.CustomerMapper;
import com.majornick.notifications.repository.CustomerRepo;
import com.majornick.notifications.repository.NotificationRepo;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportsAndSearchService {

    private final CustomerRepo customerRepo;
    private final NotificationRepo notificationRepo;
    private final EntityManager entityManager;
    private final CustomerMapper customerMapper;


    public Map<String, Long> getNotificationStatusReport() {
        var list = notificationRepo.countNotificationsByStatus();
        return list.stream()
                .collect(Collectors.toMap(
                        arr -> arr[0].toString(),
                        arr -> ((Number) arr[1]).longValue()
                ));
    }
    public Map<String,Long> getNotificationPreferencesStats() {
        var list = customerRepo.countCustomersByNotificationsType();
        return list.stream()
                .collect(Collectors.toMap(
                        arr -> arr[0].toString(),
                        arr -> ((Number) arr[1]).longValue()
                ));
    }


    public List<CustomerDTO> searchCustomers(SearchDTO searchDTO) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Customer> query = cb.createQuery(Customer.class);
        Root<Customer> customer = query.from(Customer.class);
        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.isNotBlank(searchDTO.getFullName())) {
            predicates.add(cb.like(cb.lower(customer.get("fullName")), "%" + searchDTO.getFullName().toLowerCase() + "%"));
        }
        if (StringUtils.isNotBlank(searchDTO.getEmail())) {
            predicates.add(cb.like(cb.lower(customer.get("email")), "%" + searchDTO.getEmail().toLowerCase() + "%"));
        }
        if (StringUtils.isNotBlank(searchDTO.getPhoneNumber())) {
            predicates.add(cb.like(customer.get("phoneNumber"), "%" + searchDTO.getPhoneNumber() + "%"));
        }
        if (StringUtils.isNotBlank(searchDTO.getPreferredNotificationType())) {
            predicates.add(cb.equal(customer.get("preferredNotificationType"), searchDTO.getPreferredNotificationType()));
        }
        query.where(cb.and(predicates.toArray(new Predicate[0])));
        return entityManager.createQuery(query).getResultList()
                .stream()
                .map(customerMapper::toDTO)
                .toList();
    }


}
