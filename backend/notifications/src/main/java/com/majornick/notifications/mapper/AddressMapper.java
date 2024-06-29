package com.majornick.notifications.mapper;

import com.majornick.notifications.domain.Address;
import com.majornick.notifications.dto.AddressDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AddressMapper {
    private final ModelMapper modelMapper;

    public Address toAddress(AddressDTO addressDTO) {
        return modelMapper.map(addressDTO, Address.class);
    }

    public AddressDTO toDto(Address address) {
        return modelMapper.map(address, AddressDTO.class);
    }
}
