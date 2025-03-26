package com.gismateaching.E_commerce_project;


import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    public CustomerService(CustomerRepository customerRepository, OrderRepository orderRepository) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;

    }
    public List<Customer> getCustomer() {
        return customerRepository.findAll();
    }

    public static record NewCustomerRequest(String first_name, String last_name, String email, String phone_number, LocalDate registration_date) {}
    public ResponseEntity<String> addCustomer(NewCustomerRequest request) {
        Customer customer = new Customer();
        customer.setFirst_name(request.first_name);
        customer.setLast_name(request.last_name);
        customer.setEmail(request.email);
        customer.setPhone_number(request.phone_number);
        customer.setRegistration_date(request.registration_date);
        customerRepository.save(customer);
        return ResponseEntity.ok("Customer added");
    }

    public ResponseEntity<String> updateCustomer(
            @PathVariable("customer_id") Integer customer_id, NewCustomerRequest request) {
        if (!customerRepository.existsById(customer_id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Customer with ID " + customer_id + " not found");
        }
        Customer customer = customerRepository.findById(customer_id).get();
        customer.setFirst_name(request.first_name);
        customer.setLast_name(request.last_name);
        customer.setEmail(request.email);
        customer.setPhone_number(request.phone_number);
        customer.setRegistration_date(request.registration_date);
        customerRepository.save(customer);
        return ResponseEntity.ok("Customer with ID " + customer_id + " updated successfully");
    }

    public ResponseEntity<String> deleteCustomer(@PathVariable("customer_id") Integer customer_id) {
        if (!customerRepository.existsById(customer_id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .header("X-error","Customer not found")
                    .body("Customer with ID " + customer_id +" not found");
        }
        customerRepository.deleteById(customer_id);
        return ResponseEntity.ok("Customer with ID " + customer_id + " deleted successfully");
    }

    @Transactional(readOnly = true)
    public List<Order> getCustomerOrders(Integer customer_id) {
        customerRepository.findById(customer_id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + customer_id));

        return orderRepository.findByCustomerId(customer_id);
    }

}


