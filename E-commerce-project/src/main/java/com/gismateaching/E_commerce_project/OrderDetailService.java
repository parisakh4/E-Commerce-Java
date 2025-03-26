package com.gismateaching.E_commerce_project;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
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

    //----------------------Adding Order Detail---------------------------------------------

    @Transactional
    public ResponseEntity<String> addOrderDetail(NewOrderDetailRequest request) {

        // Check if the order exists
        Order order = orderRepository.findById(request.order_id())
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + request.order_id()));

        // Check if the product exists
        Product product = productRepository.findById(request.product_id())
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + request.product_id()));

        // Check stock availability
        Integer availableStock = product.getStock();
        if (availableStock < request.quantity()) {

            // Insufficient stock - order status set as "failed"
            order.setO_status("failed");
            order.setChanged_at(LocalDateTime.now());
            orderRepository.save(order);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Transaction failed: Insufficient stock. Available stock: " + availableStock);
        }

        // Deduct stock
        product.setStock(availableStock - request.quantity());
        productRepository.save(product);

        // Save the order detail
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrder(order);
        orderDetail.setProduct(product);
        orderDetail.setQuantity(request.quantity());
        orderDetail.setPrice_each(request.price_each());
        orderDetailRepository.save(orderDetail);

        // Update the order's changed_at field
        order.setChanged_at(LocalDateTime.now());
        orderRepository.save(order);  // Persist the change timestamp only

        return ResponseEntity.ok("Order detail added successfully for Order ID: " + request.order_id());
    }

//---------------------Updating Order Detail -----------------------------------

    @Transactional
    public ResponseEntity<String> updateOrderDetail(Integer order_detail_id, NewOrderDetailRequest request) {
        // Retrieve the OrderDetail; if not found, an exception is thrown
        OrderDetail orderDetail = orderDetailRepository.findById(order_detail_id)
                .orElseThrow(() -> new RuntimeException("Order detail with ID " + order_detail_id + " not found"));

        // Retrieve the Product based on the ID in the request
        Product product = productRepository.findById(request.product_id())
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + request.product_id()));

        // Set the relationship fields and update the remaining details
        orderDetail.setProduct(product);
        orderDetail.setQuantity(request.quantity());
        orderDetail.setPrice_each(request.price_each());

        orderDetailRepository.save(orderDetail);
        return ResponseEntity.ok("Order detail updated");
    }

// -------------------------------Deleting Order Detail ------------------------------------------

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
