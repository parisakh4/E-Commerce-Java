package com.gismateaching.E_commerce_project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

    //joining order and product on status
    @Query("SELECT od FROM OrderDetail od " +
            "JOIN FETCH od.order o " +
            "JOIN FETCH od.product p " +
            "WHERE o.o_status = 'pending'")
    List<OrderDetail> findPendingOrderDetails();

    //getting order details for an order
    @Query("SELECT od FROM OrderDetail od JOIN FETCH od.product WHERE od.order.order_id = :order_id")
    List<OrderDetail> findByOrderId(@Param("order_id") Integer order_id);

}
