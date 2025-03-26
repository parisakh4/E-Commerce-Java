package com.gismateaching.E_commerce_project;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    //Updating Order Status
    @Modifying
    @Transactional
    @Query("UPDATE Order o SET o.o_status = LOWER(:newStatus)  WHERE o.order_id = :order_id")
    void updateOrderStatus(@Param("order_id") Integer order_id, @Param("newStatus") String newStatus);

    //getting customers with orders
    @Query("SELECT o FROM Order o WHERE o.customer.customer_id = :customer_id")
    List<Order> findByCustomerId(@Param("customer_id") Integer customer_id);

}
