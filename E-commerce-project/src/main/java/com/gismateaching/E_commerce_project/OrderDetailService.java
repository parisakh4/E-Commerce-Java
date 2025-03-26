package com.gismateaching.E_commerce_project;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderDetailService(OrderDetailRepository orderDetailRepository, OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderDetailRepository = orderDetailRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public List<OrderDetail> getOrderDetail() {
        return orderDetailRepository.findAll();
    }
    public static record NewOrderDetailRequest(Integer order_id, Integer product_id, Integer quantity, Double price_each){}

    public ResponseEntity<String> addOrderDetail(NewOrderDetailRequest request) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrder_id(request.order_id);
        orderDetail.setProduct_id(request.product_id);
        orderDetail.setQuantity(request.quantity);
        orderDetail.setPrice_each(request.price_each);
        orderDetailRepository.save(orderDetail);
        return ResponseEntity.ok("Order detail added");
    }

    public ResponseEntity<String> updateOrderDetail(
        @PathVariable("order_detail_id") Integer order_detail_id, NewOrderDetailRequest request) {
            if (!orderDetailRepository.existsById(order_detail_id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Order detail with ID " + order_detail_id + " not found");
            }
            OrderDetail orderDetail = orderDetailRepository.findById(order_detail_id).get();
            orderDetail.setProduct_id(request.product_id);
            orderDetail.setQuantity(request.quantity);
            orderDetail.setPrice_each(request.price_each);
            orderDetailRepository.save(orderDetail);
            return ResponseEntity.ok("Order detail updated");

    }

    public ResponseEntity<String> deleteOrderDetail(@PathVariable("order_detail_id") Integer order_detail_id) {
        if (!orderDetailRepository.existsById(order_detail_id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Order detail with ID " + order_detail_id + " not found");
        }
        orderDetailRepository.deleteById(order_detail_id);
        return ResponseEntity.ok("Order detail with ID " + order_detail_id + " deleted successfully!");
    }

    //get details for pending orders
    @Transactional(readOnly = true)
    public List<OrderDetail> getPendingOrderDetails() {
        return orderDetailRepository.findPendingOrderDetails();
    }

    //getting details for one order
    @Transactional(readOnly = true)
    public List<OrderDetail> getOrderDetailsByOrderId(Integer order_id) {
        return orderDetailRepository.findByOrderId(order_id);
    }

}
