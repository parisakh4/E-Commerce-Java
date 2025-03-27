package com.gismateaching.E_commerce_project;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.Map;

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

//-----------------------------Add New Order ------------------------------------------------

    @Transactional
    public ResponseEntity<String> addOrder(NewOrderRequest request) {
        if (request.customer_id() == null || request.total_amount() == null || request.address_id() == null) {
            throw new IllegalArgumentException("Customer ID, Total Amount, and Address ID cannot be null.");
        }
        Customer customer = customerRepository.findById(request.customer_id())
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + request.customer_id()));

        Order order = new Order();
        order.setOrder_date(request.order_date() != null ? request.order_date() : LocalDate.now()); // Using current date if not provided
        order.setTotal_amount(request.total_amount());
        order.setO_status(request.o_status() != null ? request.o_status().toLowerCase() : "pending"); // Default status is 'pending'
        order.setCustomer(customer);  // Set the fetched Customer object here
        order.setAddress_id(request.address_id());
        order.setChanged_at(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);



        return ResponseEntity.ok("Order added successfully! Order ID: " + savedOrder.getOrder_id());
    }

//-------------------Normal Update----------------------------

    @Transactional
    public ResponseEntity<String> updateOrder(Integer orderId, NewOrderRequest request) {
        if (!orderRepository.existsById(orderId)) {
            throw new RuntimeException("Order with ID " + orderId + " not found");
        }

        Order order = orderRepository.findById(orderId).get();
        order.setOrder_date(request.order_date() != null ? request.order_date() : order.getOrder_date());
        order.setTotal_amount(request.total_amount());
        order.setO_status(request.o_status());
        order.setAddress_id(request.address_id());
        order.setChanged_at(LocalDateTime.now());

        // Retrieve the Customer object and set it on the order
        Customer customer = customerRepository.findById(request.customer_id())
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + request.customer_id()));
        order.setCustomer(customer);

        orderRepository.save(order);
        return ResponseEntity.ok("Order with ID " + orderId + " updated successfully!");
    }

    //-----------Delete Order with restrictions based on Status------------

    @Transactional
    public ResponseEntity<String> deleteOrder(Integer order_id) {

        Order order = orderRepository.findById(order_id)
                .orElseThrow(() -> new RuntimeException("Order with ID " + order_id + " not found"));

        String currentStatus = order.getO_status().toLowerCase();

        // Restrict deletion based on status
        if (!currentStatus.equals("pending") && !currentStatus.equals("failed")) {
            throw new IllegalStateException("Cannot delete order with status: " + currentStatus);
        }

        // Proceed with deletion if status is allowed
        orderRepository.deleteById(order_id);
        return ResponseEntity.ok("Order with ID " + order_id + " deleted successfully!");
    }

    //---------------------------Order status change defining valid status transitions--------------------------------

    private static final Set<String> VALID_STATUSES = Set.of("pending", "completed","failed", "shipped", "delivered", "cancelled");

    private static final Map<String, List<String>> ALLOWED_TRANSITIONS = Map.of(
            "pending", List.of("completed", "failed", "cancelled"),
            "completed", List.of("shipped", "cancelled"),
            "shipped", List.of("delivered")
    );

    @Transactional
    public ResponseEntity<String> updateOrderStatus(Integer order_id, String newStatus) {
        // Check for null or empty status
        if (newStatus == null || newStatus.trim().isEmpty()) {
            throw new IllegalArgumentException("Status cannot be null or empty.");
        }
        newStatus = newStatus.toLowerCase();

        // Validating status
        if (!VALID_STATUSES.contains(newStatus)) {
            throw new IllegalArgumentException("Invalid status: " + newStatus);
        }
        Order order = orderRepository.findById(order_id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + order_id));

        String currentStatus = order.getO_status().toLowerCase();
        order.setChanged_at(LocalDateTime.now());  // Update changed_at when status changes
        orderRepository.save(order);

        // Check if the transition is allowed
        if (!ALLOWED_TRANSITIONS.getOrDefault(currentStatus, List.of()).contains(newStatus)) {
            throw new IllegalStateException("Cannot change status from " + currentStatus + " to " + newStatus);
        }

        orderRepository.updateOrderStatus(order_id, newStatus);
        return ResponseEntity.ok("Order status with ID " + order_id + " updated to " + newStatus);
    }





}
