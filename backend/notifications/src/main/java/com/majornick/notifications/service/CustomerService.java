package com.majornick.notifications.service;

import com.majornick.notifications.domain.Address;
import com.majornick.notifications.domain.Customer;
import com.majornick.notifications.domain.Notification;
import com.majornick.notifications.domain.enums.NotificationType;
import com.majornick.notifications.dto.AddressDTO;
import com.majornick.notifications.dto.CustomerDTO;
import com.majornick.notifications.dto.NotificationDTO;
import com.majornick.notifications.dto.SearchDTO;
import com.majornick.notifications.exception.CustomerNotFoundException;
import com.majornick.notifications.mapper.AddressMapper;
import com.majornick.notifications.mapper.CustomerMapper;
import com.majornick.notifications.mapper.NotificationMapper;
import com.majornick.notifications.repository.AddressRepo;
import com.majornick.notifications.repository.CustomerRepo;
import com.majornick.notifications.repository.NotificationRepo;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepo customerRepo;
    private final CustomerMapper customerMapper;
    private final AddressMapper addressMapper;
    private final AddressRepo addressRepo;
    private final NotificationMapper notificationMapper;
    private final NotificationRepo notificationRepo;
    private final EntityManager entityManager;

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
    public CustomerDTO save(CustomerDTO customerDto) {
        Customer customer = customerMapper.toCustomer(customerDto);
        if (customer.getAddresses() != null) {
            for (Address address : customer.getAddresses()) {
                address.setCustomer(customer);
            }
        }
        return customerMapper.toDTO(customerRepo.save(customer));
    }

    @Transactional
    public void updateCustomer(@Valid CustomerDTO customerDTO) {
        Customer customer = customerRepo.findById(customerDTO.getId())
                .orElseThrow(() -> new CustomerNotFoundException(
                        String.format("Cannot update customer,  customer  with the id: %d not Found", customerDTO.getId())
                ));
        mergeCustomer(customer, customerDTO);
        customerRepo.save(customer);
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
    }

    public void deleteCustomer(Long customerId) {
        customerRepo.deleteById(customerId);
    }

    @Transactional
    public AddressDTO addAddressToCustomer(Long customerId, AddressDTO addressDTO) {
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(
                        String.format("Cannot add address to customer,  customer  with the id: %d not Found", customerId)
                ));
        Address address = addressMapper.toAddress(addressDTO);
        address.setCustomer(customer);
        return addressMapper.toDto(addressRepo.save(address));
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
        if (StringUtils.isNotBlank(searchDTO.getPhoneNumber())){
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
