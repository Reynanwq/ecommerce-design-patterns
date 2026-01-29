package com.ecommerce.ecommerce_gof_patterns.service;

import com.ecommerce.ecommerce_gof_patterns.adapter.LegacyPaymentGateway;
import com.ecommerce.ecommerce_gof_patterns.adapter.PaymentGatewayAdapter;
import com.ecommerce.ecommerce_gof_patterns.adapter.PaymentProcessor;
import com.ecommerce.ecommerce_gof_patterns.dto.CustomerDTO;
import com.ecommerce.ecommerce_gof_patterns.exception.BusinessException;
import com.ecommerce.ecommerce_gof_patterns.exception.ResourceNotFoundException;
import com.ecommerce.ecommerce_gof_patterns.model.Customer;
import com.ecommerce.ecommerce_gof_patterns.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customer -> modelMapper.map(customer, CustomerDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CustomerDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));
        return modelMapper.map(customer, CustomerDTO.class);
    }

    @Transactional
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        // Validar se email já existe
        if (customerRepository.existsByEmail(customerDTO.getEmail())) {
            throw new BusinessException("Email already registered: " + customerDTO.getEmail());
        }

        // Validar se CPF já existe
        if (customerRepository.existsByCpf(customerDTO.getCpf())) {
            throw new BusinessException("CPF already registered: " + customerDTO.getCpf());
        }

        Customer customer = modelMapper.map(customerDTO, Customer.class);
        customer.setActive(true);
        // Em produção, criptografar a senha com BCrypt
        Customer savedCustomer = customerRepository.save(customer);

        processInitialPayment(savedCustomer);
        return modelMapper.map(savedCustomer, CustomerDTO.class);
    }

    @Transactional
    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));

        // Validar se email já existe (exceto para o próprio cliente)
        if (!existingCustomer.getEmail().equals(customerDTO.getEmail())
                && customerRepository.existsByEmail(customerDTO.getEmail())) {
            throw new BusinessException("Email already registered: " + customerDTO.getEmail());
        }

        existingCustomer.setName(customerDTO.getName());
        existingCustomer.setEmail(customerDTO.getEmail());
        existingCustomer.setPhone(customerDTO.getPhone());

        // Atualizar senha apenas se fornecida
        if (customerDTO.getPassword() != null && !customerDTO.getPassword().isEmpty()) {
            existingCustomer.setPassword(customerDTO.getPassword());
        }

        Customer updatedCustomer = customerRepository.save(existingCustomer);
        return modelMapper.map(updatedCustomer, CustomerDTO.class);
    }

    @Transactional
    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));
        customer.setActive(false);
        customerRepository.save(customer);
    }

    @Transactional(readOnly = true)
    public CustomerDTO getCustomerByEmail(String email) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "email", email));
        return modelMapper.map(customer, CustomerDTO.class);
    }

    private void processInitialPayment(Customer customer) {
        // Criando e usando o adapter
        LegacyPaymentGateway legacyGateway = new LegacyPaymentGateway();
        PaymentProcessor paymentProcessor = new PaymentGatewayAdapter(legacyGateway);

        // Chamada uniforme
        boolean success = paymentProcessor.processPayment(0.01, "BRL"); // Pagamento simbólico
        System.out.println("Pagamento inicial processado: " + success);
    }
}
