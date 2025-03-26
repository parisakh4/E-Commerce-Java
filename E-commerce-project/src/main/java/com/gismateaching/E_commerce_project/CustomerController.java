package com.gismateaching.E_commerce_project;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.gismateaching.E_commerce_project.CustomerService.NewCustomerRequest;
import java.util.List;

@RestController
@RequestMapping("/Customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/get")
    public List<Customer> getCustomer() {
        return customerService.getCustomer();
    }
    @PostMapping("/add")
    public ResponseEntity<String> addCustomer(@RequestBody CustomerService.NewCustomerRequest request) {
        return customerService.addCustomer(request);
    }
    @PutMapping("/update/{customerId}")
    public ResponseEntity<String> updateCustomer(@PathVariable Integer customerId,
                                                 @RequestBody NewCustomerRequest request) {
        return customerService.updateCustomer(customerId, request);
    }
    @DeleteMapping("delete/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Integer customerId) {
        return customerService.deleteCustomer(customerId);
    }

    @GetMapping("/withOrders/{customerId}")
    public ResponseEntity<Customer> getCustomerWithOrders(@PathVariable("customerId") Integer customerId) {
        Customer customer = customerService.getCustomerWithOrders(customerId);
        return ResponseEntity.ok(customer);
    }
}

