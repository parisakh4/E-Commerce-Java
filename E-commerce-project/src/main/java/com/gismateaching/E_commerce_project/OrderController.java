package com.gismateaching.E_commerce_project;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<String> updateOrder(
            @PathVariable Integer orderId,
            @RequestBody OrderService.NewOrderRequest request) {
        return orderService.updateOrder(orderId, request);
    }

    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable Integer orderId) {
        return orderService.deleteOrder(orderId);
    }

    //Updating order status
    @PutMapping("/{orderId}/status")
    public ResponseEntity<String> updateOrderStatus(
            @PathVariable("orderId") Integer orderId,
            @RequestBody Map<String, String> request) {

        String newStatus = request.get("newStatus");
        return orderService.updateOrderStatus(orderId, newStatus);  // Now returns ResponseEntity directly
    }




}
