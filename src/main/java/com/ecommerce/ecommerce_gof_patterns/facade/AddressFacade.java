package com.ecommerce.ecommerce_gof_patterns.facade;

import com.ecommerce.ecommerce_gof_patterns.dto.AddressDTO;
import com.ecommerce.ecommerce_gof_patterns.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AddressFacade {

    private final AddressService addressService;

    public AddressDTO getAddress(Long id) {
        return addressService.getAddressById(id);
    }

}
