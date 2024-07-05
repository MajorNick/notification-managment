package com.majornick.notifications.service;

import com.majornick.notifications.domain.Address;
import com.majornick.notifications.domain.Customer;
import com.majornick.notifications.domain.Notification;
import com.majornick.notifications.domain.enums.NotificationType;
import com.majornick.notifications.dto.CustomerDTO;
import com.majornick.notifications.dto.NotificationDTO;
import com.majornick.notifications.exception.CustomerNotFoundException;
import com.majornick.notifications.mapper.AddressMapper;
import com.majornick.notifications.mapper.CustomerMapper;
import com.majornick.notifications.mapper.NotificationMapper;
import com.majornick.notifications.repository.AddressRepo;
import com.majornick.notifications.repository.CustomerRepo;
import com.majornick.notifications.repository.NotificationRepo;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepo customerRepo;
    private final CustomerMapper customerMapper;
    private final NotificationMapper notificationMapper;
    private final NotificationRepo notificationRepo;


    public List<CustomerDTO> getAllCustomer() {
        return customerRepo
                .findAll()
                .stream()
                .map(customerMapper::toDTO)
                .toList();
    }

    public CustomerDTO findById(Long id) {
        return customerRepo.findById(id)
                .map(customerMapper::toDTO)
                .orElseThrow(() -> new CustomerNotFoundException(String.format("customer  with the id: %d not Found", id)));
    }

    @Transactional
    public List<CustomerDTO> save(List<CustomerDTO> customerDTOList) {
       return customerDTOList
               .stream()
               .map(customerDTO -> {
                        Customer customer = customerMapper.toCustomer(customerDTO);
                        if (customer.getAddresses() != null) {
                            for (Address address : customer.getAddresses()) {
                                address.setCustomer(customer);
                            }
                        }
                        return customerMapper.toDTO(customerRepo.save(customer));
               })
               .toList();
    }

    @Transactional
    public void updateCustomer(@Valid CustomerDTO customerDTO) {
        Customer customer = customerRepo.findById(customerDTO.getId())
                .orElseThrow(() -> new CustomerNotFoundException(
                        String.format("Cannot update customer,  customer  with the id: %d not Found", customerDTO.getId())
                ));
        mergeCustomer(customer, customerDTO);

    }

    @Transactional
    public void mergeCustomer(Customer customer, CustomerDTO customerDTO) {
        if (StringUtils.isNotBlank(customerDTO.getFullName())) {
            customer.setFullName(customerDTO.getFullName());
        }
        if (StringUtils.isNotBlank(customerDTO.getPhoneNumber())) {
            customer.setPhoneNumber(customerDTO.getPhoneNumber());
        }
        if (StringUtils.isNotBlank(customerDTO.getEmail())) {
            customer.setEmail(customerDTO.getEmail());
        }
        if (customerDTO.getPreferredNotificationType()!= null) {
            customer.setPreferredNotificationType(customerDTO.getPreferredNotificationType());
        }
        customer.setUpdatedAt(LocalDateTime.now());
    }

    public void deleteCustomer(Long customerId) {
        customerRepo.deleteById(customerId);
    }


    @Transactional
    public void switchPreference(Long customerId, NotificationType type) {
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(
                        String.format("Cannot add Notification preference to customer,  customer  with the id: %d not Found", customerId)
                ));
        customer.setPreferredNotificationType(type);
    }

    @Transactional
    public NotificationDTO assignNotificationToCustomer(Long customerId, NotificationDTO notificationDTO) {
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(
                        String.format("Cannot assign Notification  to customer,  customer  with the id: %d not Found", customerId)
                ));
        Notification notification = notificationMapper.toNotification(notificationDTO);
        notification.setCustomer(customer);
        return notificationMapper.toDto(notificationRepo.save(notification));
    }


}
