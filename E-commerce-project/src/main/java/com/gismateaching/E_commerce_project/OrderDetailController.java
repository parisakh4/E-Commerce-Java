package com.gismateaching.E_commerce_project;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/OrderDetail")
public class OrderDetailController {
    private final OrderDetailService orderDetailService;
    public OrderDetailController(OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    @GetMapping("/get")
    public List<OrderDetail> getOrderDetail() {
        return orderDetailService.getOrderDetail();
    }

    @PostMapping("/add")
    public ResponseEntity<String> addOrderDetail(@RequestBody OrderDetailService.NewOrderDetailRequest request) {
        return orderDetailService.addOrderDetail(request);
    }

    @PutMapping("/update/{orderDetailId}")
    public ResponseEntity<String> updateOrderDetail(@PathVariable Integer orderDetailId, @RequestBody OrderDetailService.NewOrderDetailRequest request) {
        return orderDetailService.updateOrderDetail(orderDetailId, request);
    }

    @DeleteMapping("/delete/{orderDetailId}")
    public ResponseEntity<String> deleteOrderDetail(@PathVariable Integer orderDetailId, @RequestBody OrderDetailService.NewOrderDetailRequest request) {
        return orderDetailService.deleteOrderDetail(orderDetailId);
    }

    //getting pending orders details
    @GetMapping("/pending")
    public ResponseEntity<List<OrderDetail>> getPendingOrderDetails() {
        List<OrderDetail> pendingOrderDetails = orderDetailService.getPendingOrderDetails();
        return ResponseEntity.ok(pendingOrderDetails);
    }

    //getting one order's detail
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderDetail>> getOrderDetailsByOrderId(@PathVariable("orderId") Integer orderId) {
        List<OrderDetail> orderDetails = orderDetailService.getOrderDetailsByOrderId(orderId);
        return ResponseEntity.ok(orderDetails);
    }
}
