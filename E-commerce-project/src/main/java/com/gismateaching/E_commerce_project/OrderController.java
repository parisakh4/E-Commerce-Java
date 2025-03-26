package com.gismateaching.E_commerce_project;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Order")
public class OrderController {
    private final OrderService orderService;
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/get")
    public List<Order> getOrders() {
        return orderService.getOrder();
    }

    @PostMapping("/add")
    public ResponseEntity<String> addOrder(@RequestBody OrderService.NewOrderRequest request) {
        return orderService.addOrder(request);
    }

    @PutMapping("/update/{orderId}")
    public ResponseEntity<String> updateOrder(@PathVariable Integer orderId, @RequestBody OrderService.NewOrderRequest request) {
        return orderService.updateOrder(orderId, request);
    }

    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable Integer orderId, @RequestBody OrderService.NewOrderRequest request) {
        return orderService.deleteOrder(orderId);
    }

}
