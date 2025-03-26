package com.gismateaching.E_commerce_project;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
    }
    public List<Order> getOrder() {
        return orderRepository.findAll();
    }
    public static record NewOrderRequest(LocalDate order_date, Double total_amount, String o_status, Integer customer_id, Integer address_id) {}
    public ResponseEntity<String> addOrder(NewOrderRequest request) {
        Order order = new Order();
        order.setOrder_date(request.order_date);
        order.setTotal_amount(request.total_amount);
        order.setO_status(request.o_status);
        order.setCustomer_id(request.customer_id);
        order.setAddress_id(request.address_id);
        orderRepository.save(order);
        return ResponseEntity.ok("Order added");
    }

    public ResponseEntity<String> updateOrder(
            @PathVariable("order_id") Integer order_id, NewOrderRequest request) {
        if (!orderRepository.existsById(order_id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Order with ID " + order_id + " not found");
        }

        Order order = orderRepository.findById(order_id).get();
        order.setOrder_date(request.order_date);
        order.setTotal_amount(request.total_amount);
        order.setO_status(request.o_status);
        order.setCustomer_id(request.customer_id);
        order.setAddress_id(request.address_id);
        orderRepository.save(order);
        return ResponseEntity.ok("Order with ID " + order_id + " updated successfully!");

    }

    public ResponseEntity<String> deleteOrder(@PathVariable("order_id") Integer order_id) {
        if (!orderRepository.existsById(order_id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Order with ID " + order_id + " not found");
        }
        orderRepository.deleteById(order_id);
        return ResponseEntity.ok("Order with ID " + order_id + " deleted successfully!");
    }
}
