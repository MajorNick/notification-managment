package com.majornick.notifications.service;

import com.majornick.notifications.domain.Address;
import com.majornick.notifications.domain.Customer;
import com.majornick.notifications.domain.NotificationPreference;
import com.majornick.notifications.domain.enums.NotificationType;
import com.majornick.notifications.dto.AddressDTO;
import com.majornick.notifications.dto.CustomerDTO;
import com.majornick.notifications.exception.CustomerNotFoundException;
import com.majornick.notifications.mapper.AddressMapper;
import com.majornick.notifications.mapper.CustomerMapper;
import com.majornick.notifications.repository.AddressRepo;
import com.majornick.notifications.repository.CustomerRepo;
import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepo customerRepo;
    private final CustomerMapper customerMapper;
    private final AddressMapper addressMapper;
    private final AddressRepo addressRepo;

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
        if (StringUtils.isNotBlank(customerDTO.getMobilePhone())) {
            customer.setMobilePhone(customerDTO.getMobilePhone());
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
    public void createPreference(Long customerId, NotificationType type) {
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(
                        String.format("Cannot add address to customer,  customer  with the id: %d not Found", customerId)
                ));
        for (var notificationPreference : customer.getNotificationPreferenceHistory()) {
            if (notificationPreference.getActive()) {
                notificationPreference.setActive(false);
                break;
            }
        }
        NotificationPreference.builder().notificationType(type).active(true).build();
    }
}
