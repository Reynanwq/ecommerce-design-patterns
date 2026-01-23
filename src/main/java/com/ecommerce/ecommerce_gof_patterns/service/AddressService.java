package com.ecommerce.ecommerce_gof_patterns.service;

import com.ecommerce.ecommerce_gof_patterns.dto.AddressDTO;
import com.ecommerce.ecommerce_gof_patterns.exception.ResourceNotFoundException;
import com.ecommerce.ecommerce_gof_patterns.model.Address;
import com.ecommerce.ecommerce_gof_patterns.model.Customer;
import com.ecommerce.ecommerce_gof_patterns.repository.AddressRepository;
import com.ecommerce.ecommerce_gof_patterns.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    // Construtor com injeção de dependência
    @Autowired
    public AddressService(AddressRepository addressRepository,
                          CustomerRepository customerRepository,
                          ModelMapper modelMapper) {
        this.addressRepository = addressRepository;
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional(readOnly = true)
    public List<AddressDTO> getAddressesByCustomerId(Long customerId) {
        List<Address> addresses = addressRepository.findByCustomerId(customerId);
        return addresses.stream()
                .map(address -> modelMapper.map(address, AddressDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AddressDTO getAddressById(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "id", id));
        return modelMapper.map(address, AddressDTO.class);
    }

    @Transactional
    public AddressDTO createAddress(AddressDTO addressDTO) {
        Customer customer = customerRepository.findById(addressDTO.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", addressDTO.getCustomerId()));

        Address address = modelMapper.map(addressDTO, Address.class);
        address.setCustomer(customer);

        // Se for o primeiro endereço ou marcado como padrão, definir como padrão
        if (addressDTO.getIsDefault() != null && addressDTO.getIsDefault()) {
            // Remover padrão dos outros endereços
            removeDefaultFromOtherAddresses(customer.getId());
            address.setIsDefault(true);
        } else {
            // Se for o primeiro endereço, definir como padrão
            List<Address> existingAddresses = addressRepository.findByCustomerId(customer.getId());
            if (existingAddresses.isEmpty()) {
                address.setIsDefault(true);
            } else {
                address.setIsDefault(false);
            }
        }

        Address savedAddress = addressRepository.save(address);
        return modelMapper.map(savedAddress, AddressDTO.class);
    }

    @Transactional
    public AddressDTO updateAddress(Long id, AddressDTO addressDTO) {
        Address existingAddress = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "id", id));

        existingAddress.setStreet(addressDTO.getStreet());
        existingAddress.setNumber(addressDTO.getNumber());
        existingAddress.setComplement(addressDTO.getComplement());
        existingAddress.setNeighborhood(addressDTO.getNeighborhood());
        existingAddress.setCity(addressDTO.getCity());
        existingAddress.setState(addressDTO.getState());
        existingAddress.setZipCode(addressDTO.getZipCode());
        existingAddress.setType(addressDTO.getType());

        if (addressDTO.getIsDefault() != null && addressDTO.getIsDefault()) {
            removeDefaultFromOtherAddresses(existingAddress.getCustomer().getId());
            existingAddress.setIsDefault(true);
        }

        Address updatedAddress = addressRepository.save(existingAddress);
        return modelMapper.map(updatedAddress, AddressDTO.class);
    }

    @Transactional
    public void deleteAddress(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "id", id));

        // Se for o endereço padrão, definir outro como padrão
        if (address.getIsDefault()) {
            List<Address> otherAddresses = addressRepository.findByCustomerId(address.getCustomer().getId())
                    .stream()
                    .filter(a -> !a.getId().equals(id))
                    .collect(Collectors.toList());

            if (!otherAddresses.isEmpty()) {
                otherAddresses.get(0).setIsDefault(true);
                addressRepository.save(otherAddresses.get(0));
            }
        }

        addressRepository.delete(address);
    }

    @Transactional(readOnly = true)
    public AddressDTO getDefaultAddress(Long customerId) {
        Address address = addressRepository.findByCustomerIdAndIsDefaultTrue(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Default address not found for customer: " + customerId));
        return modelMapper.map(address, AddressDTO.class);
    }

    @Transactional
    public AddressDTO setDefaultAddress(Long customerId, Long addressId) {
        // Verificar se o endereço pertence ao cliente
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "id", addressId));

        if (!address.getCustomer().getId().equals(customerId)) {
            throw new ResourceNotFoundException("Address does not belong to customer");
        }

        // Remover padrão dos outros endereços
        removeDefaultFromOtherAddresses(customerId);

        // Definir este como padrão
        address.setIsDefault(true);
        Address updatedAddress = addressRepository.save(address);

        return modelMapper.map(updatedAddress, AddressDTO.class);
    }

    private void removeDefaultFromOtherAddresses(Long customerId) {
        List<Address> addresses = addressRepository.findByCustomerId(customerId);
        for (Address addr : addresses) {
            if (addr.getIsDefault()) {
                addr.setIsDefault(false);
            }
        }
        addressRepository.saveAll(addresses);
    }
}